import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PassengerQueue {
    private int first;
    private int last;
    private int maxStayInQueue;
    private int maxLength;
    public static final int CAPACITY = 42;

    public static Passenger[] queueArray = new Passenger[CAPACITY];

    public static void  add (Passenger[] passengers){
        System.out.println("Opening Gui.........");

        //Creating the Panes for window
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //Adding panes to the main pane
        borderPane.setCenter(gridPane);

        //creating a new scene
        Scene scene = new Scene(borderPane, 700,700);
        Stage addStage = new Stage();


       //get the count of the objects in the array
        int numOfObjects = 0;
        for(int i = 0; TrainStation.waitingRoom[i]!=null;i++){
            System.out.println(TrainStation.waitingRoom[i].getSurName());
            numOfObjects++;
        }
        System.out.println(numOfObjects);


        //Button creation
        int row = 0;
        int coloumn = 0;
        for(int i = 1; i<=numOfObjects; i++){
            String passengerName = "";
            Button button = new Button(TrainStation.waitingRoom[row].getFirstName());
            button.setPrefSize(140, 30);
            gridPane.add(button,coloumn,row);
            row++;
        }

        //Setting the window
        addStage.setTitle("Denuwara Manike Train Station");
        addStage.setScene(scene);
        addStage.showAndWait();
    }

    public static void gui (Passenger[] passengers) {

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
                tableView_TrainQueue.getItems().add(TrainStation.waitingRoom[0]);
                tableView_TrainQueue.getItems().add(TrainStation.waitingRoom[1]);
                tableView_TrainQueue.getItems().add(TrainStation.waitingRoom[2]);
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

}

