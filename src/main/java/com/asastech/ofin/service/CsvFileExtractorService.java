package com.asastech.ofin.service;

import com.asastech.ofin.model.Asset;
import com.asastech.ofin.model.BasePeriod;
import com.asastech.ofin.model.HABalanceSheet;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvFileExtractorService {

    public String readCsv(MultipartFile file) throws IOException, CsvException {
        Reader reader = new InputStreamReader(file.getInputStream());
        // Parse CSV data
        CSVReader csvReader = new CSVReaderBuilder(reader).build();
        List<String[]> rows = csvReader.readAll();
        /*HABalanceSheet haBalanceSheet = new HABalanceSheet();
        String[] firstRow = rows.get(0);
        String basePeriod = firstRow[firstRow.length-1];
        String comparisonPeriod = firstRow[firstRow.length-2];
        ArrayList<Asset> basePeriodAssets = new ArrayList<>();
        ArrayList<Asset> comparisonPeriodAssets = new ArrayList<>();
        for(int i = 0; i < rows.size(); i++){
            basePeriodAssets.add(new Asset(Long.parseLong(i+""),"",0.0));
        }*/
        String message = rows.stream()
                .map(r -> Arrays.toString(r))
                .collect(Collectors.joining("\n"));
        return message;
        //return "Processed " + rows.size() + " rows!";
    }

}
