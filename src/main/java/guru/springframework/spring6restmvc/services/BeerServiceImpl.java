package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{
    private Map<UUID, Beer> beers;

    public BeerServiceImpl(){
        beers = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Heiniken")
                .beerStyle(BeerStyle.LAGER)
                .createdDate(LocalDateTime.now())
                .price(BigDecimal.valueOf(7.99))
                .upc("123789")
                .quantityOnHand(15)
                .updateDate(LocalDateTime.now())
                .version(1)
                .build();
        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Burgasko")
                .beerStyle(BeerStyle.WHEAT)
                .createdDate(LocalDateTime.now())
                .price(BigDecimal.valueOf(5.30))
                .upc("199456")
                .quantityOnHand(150)
                .updateDate(LocalDateTime.now())
                .version(1)
                .build();
        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beers.put(beer1.getId(), beer1);
        beers.put(beer2.getId(), beer2);
        beers.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beers.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Getting beer in beer service impl with id " + id);
        return beers.get(id);
    }
}
