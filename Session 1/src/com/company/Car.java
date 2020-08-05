package com.company;

//  THE Car CLASS IMPLEMENTS THE Vehicle INTERFACE
public class Car implements Vehicle {
    private String type;
    private String color;
    private String model;
    private String numberPlate;

    private double baseRate;

    public Car(String color, String model, String numberPlate, String type) {
        this.type = type;
        this.color = color;
        this.model = model;
        this.baseRate = baseRate;
        this.numberPlate = numberPlate;

//      THIS IS TO DETERMINE THE baseRate OF THE RIDE, DEPENDING ON THE UBER SERVICE type REQUESTED
        if(type.equals("Uber X")) {
            this.baseRate = 20.00;
        } else {
            this.baseRate = 25.00;
        }
    }

//  INTERFACE METHOD OVERRIDES
    @Override
    public String getColor() { return this.color; }

    @Override
    public String getModel() { return this.model; }

    @Override
    public String getNumberPlate() { return this.numberPlate; }

    public String getType() { return this.type; }

    public double getBaseRate() { return this.baseRate; }

    public String toString() {
        return this.type + ": A " + this.color + ", " + this.model  + " with the following license plate - " + this.numberPlate;
    }

}
