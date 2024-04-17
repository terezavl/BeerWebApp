package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customers";
    public static final String CUSTOMER_PATH_ID = "/api/v1/customers/{customerId}";
    private final CustomerService customerService;

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") UUID id){
        if(!customerService.deleteCustomerById(id)){
            throw new NotFoundException();
        };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer){
        customerService.patchCustomerById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer){
        if(customerService.updateCustomerById(id, customer).isEmpty()){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<CustomerDTO> handlePost(@RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerById(id).orElseThrow(() -> new NotFoundException());
    }
    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
