package com.company;

public class Person {
    private String name;
    private String surname;
    private String phoneNumber;

    private double cash;


    public Person(String name, String surname, String phoneNumber, double cash) {
        this.name = name;
        this.cash = cash;
        this.surname = surname;
        this.phoneNumber = phoneNumber;

    }

    public void setName(String name) {
        name = name;
    }

    public String getName() {
        return this.name;
    }


    public void setSurname(String surname) {
        surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }


    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    public void setCash(Double cash) {
        cash = cash;
    }

    public double getCash() {
        return this.cash;
    }

    public String toString() {
        return this.name + " " + this.surname + "'s phone number is: " + this.phoneNumber + ". He's not rich but he has R" + this.cash;
    }

}
