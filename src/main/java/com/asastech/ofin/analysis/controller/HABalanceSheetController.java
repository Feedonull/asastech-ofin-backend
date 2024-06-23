package com.asastech.ofin.analysis.controller;

import com.asastech.ofin.analysis.dto.HAnalysisResponse;
import com.asastech.ofin.analysis.service.CsvFileExtractorService;
import com.asastech.ofin.analysis.service.impl.ChatGPTServiceImpl;
import com.asastech.ofin.analysis.service.impl.ReplicateAIServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/h-analysis")
@AllArgsConstructor
public class HABalanceSheetController {

    private final CsvFileExtractorService csvFileExtractorService;
    private final ChatGPTServiceImpl chatGPTService;
    private final ReplicateAIServiceImpl replicateAIService;
    @PostMapping("/csv")
    @CrossOrigin(origins = "http://localhost:3000")
    public HAnalysisResponse parseCsv(@RequestParam MultipartFile file) throws Exception {
        String data = csvFileExtractorService.readCsv(file);
        String report = replicateAIService.doHorizontalAnalysis(data);
        HAnalysisResponse hAnalysisResponse = new HAnalysisResponse();
        hAnalysisResponse.setReport(report);
        hAnalysisResponse.setData(data);
        return hAnalysisResponse;
    }

}
