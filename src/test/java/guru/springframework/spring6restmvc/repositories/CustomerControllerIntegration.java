package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.controllers.CustomerController;
import guru.springframework.spring6restmvc.controllers.NotFoundException;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIntegration {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;
    @Test
    public void deleteCustomerNotFound(){
        assertThrows(NotFoundException.class,
                () -> customerController.deleteCustomer(UUID.randomUUID()));
    }
    @Test
    public void deleteCustomerById(){
        Customer customer = customerRepository.findAll().get(0);

        ResponseEntity responseEntity = customerController.deleteCustomer(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }
    @Test
    public void updateCustomerNotFound(){
        assertThrows(NotFoundException.class,
                () -> customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build()));
    }
    @Test
    public void updateCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToDto(customer);
        customerDTO.setId(null);
        final String name = "Updated name";
        customerDTO.setName(name);

        ResponseEntity responseEntity =
                customerController.updateCustomer(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updated = customerRepository.findById(customer.getId()).get();
        assertThat(updated.getName()).isEqualTo(name);

    }
    @Test
    public void saveCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String [] url = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID id = UUID.fromString(url[4]);

        Customer  customer = customerRepository.findById(id).get();
        assertThat(customer).isNotNull();
    }
    @Test
    public void customerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.getById(UUID.randomUUID());
        });
    }
    @Test
    public void getCustomerById(){
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    public void getAllCustomers(){
        List<CustomerDTO> customers = customerController.getAllCustomers();

        assertThat(customers.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    public void getAllCustomersEmptyList(){
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.getAllCustomers();

        assertThat(customers.size()).isEqualTo(0);
    }
}
