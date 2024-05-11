package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;
    @Test
    void saveBeerLongerName(){
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                            .beerName("Amstel1566115599Amstel1566115599Amstel1566115599Amstel1566115599Amstel1566115599")
                            .beerStyle(BeerStyle.LAGER)
                            .upc("123659")
                            .price(BigDecimal.valueOf(13.5)).build());

                    beerRepository.flush();
                });

    }
    @Test
    void saveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("Amstel")
                .beerStyle(BeerStyle.LAGER)
                .upc("123659")
                .price(BigDecimal.valueOf(13.5)).build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}
