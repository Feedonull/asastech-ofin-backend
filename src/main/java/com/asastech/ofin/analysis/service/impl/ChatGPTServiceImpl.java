package com.asastech.ofin.analysis.service.impl;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGPTServiceImpl {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String doHorizontalAnalysis(String sheetData) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String prompt = sheetData;
        String apiKey = "API KEY GOES HERE";

        HttpURLConnection con = (HttpURLConnection) new URL(API_URL).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer "+apiKey);

        JSONObject data = new JSONObject();
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();

        message.put("role", "user");
        message.put("content", "write a horizontal analysis report with percentage, insights and recommendation on the following data "+sheetData);
        messages.put(message);

        data.put("model", "gpt-3.5-turbo");
        data.put("messages", messages);
        //data.put("max_tokens", 4000);
        //data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();
        String responseBody = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

        return responseBody;
    }

}
