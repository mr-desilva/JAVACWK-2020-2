import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;


public class TrainStation extends Application {
    public static Passenger[] tempDetails = new Passenger[42]; //temporary array list to store object data from the previous project.
    private static Passenger[] waitingRoom = new Passenger[42];//array to store objects of people who has booked multiple seats.
    private static PassengerQueue trainQueue = new PassengerQueue();//Object of Circular array.
    private static ArrayList<Passenger> waitingList = new ArrayList<>();//array list to save waiting list objects.
    private static ArrayList<Passenger> boardedPassengers = new ArrayList<>();//array list to save boarded passenger objects.


    public static void main(String[] args) {//main method to launch the primary stage.
        launch(args);
    }
    //Method to read the booked names from the previous project text file.
    public static void readNames() throws IOException {
        int index = 0; //variable to count the index position of the tempDetails array.
        String line; //String variable to store string value of the read line.
        BufferedReader reader = new BufferedReader(new FileReader("CustomerFullName.txt")); //creating a new buffer reader and passing the file name to read.
        while ((line = reader.readLine()) != null) { //until for a empty line.
            String[] lineIntoSplit; //Creating a array to store reading line.
            lineIntoSplit = line.split(":", 2); //reading the line and spiting the string from ":" to two index positions.
            if (lineIntoSplit.length >= 2) {
                String key = lineIntoSplit[0]; //assigning o index position to a string as the FirstName.
                String value = lineIntoSplit[1]; //assigning 1 index position to a string as the SurName.
                Passenger passenger = new Passenger(); //Creating a passenger object
                passenger.setName(key, value); //Adding customer first name and the surname to the passenger object.
                tempDetails[index] = passenger; //Adding passenger to the temporary details array
                index++; //incrementing the index position of the tempDetails array.
            }
        }
        reader.close(); //Closing the Reader.
    }

    //Method to read the booked seats from the previous project text file.
    public static void readSeats() throws IOException {
        ArrayList<ArrayList<Integer>> bookedSeats = new ArrayList<>();
        String line; //String variable to store string value of the read line.
        BufferedReader reader = new BufferedReader(new FileReader("CustomerBookings.txt")); //creating a new buffer reader and passing the file name to read.
        while ((line = reader.readLine()) != null) { //until for a empty line.
            ArrayList<Integer> tempArray = new ArrayList<>(); //temp array for adding the seats numbers once at a time for a passenger.
            String[] lineIntoSplit; //Creating a array to store reading line.
            lineIntoSplit = line.split(":", 2); //reading the line and spiting the string from ":" to two index positions.
            if (lineIntoSplit.length >= 2) {
                String key = lineIntoSplit[0]; //assigning o index position to a string to add as the key value for the Hash Map.
                String value = lineIntoSplit[1]; //assigning 1 index position to a string to add as the value for the Hash Map.
                String[] seats = value.split(",");  //adding the first line key values to a String array by separating ","
                for (int i = 0; i < seats.length; i++) {  //for each string separated by , will added to a array and cast to Integer.
                    tempArray.add(Integer.parseInt(seats[i])); //adding the seat number from the text file to the temp integer array.
                }
                bookedSeats.add(tempArray); //Adding all the booked seats to a 2D array.
            }
        }
        //======adding data to waiting room array from the temporary array==========

        int count = 0; //count for get objects from the temp details object array.
        int object_count = 0; //object count for the when adding newly created objects to the array.
        for (ArrayList<Integer> seat : bookedSeats) { //for each seat number it will perform the bellow action.
            for (int eachseat : seat) { //getting seat one by one from the 2D seats array.
                Passenger passenger = new Passenger(); //will create passenger object for one seat.
                passenger.setName(tempDetails[count].getFirstName(), tempDetails[count].getSurName()); //setting the name for the new passenger object by getting data from the temporary details array.
                passenger.setSeatNumber(eachseat); //setting the seat number for the new object.
                waitingRoom[object_count] = passenger; //adding the passenger object to the waiting room array list.
                object_count++; //incrementing the object count.
            }
            count++; //incrementing the object count in temporary details array.
        }
        //Sorting Passenger objects by the seat number and adding to the final waiting list array.
        int added = 0; //Variable to count the number of passenger objects in the waiting room array excepting null.
        for(int i = 0; waitingRoom[i]!=null;i++){
            added++;
        }

        Passenger temp; //temporary passenger object to continue the sort.
        for(int i = 0; i<added-1; i++){  //main for loop to count from the front in the array.
            for(int j = 0; j<added -1-i; j++){ //sub loop to count from the rear in the array.
                int first = waitingRoom[j].getSeatNumber(); //getting the first object seat number and assigning to a variable.
                int second = waitingRoom[j+1].getSeatNumber(); //getting the first object from rear, seat number and assigning to a variable.
                if( first > second){ //comparing the seat number value.
                    temp = waitingRoom[j]; //assigning rear object to a temporary object.
                    waitingRoom[j] = waitingRoom[j+1];  //assigning the object one before the rear to the rear position.
                    waitingRoom[j+1] = temp; //assigning temporary object to the rear.
                }
            }
        }

        //Adding the sorted passenger object to the waiting list array list.
        for (Passenger passenger : waitingRoom) {
            waitingList.add(passenger);
        }
        reader.close(); //closing the file reader.
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

        //Adding element to the Layout right section.
        Label label_trainQueueTotal = new Label("Total in train queue : " + trainQueue.getMaxLength());
        gridPaneRight.add(label_trainQueueTotal, 0, 8);

        //Updating the train queue list details
        int queueCount = trainQueue.getFirst();
        for (int i = 0; i < trainQueue.getMaxLength(); i++) {
            listView_TrainQueue.getItems().add("TQ - " + ". " + trainQueue.getQueueArray()[queueCount].getFirstName() + " " + trainQueue.getQueueArray()[queueCount].getSurName() + "\t\t\t" + trainQueue.getQueueArray()[queueCount].getSeatNumber());
            queueCount++;
        }

        //Getting the number of objects in the waiting list without null objects.
        int numOfObjects = 0;
        for (int i = 0; waitingList.get(i) != null; i++) {
            numOfObjects++;
        }

        //Adding elements to the Layout left section.
        Label label_waitingRoomTotal = new Label("Total in waiting room : " + numOfObjects);
        gridPaneLeft.add(label_waitingRoomTotal, 0, 8);

        //Updating the waiting room list details.
        int passenger_count = 1;
        for (int i = 0; i < numOfObjects; i++) {
            listView_WaitingRoom.getItems().add(" " + passenger_count + ". " + waitingList.get(i).getFirstName() + " " + waitingList.get(i).getSurName() + "\t\t\t" + waitingList.get(i).getSeatNumber());
            passenger_count++;
        }

        //Adding passengers from waiting room to train queue by a randomly generated number using 1 six sided dice.
        if (numOfObjects != 0) { //checking whether the waiting room is empty before adding to the train queue.
            int max = 6; //maximum limit for the random generating number.
            int min = 1; //minimum limit for the random generating number.
            int finalNumOfObjects1 = numOfObjects; //variable to update the last value of the updated number of objects in the waiting room.

            WaitingRoom_add.setOnAction(new EventHandler<ActionEvent>() { //action event for the add button in the gui.
                @Override
                public void handle(ActionEvent event) {
                    //if (trainQueue.getMaxLength() == PassengerQueue.CAPACITY)
                    if (trainQueue.isFull()) { //Checking whether the train queue is full.
                        Alert alertFull = new Alert(Alert.AlertType.INFORMATION); //alert box to show train queue is full.
                        alertFull.setContentText("Train Queue is Full"); //error message for the alert box.
                        alertFull.showAndWait(); //show and wait until the user action.
                    } else { //if the train queue is not full.
                        //Generate a random number using 1 six sided dice
                        int random_int = (int) (Math.random() * (max - min + 1) + min); //setting the max and the min range to the random number and assigning.
                        Label label_addingCount = new Label("Moving " + random_int + " Passengers to train queue"); //creating a label of moving number of passengers to the train queue.
                        gridPaneBottom.add(label_addingCount, 0, 0); //adding the label to the pain.

                        //checking the num of passengers to move with the amount from the waiting room.
                        if (finalNumOfObjects1 <= max) {
                            random_int = finalNumOfObjects1;
                        }
                        //Moving passengers to train queue from waiting list and stopping the moving process if the train queue becomes full.
                        for (int movePassengers = 0; movePassengers < random_int; movePassengers++) {
                            if (trainQueue.isFull()) {
                                break;
                            }
                            for (int h = 0; h < 1; h++) { //loop to add the passengers.
                                if (waitingList.get(h) != null) { // until founding null objects.
                                    trainQueue.add(waitingList.get(h)); //adding passenger objects to the train queue.
                                    //adding elements to the train queue list view.
                                    listView_TrainQueue.getItems().add("TQ - " + waitingList.get(h).getFirstName() + " " + waitingList.get(h).getSurName() + "\t\t\t" + waitingList.get(h).getSeatNumber());
                                    waitingList.remove(h); //removing passenger object from the waiting list array list.
                                    listView_WaitingRoom.getItems().clear(); //Clearing the waiting room list view to update the data.

                                    //recounting the number of objects in the waiting list after the adding process.
                                    int numOfObjects = 0;
                                    for (int i = 0; waitingList.get(i) != null; i++) {
                                        numOfObjects++;
                                    }
                                    //adding a label with number of objects to the gui pane to display.
                                    label_waitingRoomTotal.setText("Total in waiting room : " + numOfObjects);

                                    //Updating the waiting room list view.
                                    int passenger_count = 1; //variable to show the list number.
                                    for (int i = 0; i < numOfObjects; i++) {
                                        listView_WaitingRoom.getItems().add(" " + passenger_count + ". " + waitingList.get(i).getFirstName() + " " + waitingList.get(i).getSurName() + "\t\t\t" + waitingList.get(i).getSeatNumber());
                                        passenger_count++;
                                    }
                                }
                                label_trainQueueTotal.setText("Total in train queue : " + trainQueue.getMaxLength()); //adding a label to show the total in train queue.
                                WaitingRoom_add.setDisable(true); //setting the add button status disable after the action.
                            }
                        }
                    }
                }
            });
        } else { //if the waiting room is empty.
            WaitingRoom_add.setDisable(true); //disabling the add button.
            Label label_waitingRoomEmpty = new Label("Waiting Room is Empty");  //creating a label to show waiting room is empty.
            gridPaneBottom.add(label_waitingRoomEmpty, 0, 0);   //adding label to the pane.
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
        Label boardRoom_Table = new Label("\t\t\t\tBoarded Passengers");
        boardRoom_Table.setStyle("-fx-font-weight: bold");

        //Fixed Buttons
        Button TrainQueue_done = new Button("Done");

        //Main Pane
        BorderPane borderPane_ViewTrainQueue = new BorderPane ();

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
        gridPaneCenter.setAlignment(Pos.CENTER);
        //gridPaneCenter.setPadding(new Insets(0, 0, 0, 100));

        //Boarded Passengers list view
        ListView listView_Boarded = new ListView();
        gridPaneRight.add(boardRoom_Table,0,0);
        gridPaneRight.add(listView_Boarded,0,1);
        listView_Boarded.setPrefSize(400,500);
        Label boarded_Passengers_Heading = new Label("Passenger Name         Seat Number");
        boarded_Passengers_Heading.setStyle("-fx-font-weight: bold");
        listView_Boarded.getItems().add(boarded_Passengers_Heading);
        gridPaneRight.setAlignment(Pos.CENTER_RIGHT);

        //Adding details to the boarded passenger list view
        for(Passenger passenger : boardedPassengers){
            listView_Boarded.getItems().add(" " + passenger.getFirstName() + " " + passenger.getSurName() + "\t\t\t" + passenger.getSeatNumber());
        }

        //Adding details to Waiting room list view
        //Getting the number of objects in the waiting list
        int numOfObjects = 0;
        for (int i = 0; waitingList.get(i) != null; i++) {
            numOfObjects++;
        }
        //Updating the waiting room list details
        int passenger_count = 1;
        for (int i = 0; i < numOfObjects; i++) {
            listView_WaitingRoom.getItems().add(" " + waitingList.get(i).getFirstName() + " " + waitingList.get(i).getSurName() + "\t\t\t" + waitingList.get(i).getSeatNumber());
            passenger_count++;
        }

        //Creating the stage
        Stage stage_ViewTrainQueue = new Stage();
        //creating the scene and adding the main pane
        Scene scene_ViewTrainQueue = new Scene(borderPane_ViewTrainQueue, 1240, 700);

        //Getting the Train Queue Details

        //Display the train queue with 21 slots
        int queuecount = 0; //variable to count the passenger object index positions from the train queue.
        int slotCount = 1;  //variable to count the slot number.
        for (Passenger passenger : trainQueue.getQueueArray()) { //taking each passenger object from the train queue.
            if (passenger == null) { //taking null objects as empty slots.
                listView_TrainQueue.getItems().add("\t\t\t\tEmpty\t\t\t\t" + slotCount); //adding the null objects as empty slot to the train queue list view.
            } else { //for passengers in the train queue will display the following details into the train queue list view.
                listView_TrainQueue.getItems().add(" " + trainQueue.getQueueArray()[queuecount].getFirstName() + " " + trainQueue.getQueueArray()[queuecount].getSurName() + "\t\t\t" + trainQueue.getQueueArray()[queuecount].getSeatNumber() + "\t\t\t\t" + slotCount);
            }
            slotCount++; //incrementing the slot count.
            queuecount++; //incrementing the queue count.
        }
        //Displaying the remaining slots (42) empty.
        for(int slots = 22; slots<=42; slots++){
            listView_TrainQueue.getItems().add("\t\t\t\tEmpty\t\t\t\t" + slots);
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

        //Getting the passenger name from the console
        System.out.print("Enter the Passenger Name : ");
        String passengerName = scanner.next();

        //Getting the seat number from the console
        System.out.print("Enter the seat number : ");
        int passengerSeat = scanner.nextInt();

        //Checking whether the entered details are matching.
        int objIndex = 0; //variable to count the index position of the object that needs to be deleted.
        for (Passenger passenger : trainQueue.getQueueArray()) { //for every object in the train queue array.
            try {
                if (passengerName.equals(passenger.getFirstName()) & passengerSeat == passenger.getSeatNumber()) { //Checking whether the both name and the seats are matching.
                    System.out.println("matched details found : " + passenger.getFirstName() + "and the seat number " + passenger.getSeatNumber()); //if matched will perform a message to the console.
                    System.out.println("Passenger "+passenger.getFirstName()+" with seat number "+passenger.getSeatNumber()+" Successfully deleted from the train queue"); //Output message for the console about the deleted record.
                    trainQueue.getQueueArray()[objIndex] = null;  //making the object null.
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println("Enter valid details"); //Error message for invalid data.
                break;
            }
            objIndex++; //counting the index position of the object in the array.
        }

        //Sorting passenger objects by moving null values to the end of array.
        for (int index = 0; index < trainQueue.getQueueArray().length; index++) { //loop will continue till the length of the array.
            if (trainQueue.getQueueArray()[index] == null) { //if the object is null.
                for (int last = index + 1; last < trainQueue.getQueueArray().length; last++) { //loop to move null values to the end of array.
                    trainQueue.getQueueArray()[last - 1] = trainQueue.getQueueArray()[last];  //assigning the null object to the last index position of the array.
                }
                trainQueue.getQueueArray()[trainQueue.getQueueArray().length - 1] = null;   //Reducing the length of the train queue to re-adjust the inspecting the null object range.
                break;
            }
        }
        trainQueue.setMaxLength(trainQueue.getMaxLength()-1);  //reducing the length of the train queue circular array after the deletion.
        trainQueue.decrease(); //moving the index of last variable in circular array to reverse after the deletion.
    }


    //==========================save method=============================================================================

    public void saveData() throws IOException {
        //writing objects to the text file.
        File file = new File("trainqueue.txt");
        FileOutputStream fileOutputStream; //to write data into the file.
        ObjectOutputStream objectOutputStream;

        try{
            fileOutputStream = new FileOutputStream(file); //creating a object of file output stream and passing the file path.
            objectOutputStream = new ObjectOutputStream(fileOutputStream);  //creating a object of output stream and passing the file output stream object.
            for(Passenger passenger : trainQueue.getQueueArray()){ //for each passenger object in the train queue.
                if(passenger!=null){ //avoiding the null objects
                    objectOutputStream.writeObject(passenger); //passing the object to the output stream to write.
                }
            }
        } catch (FileNotFoundException e){ //exception error for file not founding.
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //==========================load method=============================================================================

    public static void loadData() throws IOException {
        //Reading the objects from a text file
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        int index = 0; //variable to count the index position of the train queue array objects.

        try {
            fileInputStream = new FileInputStream("trainqueue.txt"); //creating a object of file input stream and passing the file path.
            objectInputStream = new ObjectInputStream(fileInputStream);  //creating a object of input stream and passing the file output stream object.

            while (true) {
                Passenger passenger = (Passenger) objectInputStream.readObject(); //creating a new passenger object and assigning read object from the text file.
                trainQueue.add(passenger); //adding the read passenger object to the train queue.
            }
        } catch (FileNotFoundException e) { //exception error for file not founding.
            e.printStackTrace();
        } catch (EOFException e) {
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //removing passengers that are already in the waiting room
        int fixed = 0; //fixed index position to get the first index of the waiting list after the deletion.
        for (int count = 0; waitingList.get(count) != null; count++) {
            if (trainQueue.getQueueArray()[index] != null) {
                if (trainQueue.getQueueArray()[index].getSeatNumber() == waitingList.get(fixed).getSeatNumber()) { //matching the same passenger objects by the seat number.
                    waitingList.remove(fixed); //removing the object from the waiting list.
                }
                index++; //incrementing the index position of the train queue objects.
            }
        }
    }



    //========================== R - simulation method==================================================================

    public static void simulation() throws IOException {
        //Fixed Labels
        Label GuiReport_Heading = new Label("Denuwara Manike - Train Station\n  Train Queue - Status Report");
        GuiReport_Heading.setStyle("-fx-font-weight: bold");

        //Fixed Buttons
        Button GuiReport_done = new Button("Done");

        //Tables for the layout
        TableView tableView_GuiReport =new TableView();

        //List view for Train Queue details
        ListView list_queue = new ListView();
        ListView list_queue_2 = new ListView();

        //Main Pane
        BorderPane borderPane_GuiReport = new BorderPane();

        //Sub Panes
        GridPane gridPaneTop = new GridPane();
        GridPane gridPaneBottom = new GridPane();
        GridPane gridPaneLeft = new GridPane();
        GridPane gridPaneRight = new GridPane();

        //Adding Sub Panes to the main Pane
        borderPane_GuiReport.setTop(gridPaneTop);
        borderPane_GuiReport.setBottom(gridPaneBottom);
        borderPane_GuiReport.setLeft(gridPaneLeft);
        borderPane_GuiReport.setRight(gridPaneRight);

        //Adding elements to the top
        gridPaneTop.add(GuiReport_Heading,0,0);
        gridPaneTop.setAlignment(Pos.CENTER);

        //Adding elements to the bottom
        gridPaneBottom.add(GuiReport_done,0,2);
        gridPaneBottom.setAlignment(Pos.CENTER);

        //Adding element to the left
        gridPaneLeft.add(tableView_GuiReport,0,0);
        gridPaneLeft.setAlignment(Pos.CENTER);

        //Adding elements to the right
        gridPaneRight.add(list_queue,0,0);
        gridPaneRight.add(list_queue_2,1,0);
        gridPaneRight.setAlignment(Pos.CENTER);
        list_queue.setPrefSize(500,650);;
        list_queue_2.setPrefSize(220,650);

        //Resizing the table view
        tableView_GuiReport.setPrefSize(700,650);


        //===========Train queue Report - Passenger object Details==============================
        TableColumn column1T = new TableColumn<>("First Name");
        column1T.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn column2T = new TableColumn<>("Last Name");
        column2T.setCellValueFactory(new PropertyValueFactory<>("surName"));

        TableColumn column3T = new TableColumn<>("Seat Number");
        column3T.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

        TableColumn coloumn4T = new TableColumn<>("Delay Time");
        coloumn4T.setCellValueFactory(new PropertyValueFactory<>("secondsInQueue"));;

        //adding columns to the table.
        tableView_GuiReport.getColumns().add(column1T);
        tableView_GuiReport.getColumns().add(column2T);
        tableView_GuiReport.getColumns().add(column3T);
        tableView_GuiReport.getColumns().add(coloumn4T);
        //Removing the additional column by default.
        tableView_GuiReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //===========Train queue Report - Passenger object Details==============================
        //Headings of the list views.
        Label TrainQueue_Listheading = new Label("Length of the Queue        Max stay in Queue        Min Stay in Queue");
        TrainQueue_Listheading.setStyle("-fx-font-weight: bold");
        list_queue.getItems().add(TrainQueue_Listheading);

        Label TrainQueue_Listheading_2 = new Label("Average time in the Queue");
        TrainQueue_Listheading_2.setStyle("-fx-font-weight: bold");
        list_queue_2.getItems().add(TrainQueue_Listheading_2);

        int noOfPassengers= trainQueue.getMaxLength(); //assigning the value of length of the train queue.


        //Setting the Length of the Queue attained
        int max = 6; //maximum limit fot the random value.
        int min = 1; //minimum limit for the random value.
        int dice_1;
        int dice_2;
        int dice_3;
        int total = 0;  //total value of all three dices
        int nextTotal = 0; //adding total waiting time.
        int queueCount = 0; //variable to count the index of the train queue objects.
        int maxQueueTime; //variable to count the maximum waiting time for each passenger.
        int minQueueTime; //variable to count the minimum waiting time for each passenger.

        //Generating the random numbers.
        for(Passenger passenger : trainQueue.getQueueArray()){
            if(passenger!=null){
                dice_1 = (int) (Math.random() * (max - min + 1) + min);
                dice_2 = (int) (Math.random() * (max - min + 1) + min);
                dice_3 = (int) (Math.random() * (max - min + 1) + min);

                //getting the max stay in queue and the min stay in the queue
                //getting the maximum queue time
                if(dice_1>=dice_2 & dice_1>=dice_3){
                    maxQueueTime = dice_1;
                }
                else if (dice_2>=dice_1 & dice_2>=dice_3){
                    maxQueueTime = dice_2;
                }
                else {
                    maxQueueTime = dice_3;
                }

                //getting the minimum queue time
                if(dice_1<=dice_2 & dice_1<=dice_3){
                    minQueueTime = dice_1;
                }
                else if (dice_2<=dice_1 & dice_2<=dice_3){
                    minQueueTime = dice_2;
                }
                else {
                    minQueueTime = dice_3;
                }

                //adding the total waiting time.
                total = dice_1 + dice_2 + dice_3 + nextTotal;
                passenger.setSecondsInQueue(total); //setting the total waiting time for each passenger.
                tableView_GuiReport.getItems().add(trainQueue.getQueueArray()[queueCount]); //adding passenger details to the table view.
                list_queue.getItems().add("\t\t"+noOfPassengers+"\t\t\t\t\t"+maxQueueTime+"\t\t\t\t\t"+minQueueTime); //adding the passenger details to the list views.
                queueCount++; //incrementing the train queue object index.
                boardedPassengers.add(passenger);   //adding the passenger to the boarded array list.
                trainQueue.remove(); //removing the passenger from the circular array.
            }
            nextTotal = total; //adding the total waiting time of a passenger to a variable.
            trainQueue.setAvg_timeInQueue(nextTotal);   //setting the average waiting time in the queue for each customer.
        }
        //Calculating the average waiting time of all passengers and adding data to the list view.
        for(Passenger passenger : trainQueue.getQueueArray()){
            if(passenger!=null) {
                list_queue_2.getItems().add("\t\t\t"+trainQueue.getAvg_timeInQueue()/noOfPassengers);
            }
        }

        //Writing data report to a text file.
        FileWriter fileWriter; //Creating a file writer.
        fileWriter = new FileWriter("trainQueueReport.txt"); //Passing the file path to the file writer.
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); //creating a buffer reader.
        bufferedWriter.write("Denuwara Manike - TrainStation - Board Report"); //writing the heading of the tex file.
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        int passengerCount = 1; //variable to print the list number.
        for (Passenger passenger : trainQueue.getQueueArray()) {
            if(passenger!=null){
            String passengerName = passenger.getFirstName()+" "+passenger.getSurName(); //assigning passenger name to a string to print.
            int seatNumbers = passenger.getSeatNumber(); //assigning seat number to print.
            int secondsInQueue = passenger.getSecondsInQueue(); //assigning seconds spent in the queue to print.
            int avgTime = trainQueue.getAvg_timeInQueue()/noOfPassengers; //assigning average spent time in the queue.
                //writing the data to the text file line by line.
            bufferedWriter.write(passengerCount+"."+"\t"+"Name = "+passengerName+"\t\t"+"Seats = "+seatNumbers+"\t"+"Seconds in Queue = "+secondsInQueue+"\t"+"Average Time = "+avgTime);
            bufferedWriter.newLine();
            passengerCount++; //incrementing the list number.
        } }
        bufferedWriter.close(); //closing the buffer writer.

        trainQueue.setFirst(0); //resetting the first index position of the circular array.
        trainQueue.setLast(0); //resetting the last index position of the circular array.

        //Creating the stage
        Stage stage_GuiReport = new Stage();

        //creating the scene and adding the main pane
        Scene scene_GuiReport = new Scene(borderPane_GuiReport, 1400, 800);

        //OnClick Actions
        GuiReport_done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage_GuiReport.close();
            }
        });

        //Setting the Stage and the scene
        stage_GuiReport.setTitle("Train Station");
        stage_GuiReport.setScene(scene_GuiReport);
        stage_GuiReport.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        readNames(); //loading the customer names from the previous project.
        readSeats(); //loading the seat numbers from the previous project.
        menu: //label to break the console menu.
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
            switch (userOption.toLowerCase()) { //setting user input to the lower case.
                case "a":
                    AddToQueue(); //calling the add method.
                    break;
                case "v":
                    viewTrainQueue(); //calling the view method.
                    break;
                case "d":
                    deletePassengerQueue(); //calling the delete passenger method
                    break;
                case "s":
                    saveData(); //calling the save data method.
                    break;
                case "l":
                    loadData(); //calling load data method.
                    break;
                case "r":
                    simulation(); //calling the simulation method.
                    break;
                case "q":
                    break menu; //quiting the application.
                default:
                    System.out.println("====Enter a valid menu option====\n");
            }
        }
    }
}
