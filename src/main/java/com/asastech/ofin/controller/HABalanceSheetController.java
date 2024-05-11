package com.asastech.ofin.controller;

import com.asastech.ofin.dto.HAnalysisResponse;
import com.asastech.ofin.service.CsvFileExtractorService;
import com.asastech.ofin.service.impl.ChatGPTServiceImpl;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/h-analysis")
@AllArgsConstructor
public class HABalanceSheetController {

    private final CsvFileExtractorService csvFileExtractorService;
    private final ChatGPTServiceImpl chatGPTService;
    @PostMapping("/csv")
    @CrossOrigin(origins = "http://localhost:3000")
    public HAnalysisResponse parseCsv(@RequestParam MultipartFile file) throws Exception {
        String data = csvFileExtractorService.readCsv(file);
        String report = chatGPTService.doHorizontalAnalysis(data);
        HAnalysisResponse hAnalysisResponse = new HAnalysisResponse();
        hAnalysisResponse.setReport(report);
        hAnalysisResponse.setData(data);
        return hAnalysisResponse;
    }

}
