package com.example.warerides;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DistanceCalculator {

    public static void main(String[] args) {
        String apiKey = "AIzaSyCkMiIRNbJKN6tc2YAU7KV9p-7s74Lew9A";
        String origin = "New+York,NY";
        String destination = "Los+Angeles,CA";

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="
                    + origin + "&destinations=" + destination + "&key=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

            // Parse the JSON response to extract distance information
            String jsonResponse = response.toString();
            // Parse the jsonResponse and extract distance information
            System.out.println(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
