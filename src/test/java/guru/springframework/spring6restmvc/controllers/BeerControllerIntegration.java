package guru.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerControllerIntegration {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void patchBeer() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "Long name 123456789123456789123456789123456789123456789123456789123456789123456789");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest());

    }


    @Test
    public void deleteByIdNotFound(){
        assertThrows(NotFoundException.class,
                () -> beerController.deleteBeer(UUID.randomUUID()));
    }
    @Test
    public void deleteBeerById(){
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteBeer(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }
    @Test
    public void updateNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeer(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }
    @Test
    public void updateBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToDto(beer);
        beerDTO.setId(null);
        final String beerName = "Updated name";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateBeer(beer.getId(), beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();

        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }
    @Test
    public void saveBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New beer")
                .build();
        ResponseEntity responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String [] url = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID id = UUID.fromString(url[4]);

        Beer beer = beerRepository.findById(id).get();
        assertThat(beer).isNotNull();
    }
    @Test
    public void getBeerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }
    @Test
    public void getBeerById(){
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDto = beerController.getBeerById(beer.getId());

        assertThat(beerDto).isNotNull();
    }
    @Test
    public void getAllBeers(){
        List<BeerDTO> listBeers = beerController.listBeers();

        assertThat(listBeers.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    public void getAllBeersEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> listBeers = beerController.listBeers();

        assertThat(listBeers.size()).isEqualTo(0);
    }
}
