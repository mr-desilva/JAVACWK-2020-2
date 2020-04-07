import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainStation {
    public static ArrayList<Passenger> waitingRoom = new ArrayList<>(42);                                                 //array to store read passenger details


    public static void readNames() throws IOException {                                                                 //Read the text file from the previous project.
        int index = 0;
            String line;                                                                                                //String variable to store reading line value.
            BufferedReader reader = new BufferedReader(new FileReader("CustomerFullName.txt"));                //creating a new buffer reader and passing the file path.
            while ((line = reader.readLine()) != null) {                                                                //until for a empty line.
                String[] lineIntoSplit;                                                                                 //Creating a array to store reading line.
                lineIntoSplit = line.split(":", 2);                                                        //reading the line and spiting the string from ":" to two index positions.
                if (lineIntoSplit.length >= 2) {
                    String key = lineIntoSplit[0];                                                                      //assigning o index position to a string to add as the key value for the Hash Map.
                    String value = lineIntoSplit[1];                                                                    //assigning 1 index position to a string to add as the value for the Hash Map.
                    Passenger passenger = new Passenger();                                                              //Creating a passenger object
                    passenger.setName(key,value);                                                                       //Adding customer first name and the surname to the passenger object
                    waitingRoom.add(index,passenger);                                                                   //Adding passenger to the waiting room
                    index++;                                                                                            //incrementing the index position of the waiting room
                }
            }
            reader.close();                                                                                             //Closing the Reader.
        }

    public static void readSeats() throws IOException{
        ArrayList<ArrayList<Integer>> bookedSeats = new ArrayList<>();
        int arrIndex = 0;
        String line;                                                                                                    //String variable to store reading line value.
        BufferedReader reader = new BufferedReader(new FileReader("CustomerBookings.txt"));                    //creating a new buffer reader and passing the file path.
        while ((line = reader.readLine()) != null) {                                                                    //until for a empty line.
            ArrayList<Integer> temparray = new ArrayList<>();                                                           //temp array for adding the seats numbers once at a time for a passenger.
            String[] lineIntoSplit;                                                                                     //Creating a array to store reading line.
            lineIntoSplit = line.split(":", 2);                                                            //reading the line and spiting the string from ":" to two index positions.
            if (lineIntoSplit.length >= 2) {
                String key = lineIntoSplit[0];                                                                          //assigning o index position to a string to add as the key value for the Hash Map.
                String value = lineIntoSplit[1];                                                                        //assigning 1 index position to a string to add as the value for the Hash Map.


                String[] seats = value.split(",");                                                               //adding the first line key values to a String array by separating ","
                for(int i=0; i<seats.length;i++){                                                                       //for each string separated by , will added to a array and cast to Integer.
                    temparray.add(Integer.parseInt(seats[i]));                                                          //adding the seat number from the text file to the temp integer array.
                }
                bookedSeats.add(temparray);                                                                             //Adding all the booked seats to a 2D array
            }
        }
        for(Passenger passenger: waitingRoom){                                                                          //For each created passenger object it will perform the bellow action
            passenger.setSeatNumber(bookedSeats.get(arrIndex));                                                         //adding the booked seat numbers to the relevant passenger object
            arrIndex++;                                                                                                 //incrementing the passenger object array position.
        }
        System.out.println(bookedSeats);
        reader.close();
    }






    public static void main(String[] args) throws IOException {
        readNames();                                                                                                    //Loading the customer names from the data Structure.
        readSeats();
        menu:
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("------------------Welcome to--------------------");
            System.out.println("=========Denuwara Express Train Station=========");
            System.out.println("---Please select the below option to continue---\n");
            System.out.println("Enter A to add passenger to the train queue");
            System.out.println("Enter V to view the train queue");
            System.out.println("Enter D to delete passenger from the train queue");
            System.out.println("Enter S to store the data");
            System.out.println("Enter L to load the data");
            System.out.println("Enter R to run the simulation");
            System.out.print("Enter 1 to save and exit : ");

            String userOption = scanner.next();
            switch (userOption.toLowerCase()) {
                case "a":
                    System.out.println(waitingRoom);
                    break;
                case "v":
                    //view();
                    break;
                case "d":
                    //delete();
                    break;
                case "s":
                    //save();
                    break;
                case "l":
                    //load();
                    break;
                case "r":
                    //run();
                    break;
                case "q":
                    break menu;
            }
        }
    }
}
