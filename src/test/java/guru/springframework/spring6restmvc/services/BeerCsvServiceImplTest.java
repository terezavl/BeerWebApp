package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCsvRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerCsvServiceImplTest {
    BeerCsvService beerCsvService = new BeerCsvServiceImpl();
    @Test
    void convertCSV() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csv-data/beers.csv");

        List<BeerCsvRecord> recs = beerCsvService.convertCsvFile(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}
