package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToDto(
                beerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beer -> beerMapper.beerToDto(beer))
                .toList();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDto) {
        Beer beerToSave = beerMapper.dtoToBeer(beerDto);
        beerToSave.setCreatedDate(LocalDateTime.now());
        beerToSave.setUpdateDate(LocalDateTime.now());
        return beerMapper.beerToDto(beerRepository.save(beerToSave));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        Optional<Beer> optional = beerRepository.findById(id);
        if(optional.isPresent()){
            Beer foundBeer = optional.get();
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            beerRepository.save(foundBeer);
            return Optional.of(beerMapper.beerToDto(foundBeer));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        if(beerRepository.existsById(id)){
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer) {

    }
}
