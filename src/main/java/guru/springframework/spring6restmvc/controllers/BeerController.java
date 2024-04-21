package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beers";
    public static final String BEER_PATH_ID = "/api/v1/beers/{beerId}";
    private final BeerService beerService;

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> deleteBeer (@PathVariable("beerId") UUID id){
        if(!beerService.deleteBeerById(id)){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> patchBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){
        beerService.patchBeerById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer){
        if(beerService.updateBeerById(id, beer).isEmpty()){
            throw new NotFoundException();
        };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost(@Valid @RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.saveBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beers/" + savedBeer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }
    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get beer by id in controller");
        return beerService.getBeerById(id).orElseThrow(() -> new NotFoundException());
    }
}
