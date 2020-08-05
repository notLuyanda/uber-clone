package com.company;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Random;

public class UberRide extends Ride {
    private String pickupLocation;
    private String destination;
    private String apiRequest = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=UCT,SA&destinations=CanalWalkShoppingMall,SA&departure_time=now&key=AIzaSyD43u7-fJZ3-CPc5GShnl09KpEzUacoFZg";

    private double rate;
    private double price;
    private double distance;

    private Driver driver;
    private Passenger passenger;


    public UberRide() {}

    public UberRide(String pickupLocation, String destination, double price, double rate, Driver driver, Passenger passenger) {
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.price = price;
        this.rate = rate;
        this.driver = driver;
        this.passenger = passenger;
    }


    public void setPickupLocation(String location) { this.pickupLocation = location; }

    public String getPickupLocation() { return this.pickupLocation; }


    public void setDestination(String destination) { this.destination = destination; }

    public String getDestination() { return this.destination; }


    public void setPrice(double price) { this.price = price; }

    public double getPrice() { return this.price; }


    public void setRate(double rate) { this.rate = rate; }

    public double getRate() { return this.rate; }


    public void setDriver(Driver driver) { this.driver = driver; }

    public Driver getDriver() { return this.driver; }


    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Passenger getPassenger() { return this.passenger; }


    @Override
    public double calculateDistance(String startPoint, String endPoint) {
        double distance = 0;

        try {
            UberRide uberRide = new UberRide();
            distance = MyGETRequest(startPoint, endPoint);
        }
        catch(IOException ex){
            System.out.println (ex.toString());
        }

        this.distance = distance / 1000;
        System.out.println("The distance is: " + this.distance + " km");

        return (distance/1000);
    }

    @Override
    public double calculateCost(String startPoint, String endPoint) {
        double cost = 0.0;

//        MAKE A NEW INSTANCE OF THE UberRide CLASS
        UberRide uberRide = new UberRide();

//        GET THE DISTANCE BETWEEN THE TWO LOCATIONS
        double distance = uberRide.calculateDistance(startPoint, endPoint);

//        COST IS DISTANCE * THE DRIVERS BASE PRICE
        cost = distance * this.driver.getCar().getBaseRate();

        this.price = cost;
        System.out.println("The cost is: R" + (int)this.price);

        return cost;
    }

    @Override
    public Driver assignDriver() {
//        CREATE A NEW INSTANCE OF THE Database CLASS
        Database DB = new Database();
        DB.sortDriversByType();
        Driver[] drivers = DB.getDriversByType("XL");

//        CREATE A NEW INSTANCE OF THE Random CLASS
        Random ran = new Random();

//        GET A NUMBER BETWEEN 0 AND THE LENGTH OF drivers - 1
        int randomIndex = ran.nextInt(drivers.length-1);

//        THE driver AT THIS INDEX IS THE ASSIGNED DRIVER
        this.driver = drivers[randomIndex];

        return drivers[randomIndex];
    }

    @Override
    public void completePayment() {
//        GET THE DRIVERS CURRENT BALANCE
        double driversBalance = driver.getCash();

//        ADD THE COST OF THE TRIP TO THE DRIVERS BALANCE
        driver.setCash(driversBalance + this.price);
        System.out.println("Adding R" + (int)this.price + " to driver account. Account bal: R" + driver.getCash());

//        GET THE PASSENGERS CURRENT BALANCE
        double passengerBalance = passenger.getCash();

//        MINUS THE COST OF THE TRIP FROM THE PASSENGERS BALANCE
        passenger.setCash(passengerBalance - this.price);
        System.out.println("Deducting R" + (int)this.price + " from passenger account. Account bal: R" + passenger.getCash());
    }

    public static double MyGETRequest(String startPoint, String endPoint) throws IOException, MalformedURLException {
//      CREATE A URL, BUT DYNAMICALLY ADD THE startPoint AND THE endPoint
        URL URL = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + startPoint + ",SA&destinations=" + endPoint + ",SA&departure_time=now&key=AIzaSyD43u7-fJZ3-CPc5GShnl09KpEzUacoFZg");

        String readLine = null;

//        CREATE A NEW INSTANCE OF THE HttpURLConnection CLASS
        HttpURLConnection connection = (HttpURLConnection) URL.openConnection();

//        SET THE REQUEST METHOD TO "GET" BECAUSE WE WANT TO GET INFORMATION FROM THE SERVER
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

//        CREATE A NEW INSTANCE OF THE UberRide CLASS
        UberRide uberRide = new UberRide();
        double distance = 0.0;

//        IF responseCode IS EQUAL TO 200, THEN THE REQUEST WAS SUCCESSFULL
        if (responseCode == HttpURLConnection.HTTP_OK) {
//            CREATE A BufferedReader TO READ THE INCOMING DATA
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();

//            ON EACH ITERATION, WE CHECK IF THERE IS A SOMETHING TO READ ON THE LINE
            while ((readLine = bufferedReader.readLine()) != null) {
                response.append(readLine);
            }

//            CLOSE THE BufferedReader
            bufferedReader.close();

//            CONVERT THE responce TO A STRING
            String distanceAsString = uberRide.convertToString(response.toString());
            distance = Double.parseDouble(distanceAsString);
        } else {
            System.out.println("REQUEST ERROR");
        }

        return distance;
    }

    public String convertToString(String jsonString) {
//        MAKE A NEW INSTANCE OF THE GsonBuilder CLASS
        GsonBuilder gsonBuilder = new GsonBuilder();

//        setPrettyPrinting() CONFIGURES GSON TO OUTPUT JSON
        gsonBuilder.setPrettyPrinting();

//        create() CREATES A GSON THAT WE CAN CHANGE
        Gson gson = gsonBuilder.create();

//        CONVERT THE GSON TO A JsonObject
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("rows");

        JsonElement jsonElement;

//        CREATE AN Iterator THAT WE WILL USE TO LOOP
        Iterator<JsonElement> iterator = jsonArray.iterator();

//        THIS WILL LET US ACCESS EVERY ITEM INSIDE THE ARRAY
        while(((Iterator<?>) iterator).hasNext()) {
//            NOW THAT WE ARE INSIDE THE "rows" ARRAY...
            jsonElement = iterator.next();

//            WE RE ASSIGN THE jsonArray TO THE "elements" ARRAY SO WE CAN LOOP THROUGH IT
            jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("elements");
        }

//        NOW WE LOOP THROUGH THE "elements" ARRAY
        iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            jsonElement = iterator.next();

//            NOW, WE DRILL INSIDE TO GET THE DISTANCE
            jsonObject = jsonElement.getAsJsonObject().get("distance").getAsJsonObject();
        }

        return jsonObject.get("value")+"";
    }


}
