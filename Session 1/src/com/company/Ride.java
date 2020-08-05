package com.company;

public abstract class Ride {
    private String pickupLocation;
    private String destination;

    public abstract double calculateDistance(String startPoint, String endPoint);

    public abstract double calculateCost(String startPoint, String endPoint);

    public abstract Driver assignDriver();

    public abstract void completePayment();
}