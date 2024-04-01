package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerImpl implements CustomerService{
    private Map<UUID, CustomerDTO> customers;
    public CustomerImpl(){
        customers = new HashMap<>();
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Ivan")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .name("Mimi")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CustomerDTO customer3 = CustomerDTO.builder()
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
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customers.get(id));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .name(customer.getName())
                .version(customer.getVersion())
                .build();
        customers.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existingCustomer = customers.get(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setUpdateDate(LocalDateTime.now());

        customers.put(existingCustomer.getId(), existingCustomer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customers.remove(id);
    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existingCustomer = customers.get(id);
        if(StringUtils.hasText(customer.getName())) {
            existingCustomer.setName(customer.getName());
        }
        if(customer.getVersion() != null) {
            existingCustomer.setVersion(customer.getVersion());
        }
        existingCustomer.setUpdateDate(LocalDateTime.now());

        customers.put(existingCustomer.getId(), existingCustomer);
    }
}
