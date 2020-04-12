//class that will take the customer details from the previous database.

import java.util.ArrayList;
import java.util.List;

public class Passenger {
    static int objcount = 0;
    {objcount++;}
    private String firstName;                   //first name of the customer
    private String surName;                     //second name of the customer
    public String name;                         //return string of the joined first name and the surname
    private int secondsInQueue;                 //seconds
    private int seatNumber;                     //getting the seat number


    //=========================================================================================
    public String getFirstName(){
        //name = firstName + surName;             //concatenating the first name and the surname.
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    //Return the customer name read by the getName method and assign to the parameters.
    public void setName(String name, String surname){
        this.firstName = name;
        this.surName = surname;
    }

    //Get the seat number
    public int getSeatNumber() {
        return seatNumber;
    }

    //Return the seat number
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    //Get the seconds in the queue - (Getter)
    public int getSecondsInQueue() {
        return secondsInQueue;
    }

    //Set the custom seconds in a queue - (Setter)
    public void setSecondsInQueue(int secondsInQueue) {
        this.secondsInQueue = secondsInQueue;
    }
}
