/*
    Bryan Dingman
    Create two user defined sized rectangles and display if they are intersecting
*/


import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFX_RectangleIntersections extends Application
{
    private TextField tfRec1X = new TextField();
    private TextField tfRec1Y = new TextField();
    private TextField tfRec2X = new TextField();
    private TextField tfRec2Y = new TextField();
    private TextField tfRec1H = new TextField();
    private TextField tfRec1W = new TextField();
    private TextField tfRec2W = new TextField();
    private TextField tfRec2H = new TextField();
    private Button btnCreate = new Button("Create Rectangles");
    private Text notif = new Text(50,50,"");
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) 
    {         
        // Create a way of remembering our rectangles so we can DESTORY THEM LATER.
        // MWAHAHAHAHA >:D
        ArrayList<Rectangle> rectList = new ArrayList<>();
        
        // Create our controls group
        GridPane controls = new GridPane();
        controls.setHgap(5);
        controls.setVgap(5);

        // Add controls for rectangle 1
        controls.add(new Label("Rectangle 1"), 0, 0);
        controls.add(new Label("X Coord: "), 0, 1);
        controls.add(tfRec1X, 1, 1);
        controls.add(new Label("Y Coord: "), 0, 2);
        controls.add(tfRec1Y, 1, 2);
        controls.add(new Label("Width: "), 0, 3);
        controls.add(tfRec1W, 1, 3);
        controls.add(new Label("Height: "), 0, 4);
        controls.add(tfRec1H, 1, 4);
        
        // Add controls for rectangle 2
        controls.add(new Label("Rectangle 2"), 0, 5);
        controls.add(new Label("X Coord: "), 0, 6);
        controls.add(tfRec2X, 1, 6);
        controls.add(new Label("Y Coord: "), 0, 7);
        controls.add(tfRec2Y, 1, 7);
        controls.add(new Label("Width: "), 0, 8);
        controls.add(tfRec2W, 1, 8);
        controls.add(new Label("Height: "), 0, 9);
        controls.add(tfRec2H, 1, 9);
        controls.add(btnCreate, 1, 10);
        
        // Create the pane for our retangles
        Pane recPane = new Pane();
        
        // Create our main pane
        BorderPane pane = new BorderPane();
        pane.setRight(controls);
        pane.setLeft(recPane);
        pane.setBottom(notif);
        
        // Our button EH
        btnCreate.setOnAction(e -> 
        {
            // If we've already have rectangles....
            if (rectList.size() > 0)
            {
                // DESTROY THEMMMMMMMM
                for (Rectangle x : rectList)
                {
                    recPane.getChildren().remove(x);
                }
                rectList.clear();
                // Reset our notification
                notif.setText("");
            }
            
            // Create rectangle 1
            Rectangle rect1 = new Rectangle
            (
                Double.parseDouble(tfRec1X.getText()),
                Double.parseDouble(tfRec1Y.getText()),
                Double.parseDouble(tfRec1W.getText()),
                Double.parseDouble(tfRec1H.getText())
            );
            
            // Create Rectangle 2
            Rectangle rect2 = new Rectangle
            (
                Double.parseDouble(tfRec2X.getText()),
                Double.parseDouble(tfRec2Y.getText()),
                Double.parseDouble(tfRec2W.getText()),
                Double.parseDouble(tfRec2H.getText())    
            );
            
            // Add em!
            recPane.getChildren().addAll(rect1,rect2);
            rectList.add(rect1);
            rectList.add(rect2);
            
            // Check to see if rectangle 1 intersects rectangle 2
            if (
                rect1.intersects(
                    Double.parseDouble(tfRec2X.getText()),
                    Double.parseDouble(tfRec2Y.getText()),
                    Double.parseDouble(tfRec2W.getText()),
                    Double.parseDouble(tfRec2H.getText())
                )
            )
            {
                // Notify them!
                notif.setText("The two rectangles are intersecting!");
            }
        });

        /*==================================================
                    DISPLAY THE STAGES
        ====================================================*/
        // Create our scene
        Scene scene = new Scene(pane, 700, 400);
        primaryStage.setTitle("Draw Rectangles"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage 
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
