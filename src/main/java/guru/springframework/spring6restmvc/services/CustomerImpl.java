package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerImpl implements CustomerService{
    private Map<UUID, Customer> customers;
    public CustomerImpl(){
        customers = new HashMap<>();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Ivan")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Mimi")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Petya")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);
    }
    @Override
    public Customer getCustomerById(UUID id) {
        return customers.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .name(customer.getName())
                .version(customer.getVersion())
                .build();
        customers.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }
}
