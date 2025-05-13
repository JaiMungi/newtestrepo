package com.example1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CricketDataParser {
	String jsonData;
    public static void main(String[] args) throws JSONException, IOException {
       // String jsonData = "{ \"apikey\": \"2694da04-276b-4300-b298-a02455b27368\", \"data\": [ { \"id\": \"8c3e9e9c-2744-40a6-8f0b-7adaf7941fd2\", \"name\": \"Thailand Women vs United Arab Emirates Women, 6th Match\", \"matchType\": \"t20\", \"status\": \"Thailand Women won by 16 runs\", \"venue\": \"Terdthai Cricket Ground, Bangkok\", \"date\": \"2025-05-06\", \"dateTimeGMT\": \"2025-05-06T06:00:00\", \"teams\": [ \"Thailand Women\", \"United Arab Emirates Women\" ], \"teamInfo\": [ { \"name\": \"Thailand Women\", \"shortname\": \"THW\", \"img\": \"https://g.cricapi.com/iapi/2351-637985730472466346.webp?w=48\" }, { \"name\": \"United Arab Emirates Women\", \"shortname\": \"UAEW\", \"img\": \"https://g.cricapi.com/iapi/1136-637877081374906304.webp?w=48\" } ], \"score\": [ { \"r\": 117, \"w\": 5, \"o\": 20, \"inning\": \"Thailand Women Inning 1\" }, { \"r\": 101, \"w\": 10, \"o\": 19.1, \"inning\": \"United Arab Emirates Women Inning 1\" } ], \"series_id\": \"8a5172a9-28af-4c51-99bc-fc1ccebda96d\", \"fantasyEnabled\": true, \"bbbEnabled\": true, \"hasSquad\": true, \"matchStarted\": true, \"matchEnded\": true } ], \"status\": \"success\", \"info\": { \"hitsToday\": 79, \"hitsUsed\": 1, \"hitsLimit\": 100, \"credits\": 0, \"server\": 11, \"offsetRows\": 0, \"totalRows\": 8, \"queryTime\": 22.3054, \"s\": 0, \"cache\": 1 } }";
    	String apiUrl = "https://api.cricapi.com/v1/currentMatches?apikey=2694da04-276b-4300-b298-a02455b27368&offset=0"; // Replace with actual API URL
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
        	System.out.println("Got Response");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            try {
				jsonData = resonse.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    	
    	 
        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // Loop through each match in the data array
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject match = dataArray.getJSONObject(i);

            // Extract teamInfo
            JSONArray teamInfoArray = match.getJSONArray("teamInfo");
            for (int j = 0; j < teamInfoArray.length(); j++) {
                JSONObject team = teamInfoArray.getJSONObject(j);
                String teamName = team.getString("name");
                String teamShortName = team.getString("shortname");
                String teamImage = team.getString("img");

                // Print team information
                System.out.println("Team Name: " + teamName);
                System.out.println("Short Name: " + teamShortName);
                System.out.println("Image URL: " + teamImage);
            }

            // Extract score
            JSONArray scoreArray = match.getJSONArray("score");
            for (int j = 0; j < scoreArray.length(); j++) {
                JSONObject score = scoreArray.getJSONObject(j);
                int runs = score.getInt("r");
                int wickets = score.getInt("w");
                double overs = score.getDouble("o");
                String inning = score.getString("inning");

                // Print score information
                System.out.println("Inning: " + inning);
                System.out.println("Runs: " + runs);
                System.out.println("Wickets: " + wickets);
                System.out.println("Overs: " + overs);
            }

            System.out.println("-------------------------------------------------");
        
    }
}
}