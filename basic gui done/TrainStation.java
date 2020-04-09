import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TrainStation extends Application {
    public static Passenger[] tempDetails = new Passenger[42];                                                          //array to store read passenger details
    public static Passenger[] waitingRoom = new Passenger[42];

    public static void main(String[] args) {launch(args);}                                                              //main method

    public static void readNames() throws IOException {                                                                 //Read the text file from the previous project.
        int index = 0;
            String line;                                                                                                //String variable to store reading line value.
            BufferedReader reader = new BufferedReader(new FileReader("CustomerFullName.txt"));                //creating a new buffer reader and passing the file path.
            while ((line = reader.readLine()) != null) {                                                                //until for a empty line.
                String[] lineIntoSplit;                                                                                 //Creating a array to store reading line.
                lineIntoSplit = line.split(":", 2);                                                         //reading the line and spiting the string from ":" to two index positions.
                if (lineIntoSplit.length >= 2) {
                    String key = lineIntoSplit[0];                                                                      //assigning o index position to a string to add as the key value for the Hash Map.
                    String value = lineIntoSplit[1];                                                                    //assigning 1 index position to a string to add as the value for the Hash Map.
                    Passenger passenger = new Passenger();                                                              //Creating a passenger object
                    passenger.setName(key,value);                                                                       //Adding customer first name and the surname to the passenger object
                    tempDetails[index] = passenger;                                                                     //Adding passenger to the temporary details array
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
                arrIndex++;
            }

        }
        //======adding data to waiting room array from the temporary array
        int count = 0;                                                                                                  //count for get objects from the temp details object array
        int object_count = 0;                                                                                           //object count for the when adding newly created objects to the array
        for(ArrayList<Integer> seat : bookedSeats){                                                                     //for each seat number it will perform the bellow action
            for(int eachseat : seat){                                                                                   //getting seat one by one from the 2D seats array
                Passenger passenger = new Passenger();                                                                  //will create passenger object for one seat
                passenger.setName(tempDetails[count].getFirstName(),tempDetails[count].getSurName());                   //setting the name for the new passenger object by getting data from the temporary details array
                passenger.setSeatNumber(eachseat);                                                                      //setting the seat number for the new object
                waitingRoom[object_count] = passenger;                                                                  //adding the passenger object to the waiting room array list
                object_count++;                                                                                         //incrementing the object count
            }
            count++;                                                                                                    //incrementing the object count in temporary details array
        }
        reader.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
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
            System.out.print("Enter Q to save and exit : ");

            String userOption = scanner.next();
            switch (userOption.toLowerCase()) {
                case "a":
                    PassengerQueue.gui(waitingRoom);
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
