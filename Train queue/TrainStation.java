import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public static PassengerQueue trainQueue = new PassengerQueue();

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


    //========================add method============================
    public static void AddToQueue () {

        //Buttons for the layout
        Button WaitingRoom_add = new Button("Add to Train Queue");
        Button WaitingRoom_Done = new Button("Done");

        //Labels for the layout
        Label WaitingRoom_Heading = new Label("Denuwara Manike - Train Station\n                  Waiting Room");
        WaitingRoom_Heading.setStyle("-fx-font-weight: bold");
        Label waitingRoom_table = new Label("                                              Waiting Room");
        waitingRoom_table.setStyle("-fx-font-weight: bold");
        Label trainQueue_table = new Label("                                               Train Queue");
        trainQueue_table.setStyle("-fx-font-weight: bold");

        //Tables for the layout
        TableView tableView_WaitingRoom = new TableView();
        TableView tableView_TrainQueue =new TableView();

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
        gridPaneTop.add(WaitingRoom_Heading,0,0);
        gridPaneTop.setAlignment(Pos.CENTER);

        //Adding elements to the Layout Bottom section
        gridPaneBottom.add(WaitingRoom_add,0,0);
        gridPaneBottom.add(WaitingRoom_Done,1,0);
        gridPaneBottom.setAlignment(Pos.CENTER);

        //Adding elements to the Layout Left section
        gridPaneLeft.add(waitingRoom_table,0,0);
        gridPaneLeft.add(tableView_WaitingRoom,0,1);
        tableView_WaitingRoom.setPrefSize(500,500);
        gridPaneLeft.setAlignment(Pos.CENTER);

        //Adding elements to the Layout Right section
        gridPaneRight.add(trainQueue_table,0,0);
        gridPaneRight.add(tableView_TrainQueue,0,1);
        tableView_TrainQueue.setPrefSize(500,500);
        gridPaneRight.setAlignment(Pos.CENTER);

        //=========WaitingRoom Table===========================================================
        TableColumn column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("surName"));

        TableColumn column3 = new TableColumn<>("Seat Number");
        column3.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

        tableView_WaitingRoom.getColumns().add(column1);
        tableView_WaitingRoom.getColumns().add(column2);
        tableView_WaitingRoom.getColumns().add(column3);

        //Adding elements to the table using for loop
        //get the count of the objects in the array
        int numOfObjects = 0;
        for(int i = 0; TrainStation.waitingRoom[i]!=null;i++){
            numOfObjects++;
        }

        //Adding Passenger Details to the Waiting room table
        int passengerCount = 0;
        for(int i = 1; i<=numOfObjects; i++){
            tableView_WaitingRoom.getItems().add(TrainStation.waitingRoom[passengerCount]);
            passengerCount++;
        }


        //=======TrainQueue Table============================================================
        TableColumn column1T = new TableColumn<>("First Name");
        column1T.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn column2T = new TableColumn<>("Last Name");
        column2T.setCellValueFactory(new PropertyValueFactory<>("surName"));

        TableColumn column3T = new TableColumn<>("Seat Number");
        column3T.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

        tableView_TrainQueue.getColumns().add(column1T);
        tableView_TrainQueue.getColumns().add(column2T);
        tableView_TrainQueue.getColumns().add(column3T);

        WaitingRoom_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                trainQueue.add(waitingRoom[0]);
                trainQueue.add(waitingRoom[1]);
                System.out.println("1st passenger is "+trainQueue.getQueueArray()[0].getFirstName());
            }
        });





        //Creating the stage
        Stage stage_WaitingRoom = new Stage();
        //creating the scene and adding the main pane
        Scene scene_WaitingRoom = new Scene(borderPane_WaitingRoom,1240,700);

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
