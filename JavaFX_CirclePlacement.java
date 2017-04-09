/*
    Bryan Dingman
    Lab 7a1
    Place a circle where the user clicks and remove it if they right click
*/
package lab7;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class dingman_lab7a1 extends Application
{
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) 
    {   
        // Create our pane
        Pane pane = new Pane();
        
        // Create our array list for remembering circles
        ArrayList<Circle> listOfCircles = new ArrayList<>();
        
        // Mouse button event clicked
        pane.setOnMouseClicked(e ->
        {
            // Left mouse 
            if (e.getButton() == MouseButton.PRIMARY) 
            {
                // Add our circle
                Circle circle = new Circle(7);
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                pane.getChildren().add(circle);
                
                // Remember it!
                listOfCircles.add(circle);
            }
            else if (e.getButton() == MouseButton.SECONDARY) 
            {
                // Right mouse
                
                // Interator
                int num = 0;
                boolean found = false;
                
                // Loop through our circles to see if it exists
                for (Circle woot : listOfCircles)
                {
                    // Does the current circle have it?
                    if (woot.contains(e.getX(), e.getY()))
                    {
                        
                        // Remove circle
                        pane.getChildren().remove(woot);
                        found = true;
                        break;
                    }
                    num++;
                }
                
                // Did we find it? 
                if (found)
                {
                    // Remove it!
                    listOfCircles.remove(num);
                }
                    
            }
        });
        
        /*==================================================
                    DISPLAY THE STAGES
        ====================================================*/
        // Create our scene
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Button Click Thingy"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage 
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
