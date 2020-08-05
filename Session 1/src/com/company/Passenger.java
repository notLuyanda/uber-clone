package com.company;

public class Passenger extends Person  {
    private String email;

    public Passenger(String email, String name, String surname, String phoneNumber, double cash) {
        super(name, surname, phoneNumber, cash);

        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
