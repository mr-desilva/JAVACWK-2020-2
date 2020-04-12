import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class TrainStation extends Application {
    public static Passenger[] tempDetails = new Passenger[42];                                                          //array to store read passenger details
    private static Passenger[] waitingRoom = new Passenger[42];             //private
    private static PassengerQueue trainQueue = new PassengerQueue();        //private
    private static ArrayList<Passenger> waitinglist = new ArrayList<>();    //private

    public static ArrayList<Passenger> test = new ArrayList<>();


    public static void main(String[] args) {
        launch(args);
    }                                                              //main method

    public static void readNames() throws IOException {                                                                 //Read the text file from the previous project.
        int index = 0;
        String line;                                                                                                    //String variable to store reading line value.
        BufferedReader reader = new BufferedReader(new FileReader("CustomerFullName.txt"));                    //creating a new buffer reader and passing the file path.
        while ((line = reader.readLine()) != null) {                                                                    //until for a empty line.
            String[] lineIntoSplit;                                                                                     //Creating a array to store reading line.
            lineIntoSplit = line.split(":", 2);                                                             //reading the line and spiting the string from ":" to two index positions.
            if (lineIntoSplit.length >= 2) {
                String key = lineIntoSplit[0];                                                                          //assigning o index position to a string to add as the key value for the Hash Map.
                String value = lineIntoSplit[1];                                                                        //assigning 1 index position to a string to add as the value for the Hash Map.
                Passenger passenger = new Passenger();                                                                 //Creating a passenger object
                passenger.setName(key, value);                                                                       //Adding customer first name and the surname to the passenger object
                tempDetails[index] = passenger;                                                                     //Adding passenger to the temporary details array
                index++;                                                                                            //incrementing the index position of the waiting room
            }
        }
        reader.close();                                                                                                 //Closing the Reader.
    }

    public static void readSeats() throws IOException {
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
                for (int i = 0; i < seats.length; i++) {                                                                       //for each string separated by , will added to a array and cast to Integer.
                    temparray.add(Integer.parseInt(seats[i]));                                                          //adding the seat number from the text file to the temp integer array.
                }
                bookedSeats.add(temparray);                                                                             //Adding all the booked seats to a 2D array
                arrIndex++;
            }

        }
        //======adding data to waiting room array from the temporary array
        int count = 0;                                                                                                  //count for get objects from the temp details object array
        int object_count = 0;                                                                                           //object count for the when adding newly created objects to the array
        for (ArrayList<Integer> seat : bookedSeats) {                                                                     //for each seat number it will perform the bellow action
            for (int eachseat : seat) {                                                                                   //getting seat one by one from the 2D seats array
                Passenger passenger = new Passenger();                                                                  //will create passenger object for one seat
                passenger.setName(tempDetails[count].getFirstName(), tempDetails[count].getSurName());                   //setting the name for the new passenger object by getting data from the temporary details array
                passenger.setSeatNumber(eachseat);                                                                      //setting the seat number for the new object
                waitingRoom[object_count] = passenger;                                                                  //adding the passenger object to the waiting room array list
                object_count++;                                                                                         //incrementing the object count
            }
            count++;                                                                                                    //incrementing the object count in temporary details array
        }
        //Adding Passenger object to a waiting list
        for (Passenger passenger : waitingRoom) {
            waitinglist.add(passenger);
        }
        reader.close();
    }


    //========================Add Method================================================================================
    public static void AddToQueue() {

        //Buttons for the layout
        Button WaitingRoom_add = new Button("Add to Train Queue");
        Button WaitingRoom_Done = new Button("Done");

        //Labels for the layout
        Label WaitingRoom_Heading = new Label("Denuwara Manike - Train Station\n                  Waiting Room");
        WaitingRoom_Heading.setStyle("-fx-font-weight: bold");
        Label waitingRoom_table = new Label("\t\t\t\tWaiting Room");
        waitingRoom_table.setStyle("-fx-font-weight: bold");
        Label trainQueue_table = new Label("\t\t\t\tTrain Queue");
        trainQueue_table.setStyle("-fx-font-weight: bold");

        //Main Pane
        BorderPane borderPane_WaitingRoom = new BorderPane();

        //Sub panes to the layout
        GridPane gridPaneTop = new GridPane();
        GridPane gridPaneBottom = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneRight = new GridPane();

        //setting the grid panes to border pane
        borderPane_WaitingRoom.setTop(gridPaneTop);
        borderPane_WaitingRoom.setBottom(gridPaneBottom);
        borderPane_WaitingRoom.setLeft(gridPaneLeft);
        borderPane_WaitingRoom.setRight(gridPaneRight);

        //Adding elements to the Layout Top section
        gridPaneTop.add(WaitingRoom_Heading, 0, 0);
        gridPaneTop.setAlignment(Pos.CENTER);

        //Adding elements to the Layout Bottom section
        gridPaneBottom.add(WaitingRoom_add, 0, 1);
        gridPaneBottom.add(WaitingRoom_Done, 1, 1);
        gridPaneBottom.setAlignment(Pos.CENTER);

        //List view
        Label WaitingRoom_Listheading = new Label("Passenger Name         Seat Number");
        WaitingRoom_Listheading.setStyle("-fx-font-weight: bold");
        Label TrainQueue_Listheading = new Label("Passenger Name         Seat Number");
        TrainQueue_Listheading.setStyle("-fx-font-weight: bold");

        //Waiting room list view
        ListView listView_WaitingRoom = new ListView();
        gridPaneLeft.add(waitingRoom_table, 0, 0);
        gridPaneLeft.add(listView_WaitingRoom, 0, 1);
        listView_WaitingRoom.setPrefSize(400, 500);
        listView_WaitingRoom.getItems().add(WaitingRoom_Listheading);

        //Train Queue list view
        ListView listView_TrainQueue = new ListView();
        gridPaneRight.add(trainQueue_table, 0, 0);
        gridPaneRight.add(listView_TrainQueue, 0, 1);
        listView_TrainQueue.setPrefSize(400, 500);
        listView_TrainQueue.getItems().add(TrainQueue_Listheading);

        //Creating the stage
        Stage stage_WaitingRoom = new Stage();
        //creating the scene and adding the main pane
        Scene scene_WaitingRoom = new Scene(borderPane_WaitingRoom, 1240, 700);

        //Getting the number of objects in the train queue

        Label label_trainQueueTotal = new Label("Total in train queue : " + trainQueue.getMaxLength());
        gridPaneRight.add(label_trainQueueTotal, 0, 8);

        //Updating the train queue list details
        int queuecount = 0;
        for (int i = 0; i < trainQueue.getMaxLength(); i++) {
            listView_TrainQueue.getItems().add("TQ - " + ". " + trainQueue.getQueueArray()[queuecount].getFirstName() + " " + trainQueue.getQueueArray()[queuecount].getSurName() + "\t\t\t" + trainQueue.getQueueArray()[queuecount].getSeatNumber());
            queuecount++;
        }

        //Getting the number of objects in the waiting list
        int numOfObjects = 0;
        for (int i = 0; waitinglist.get(i) != null; i++) {
            numOfObjects++;
        }

        Label label_waitingRoomTotal = new Label("Total in waiting room : " + numOfObjects);
        gridPaneLeft.add(label_waitingRoomTotal, 0, 8);

        //Updating the waiting room list details
        int passenger_count = 1;
        for (int i = 0; i < numOfObjects; i++) {
            listView_WaitingRoom.getItems().add(" " + passenger_count + ". " + waitinglist.get(i).getFirstName() + " " + waitinglist.get(i).getSurName() + "\t\t\t" + waitinglist.get(i).getSeatNumber());
            passenger_count++;
        }



        if (numOfObjects != 0) {
            //Adding passengers to the train queue from the waiting room using a 1 siz sided dice
            int max = 6;
            int min = 1;
            int finalNumOfObjects = numOfObjects;
            int finalNumOfObjects1 = numOfObjects;
            WaitingRoom_add.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (trainQueue.getMaxLength() == PassengerQueue.CAPACITY) {
                        Alert alertFull = new Alert(Alert.AlertType.INFORMATION);
                        alertFull.setContentText("Train Queue is Full");
                        alertFull.showAndWait();
                    } else {
                        //Generate a random number using 1 six sided dice
                        Random random = new Random();
                        int random_int = (int) (Math.random() * (max - min + 1) + min);
                        System.out.println(random_int);
                        Label label_addingCount = new Label("Moving " + random_int + " Passengers to train queue");
                        gridPaneBottom.add(label_addingCount, 0, 0);

                        //checking the num of passengers to move
                        if (finalNumOfObjects1 <= max) {
                            random_int = finalNumOfObjects1;
                        }
                        //Moving passengers to train queue from waiting list
                        for (int movePassengers = 0; movePassengers < random_int; movePassengers++) {
                            if (trainQueue.isFull()) {
                                break;
                            }
                            for (int h = 0; h < 1; h++) {
                                if (waitinglist.get(h) != null) {
                                    trainQueue.add(waitinglist.get(h));
                                    listView_TrainQueue.getItems().add("TQ - " + waitinglist.get(h).getFirstName() + " " + waitinglist.get(h).getSurName() + "\t\t\t" + waitinglist.get(h).getSeatNumber());
                                    waitinglist.remove(h);
                                    listView_WaitingRoom.getItems().clear();

                                    int numOfObjects = 0;
                                    for (int i = 0; waitinglist.get(i) != null; i++) {
                                        numOfObjects++;
                                    }


                                    label_waitingRoomTotal.setText("Total in waiting room : " + numOfObjects);

                                    //List view Passenger from the waiting room
                                    int passenger_count = 1;
                                    for (int i = 0; i < numOfObjects; i++) {
                                        listView_WaitingRoom.getItems().add(" " + passenger_count + ". " + waitinglist.get(i).getFirstName() + " " + waitinglist.get(i).getSurName() + "\t\t\t" + waitinglist.get(i).getSeatNumber());
                                        passenger_count++;
                                    }
                                }
                                label_trainQueueTotal.setText("Total in train queue : " + trainQueue.getMaxLength());
                                WaitingRoom_add.setDisable(true);

                            }
                        }
                    }
                }
            });
        } else {
            WaitingRoom_add.setDisable(true);
            Label label_waitingRoomEmpty = new Label("Waiting Room is Empty");
            gridPaneBottom.add(label_waitingRoomEmpty, 0, 0);
        }

        //OnClick Actions
        WaitingRoom_Done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage_WaitingRoom.close();
            }
        });

        //Setting the Stage and the scene
        stage_WaitingRoom.setTitle("Train Station");
        stage_WaitingRoom.setScene(scene_WaitingRoom);
        stage_WaitingRoom.showAndWait();
    }

    //==============================================View Method=========================================================
    public static void viewTrainQueue() {

        //Fixed Labels
        Label WaitingRoom_Heading = new Label("Denuwara Manike - Train Station\n                  Train Queue");
        WaitingRoom_Heading.setStyle("-fx-font-weight: bold");
        Label waitingRoom_table = new Label("\t\t\t\tWaiting Room");
        waitingRoom_table.setStyle("-fx-font-weight: bold");
        Label trainQueue_table = new Label("\t\t\t\tTrain Queue");
        trainQueue_table.setStyle("-fx-font-weight: bold");

        //Fixed Buttons
        Button TrainQueue_done = new Button("Done");

        //Main Pane
        BorderPane borderPane_ViewTrainQueue = new BorderPane();

        //Sub panes to the layout
        GridPane gridPaneTop = new GridPane();
        GridPane gridPaneBottom = new GridPane();
        GridPane gridPaneCenter = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneRight = new GridPane();

        //setting the grid panes to border pane
        borderPane_ViewTrainQueue.setTop(gridPaneTop);
        borderPane_ViewTrainQueue.setBottom(gridPaneBottom);
        borderPane_ViewTrainQueue.setCenter(gridPaneCenter);
        borderPane_ViewTrainQueue.setLeft(gridPaneLeft);
        borderPane_ViewTrainQueue.setRight(gridPaneRight);

        //Adding elements to the Layout Top section
        gridPaneTop.add(WaitingRoom_Heading, 0, 0);
        gridPaneTop.setAlignment(Pos.CENTER);

        //Adding elements to the Layout Bottom
        gridPaneBottom.add(TrainQueue_done, 0, 0);
        gridPaneBottom.setAlignment(Pos.CENTER);

        //Waiting Room list view
        Label WaitingRoom_Listheading = new Label("Passenger Name         Seat Number");
        WaitingRoom_Listheading.setStyle("-fx-font-weight: bold");
        //Waiting room list view
        ListView listView_WaitingRoom = new ListView();
        gridPaneLeft.add(waitingRoom_table, 0, 0);
        gridPaneLeft.add(listView_WaitingRoom, 0, 1);
        listView_WaitingRoom.setPrefSize(400, 500);
        listView_WaitingRoom.getItems().add(WaitingRoom_Listheading);
        gridPaneLeft.setAlignment(Pos.CENTER_LEFT);

        //Train Queue list view
        ListView listView_TrainQueue = new ListView();
        gridPaneCenter.add(trainQueue_table, 0, 0);
        gridPaneCenter.add(listView_TrainQueue, 0, 1);
        listView_TrainQueue.setPrefSize(400, 500);
        Label TrainQueue_Listheading = new Label("Passenger Name\t  Seat Number\t\tSlot No.");
        TrainQueue_Listheading.setStyle("-fx-font-weight: bold");
        listView_TrainQueue.getItems().add(TrainQueue_Listheading);
        gridPaneCenter.setAlignment(Pos.CENTER_LEFT);
        gridPaneCenter.setPadding(new Insets(0, 0, 0, 100));

        //Adding details to Waiting room list view
        //Getting the number of objects in the waiting list
        int numOfObjects = 0;
        for (int i = 0; waitinglist.get(i) != null; i++) {
            numOfObjects++;
        }
        //Updating the waiting room list details
        int passenger_count = 1;
        for (int i = 0; i < numOfObjects; i++) {
            listView_WaitingRoom.getItems().add(" " + waitinglist.get(i).getFirstName() + " " + waitinglist.get(i).getSurName() + "\t\t\t" + waitinglist.get(i).getSeatNumber());
            passenger_count++;
        }

        //Creating the stage
        Stage stage_ViewTrainQueue = new Stage();
        //creating the scene and adding the main pane
        Scene scene_ViewTrainQueue = new Scene(borderPane_ViewTrainQueue, 1240, 700);

        //Getting the Train Queue Details======================================================

        //Display the train queue with 42 slots
        int queuecount = 0;
        int slotCount = 1;
        for (Passenger passenger : trainQueue.getQueueArray()) {
            if (passenger == null) {
                listView_TrainQueue.getItems().add("\t\t\t\tEmpty\t\t\t\t" + slotCount);
                slotCount++;
            } else {
                listView_TrainQueue.getItems().add(" " + trainQueue.getQueueArray()[queuecount].getFirstName() + " " + trainQueue.getQueueArray()[queuecount].getSurName() + "\t\t\t" + trainQueue.getQueueArray()[queuecount].getSeatNumber() + "\t\t\t\t" + slotCount);
                slotCount++;
            }
            queuecount++;
        }

        //Onclick Action
        TrainQueue_done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage_ViewTrainQueue.close();
            }
        });

        //Setting the Stage and the scene
        stage_ViewTrainQueue.setTitle("Train Station");
        stage_ViewTrainQueue.setScene(scene_ViewTrainQueue);
        stage_ViewTrainQueue.showAndWait();
    }


    //====================Delete Method=================================================================================
    public static void deletePassengerQueue() {

        //console menu
        Scanner scanner = new Scanner(System.in);
        System.out.println("======Delete Passenger from the Train Queue======");
        System.out.println("==Please Enter the Passenger details bellow==");

        //Getting the passenger details from the console
        System.out.print("Enter the Passenger Name : ");
        String passengerName = scanner.next();

        System.out.print("Enter the seat number : ");
        int passengerSeat = scanner.nextInt();

        //Checking whether the entered details are matching.
        int objIndex = 0;
        for (Passenger passenger : trainQueue.getQueueArray()) {                                                        //for every object in the train queue array
            try {
                if (passengerName.equals(passenger.getFirstName()) & passengerSeat == passenger.getSeatNumber()) {      //Checking whether the both name and the seats are matching.
                    System.out.println("matched details found : " + passenger.getFirstName() + "and the seat number " + passenger.getSeatNumber());     //if matched will perform a message to the console.
                    System.out.println("Passenger "+passenger.getFirstName()+" with seat number "+passenger.getSeatNumber()+" Successfully deleted from the train queue");
                    trainQueue.getQueueArray()[objIndex] = null;                                                           //making the object null.
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("Enter valid details");                                                              //Error message for invalid data.
                break;
            }
            objIndex++;                                                                                                 //counting the index position of the object in the array.
        }

        //Sorting passenger objects by moving null values to the end in array.
        for (int j = 0; j < trainQueue.getQueueArray().length; j++) {
            if (trainQueue.getQueueArray()[j] == null) {
                for (int k = j + 1; k < trainQueue.getQueueArray().length; k++) {
                    trainQueue.getQueueArray()[k - 1] = trainQueue.getQueueArray()[k];
                }
                trainQueue.getQueueArray()[trainQueue.getQueueArray().length - 1] = null;
                break;
            }
        }

        trainQueue.setMaxLength(trainQueue.getMaxLength()-1);                                                           //reducing the length for deletion.
        trainQueue.decrease();                                                                                          //moving the index of last variable.
    }













    //==================================================================================================================
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
                    AddToQueue();
                    break;
                case "v":
                    viewTrainQueue();
                    break;
                case "d":
                    deletePassengerQueue();
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
