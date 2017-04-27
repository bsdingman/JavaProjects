/*
    Bryan Dingman
    Create a program that allows the user to draw lines on the screen using their arrow keys. 
    It's a JavaFX Etch-A-Sketch!
*/

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class JavaFX_Etch-A-Sketch extends Application
{  
    // Declare our inital x and y starting points
    private int x = 150;
    private int y = 150;
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) 
    {   
        // Create our pane
        Pane pane = new Pane();
        
        // Create our path
        Path line = new Path(new MoveTo(x, y));
        
        // Set path attributes
        line.setStrokeWidth(1);
        line.setStroke(Color.BLACK);

        // Add our line to the pane
        pane.getChildren().add(line);
  
        // Set EH for arrow keys
        line.setOnKeyPressed(e -> 
        {
            // Make sure the only key pressed is an arrow key
            if (e.getCode().isArrowKey()) 
            {
                // Adjust the coord by 2 based on direction
                if (e.getCode() == KeyCode.UP)
                {
                    y -= 2;
                }
                else if (e.getCode() == KeyCode.DOWN)
                {
                    y += 2;
                }
                else if (e.getCode() == KeyCode.LEFT)
                {
                    x -= 2;
                }
                else if (e.getCode() == KeyCode.RIGHT)
                {
                    x += 2;
                }
                
                // "Move" the line!
                line.getElements().add(new LineTo(x, y));
            }
        });

        /*==================================================
                    DISPLAY THE STAGES
        ====================================================*/
        // Create our scene
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Etch-a-Sketch"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage 
        
        // Very important for paths!
        line.requestFocus();
    }
       
    public static void main(String[] args) 
    {
        launch(args);
    }
}
