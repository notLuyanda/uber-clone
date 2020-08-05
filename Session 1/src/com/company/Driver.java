package com.company;

public class Driver extends Person {
    private Car car;
    private String licenceNumber;

    public Driver(Car car, String licenceNumber, String name, String surname, String phoneNumber, double cash) {
        super(name, surname, phoneNumber, cash);

        this.car = car;
        this.licenceNumber = licenceNumber;

    }


    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return this.car;
    }


    public void setLicenceNumber(String licenceNumber) { this.licenceNumber = licenceNumber; }

    public String getLicenceNumber() {
        return this.licenceNumber;
    }

    public String toString() {
        return super.toString() + " and I am a driver.";
    }
}
