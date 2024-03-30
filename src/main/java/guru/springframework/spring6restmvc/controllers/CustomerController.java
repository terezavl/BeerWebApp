package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") UUID id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> patchCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer){
        customerService.patchCustomerById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer){
        customerService.updateCustomerById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Customer> handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping("/{customerId}")
    public Customer getById(@PathVariable("customerId") UUID id){
        return customerService.getCustomerById(id);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
