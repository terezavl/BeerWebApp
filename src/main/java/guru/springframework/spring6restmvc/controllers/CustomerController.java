package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

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
