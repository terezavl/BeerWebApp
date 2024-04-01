package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
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
    public ResponseEntity<Beer> deleteBeer (@PathVariable("beerId") UUID id){
        beerService.deleteBeerById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> patchBeer(@PathVariable("beerId") UUID id, @RequestBody Beer beer){
        beerService.patchBeerById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<Beer> updateBeer(@PathVariable("beerId") UUID id, @RequestBody Beer beer){
        beerService.updateBeerById(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(BEER_PATH)
    public ResponseEntity<Beer> handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beers/" + savedBeer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID id){
        log.debug("Get beer by id in controller");
        return beerService.getBeerById(id).orElseThrow(() -> new NotFoundException());
    }
}
