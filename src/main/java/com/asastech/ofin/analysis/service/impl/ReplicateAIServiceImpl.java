package com.asastech.ofin.analysis.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@Service
@NoArgsConstructor
public class ReplicateAIServiceImpl {

    private static final String REPLICATE_API_URL = "https://api.replicate.com/v1/models/meta/llama-2-70b-chat/predictions";
    private String apiKey = "";// API KEY

    public String doHorizontalAnalysis(String sheetData) throws Exception {

        String prompt = sheetData;


        HttpURLConnection con = (HttpURLConnection) new URL(REPLICATE_API_URL).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);

        JSONObject data = new JSONObject();
        JSONObject input = new JSONObject();

        input.put("top_p", 1);
        input.put("prompt", "generate a horizontal analysis report with percentage, insights and recommendation on the following data "+sheetData);
        input.put("temperature", 0.5);
        input.put("max_new_tokens", 500);

        data.put("stream", false);
        data.put("input", input);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();
        String responseBody = new JSONObject(output).getJSONObject("urls").getString("get");//.getJSONObject(0).getJSONObject("message").getString("content");
        return getHorizontalAnalysis(responseBody);
    }

    public String getHorizontalAnalysis(String url) throws Exception {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();
        //String report = new JSONObject(output).getJSONArray("output").toString();//.getJSONObject(0).getJSONObject("message").getString("content");
        return output;
    }

}
