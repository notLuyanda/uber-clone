package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Database {
    private String filePath = "C:/Users/UWC Samsung 18/Desktop/Zaio/Java/Webinar Sessions/Session 1/bin/";

     int numberOfXL;
     int numberOfX;

    private Driver[] drivers;

    public int countLinesInFile(String filename) {
        int amountOfLines = 0;

//        CREATE A BufferedReader THAT WE WILL USE TO READ THE cvc FILE
//        WE WRAP THIS IN A try...catch BLOCK TO HANDLE ERRORS
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + filename));
            String fileLine = null;

//            ON EACH ITERATION, THIS CHECKS TO SEE IF NEXT LINE EXISTS
            while ((fileLine = bufferedReader.readLine()) != null) {
                amountOfLines++;
            }

            bufferedReader.close();
        }
//        CATCH THE ERROR IF THE FILE DOESN'T EXIST
        catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        AT THE END, i WILL CONTAIN THE AMOUNT OF LINES IN THE FILE
        return amountOfLines;
    }

    public void printArray(Driver[] drivers) {
        System.out.print("[ ");

        for(int i = 0; i <= drivers.length; i++) {
            System.out.println(drivers[i]);
            System.out.println(", ");
            System.out.println("");
        }

        System.out.print(" ]");
    }

    public void sortDriversByType() {
        try {
//        CREATE A BufferedReader THAT WE WILL USE TO READ THE cvc FILE
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + "drivers.csv"));

            String fileLine = null;
            int i = 0;
            this.drivers = new Driver[countLinesInFile("drivers.csv")-1];
            while ((fileLine = bufferedReader.readLine()) != null) {

//                SINCE THE FIRST LINE IS THE HEADING..WE HAVE TO SKIP IT
                if(i > 0) {
//                    SPLIT THE data AT THE "," SO THAT WE HAVE ACCESS TO EACH "CELL"
                    String[] data = fileLine.split(",");

//                    CREATE A NEW INSTANCE OF THE Car CLASS WITH THE INFORMATION FROM THE data ON EACH ITERATION
                    Car car = new Car(data[5].trim(), data[6].trim(), data[7].trim(), data[8].trim());

//                    CREATE A NEW INSTANCE OF THE Driver CLASS WITH THE INFORMATION FROM THE data ON EACH ITERATION
                    Driver driver = new Driver(car, data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), Integer.parseInt(data[4].trim()));

//                    HERE, WE SEPARATE THE DRIVERS BY TYPE
                    if(data[8].trim().equals("X")) {
                        this.numberOfX++;
                    }
                    if(data[8].trim().equals("XL")) {
                        this.numberOfXL++;
                    }

                    this.drivers[i-1] = driver;
                }

                i++;
            }

            bufferedReader.close();
        }
//        CATCH THE EXCEPTION IF IT HAPPENS
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Driver[] getDriversByType(String type) {
        Driver[] Xdrivers = new Driver[this.numberOfX];
        Driver[] XLdrivers = new Driver[this.numberOfXL];

        int xIndex = 0;
        int xlIndex = 0;

//        CREATE A LOOP FOR THE LENGTH OF THE drivers ARRAY
        for(int i=0; i<this.drivers.length; i++) {
//            PUT EACH DRIVER IN THE APPROPRIATE CATEGORY
            if(this.drivers[i].getCar().getType().equals("X")) {
                Xdrivers[xIndex] = this.drivers[i];
                xIndex++;
            }

            if(this.drivers[i].getCar().getType().equals("XL")) {
                XLdrivers[xlIndex] = this.drivers[i];
                xlIndex++;
            }
        }

//        RETURN WHICHEVER ARRAY THAT THE USER WANTS
        if(type.equals("X")) { return Xdrivers; }

        return XLdrivers;
    }
}
