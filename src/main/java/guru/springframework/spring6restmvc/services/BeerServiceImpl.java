package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{
    private Map<UUID, BeerDTO> beers;

    public BeerServiceImpl(){
        beers = new HashMap<>();
        BeerDTO beer1 = BeerDTO.builder()
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
        BeerDTO beer2 = BeerDTO.builder()
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
        BeerDTO beer3 = BeerDTO.builder()
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
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beers.values());
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .version(beer.getVersion())
                .build();
        beers.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        BeerDTO existingBeer = beers.get(id);
        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setBeerStyle(beer.getBeerStyle());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        existingBeer.setUpc(beer.getUpc());
        existingBeer.setUpdateDate(LocalDateTime.now());

        beers.put(existingBeer.getId(), existingBeer);
        return Optional.of(existingBeer);
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        if(beers.containsKey(id)) {
            beers.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer) {
        BeerDTO existingBeer = beers.get(id);
        if(StringUtils.hasText(beer.getBeerName())) {
            existingBeer.setBeerName(beer.getBeerName());
        }
        if(StringUtils.hasText(beer.getUpc())) {
            existingBeer.setUpc(beer.getUpc());
        }
        if(beer.getBeerStyle() != null) {
            existingBeer.setBeerStyle(beer.getBeerStyle());
        }
        if(beer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
        }
        if(beer.getQuantityOnHand() != null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }
        existingBeer.setUpdateDate(LocalDateTime.now());

        beers.put(existingBeer.getId(), existingBeer);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Getting beer in beer service impl with id " + id);
        return Optional.of(beers.get(id));
    }
}
