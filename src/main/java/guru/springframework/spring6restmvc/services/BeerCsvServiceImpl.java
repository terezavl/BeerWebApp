package guru.springframework.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService{
    @Override
    public List<BeerCsvRecord> convertCsvFile(File csvFile) {
        try {
            List<BeerCsvRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(csvFile))
                    .withType(BeerCsvRecord.class)
                    .build().parse();
            return beerCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
