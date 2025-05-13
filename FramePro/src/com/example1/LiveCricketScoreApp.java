package com.example1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;


import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class LiveCricketScoreApp {
    private JFrame frame;
    private JLabel team1Label, team2Label, scoreLabel, wicketsLabel, oversLabel;
    private JTextArea commentaryArea;
    private JButton refreshButton;
    private String team1Name;
    private String team2Name;
    int team1Runs = 0, team1Wickets = 0;
    double team1Overs = 0.0;
    int team2Runs = 0, team2Wickets = 0;
    double team2Overs = 0.0;

    public LiveCricketScoreApp() {
        frame = new JFrame("Live Cricket Score");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(5, 2));

        team1Label = new JLabel("Team 1: ");
        team2Label = new JLabel("Team 2: ");
        scoreLabel = new JLabel("Score: ");
        wicketsLabel = new JLabel("Wickets: ");
        oversLabel = new JLabel("Overs: ");

        scorePanel.add(team1Label);
        scorePanel.add(team2Label);
        scorePanel.add(scoreLabel);
        scorePanel.add(wicketsLabel);
        scorePanel.add(oversLabel);

        commentaryArea = new JTextArea();
        commentaryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(commentaryArea);

        refreshButton = new JButton("Refresh Score");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchLiveScore();
            }
        });

        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(refreshButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
   
    private void fetchLiveScore() {
        try {
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

                // Parse the JSON response and update the UI
                updateUIWithScoreData(response.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Error fetching data: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private void updateUIWithScoreData(String jsonData) {
    	   try {
               JSONObject jsonObject = new JSONObject(jsonData);

               // Extract team info
               JSONArray teamInfoArray = jsonObject.getJSONArray("data");
              // String team1Name = "";
             //  String team2Name = "";
               if (teamInfoArray.length() >= 2) {
                   team1Name = teamInfoArray.getJSONObject(0).getString("name");
                   team2Name = teamInfoArray.getJSONObject(1).getString("name");
               } else if (teamInfoArray.length() == 1) {
                   team1Name = teamInfoArray.getJSONObject(0).getString("name");
               }

               // Extract score info
               JSONArray scoreArray = jsonObject.getJSONArray("score");
               

               if (scoreArray.length() >= 2) {
                   // First team's score
                   JSONObject score1 = scoreArray.getJSONObject(0);
                   team1Runs = score1.getInt("r");
                   team1Wickets = score1.getInt("w");
                   team1Overs = score1.getDouble("o");

                   // Second team's score
                   JSONObject score2 = scoreArray.getJSONObject(1);
                   team2Runs = score2.getInt("r");
                   team2Wickets = score2.getInt("w");
                   team2Overs = score2.getDouble("o");
               } else if (scoreArray.length() == 1) {
                   JSONObject score1 = scoreArray.getJSONObject(0);
                   team1Runs = score1.getInt("r");
                   team1Wickets = score1.getInt("w");
                   team1Overs = score1.getDouble("o");
               }

               // Update labels on the Event Dispatch Thread
               SwingUtilities.invokeLater(() -> {
                   team1Label.setText("Team 1: " + team1Name);
                   team2Label.setText("Team 2: " + team2Name);
                   scoreLabel.setText("Score: " + team1Runs + "/" + team1Wickets);
                   oversLabel.setText("Overs: " + team1Overs);

                   // Clear the commentary area and add second team's score as example
                   commentaryArea.setText("");
                   if (!team2Name.isEmpty()) {
                       commentaryArea.append(team2Name + ": " + team2Runs + "/" + team2Wickets + " (" + team2Overs + " overs)\n");
                   }
               });

           } catch (Exception e) {
               e.printStackTrace();
               SwingUtilities.invokeLater(() -> {
                   JOptionPane.showMessageDialog(frame, "Failed to parse score data: " + e.getMessage());
               });
           }
    	}
//    Timer timer = new Timer(60000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            fetchLiveScore();
//        }
//    });
//    timer.start();
    public static void main(String args[])
    {
    	LiveCricketScoreApp lc = new LiveCricketScoreApp();
    }
}
    
