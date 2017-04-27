/*
    Bryan Dingman
    Display a circle and sliders to control the color and opacity of said circle. Updating automatically
*/


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFX_CirclesAndSliders extends Application
{
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) 
    {   
        // Create our circle
        Circle circle = new Circle(50);
        circle.setCenterX(100);
        circle.setCenterY(75);

        // Create the red slider, min 0, max 255, default 0
        Slider redSlider = new Slider(0,255,0);
        redSlider.setOrientation(Orientation.HORIZONTAL);
        redSlider.setShowTickLabels(true);
        redSlider.setShowTickMarks(true);
        redSlider.setValue(0);
        
        // Create the green slider, min 0, max 255, default 0
        Slider greenSlider = new Slider(0,255,0);
        greenSlider.setOrientation(Orientation.HORIZONTAL);
        greenSlider.setShowTickLabels(true);
        greenSlider.setShowTickMarks(true);
        greenSlider.setValue(0);
        
        // Create the blue slider, min 0, max 255, default 0
        Slider blueSlider = new Slider(0,255,0);
        blueSlider.setOrientation(Orientation.HORIZONTAL);
        blueSlider.setShowTickLabels(true);
        blueSlider.setShowTickMarks(true);
        blueSlider.setValue(0);
        
        // Create the opacity slider, min 0, max 1, default 1
        // This slider is also OP
        Slider opSlider = new Slider(0.0,1.0,1.0);
        opSlider.setOrientation(Orientation.HORIZONTAL);
        opSlider.setShowTickLabels(true);
        opSlider.setShowTickMarks(true);
        opSlider.setMajorTickUnit(0.25f);
        opSlider.setBlockIncrement(0.1f);

        // Create a circle in a pane
        Pane circlePane = new Pane();
        circlePane.getChildren().add(circle);
        
        // Create our pane for our sliders, with labels
        GridPane sliderPane = new GridPane();
        sliderPane.add(new Text("Red"), 0, 0);
        sliderPane.add(redSlider, 1, 0);
        sliderPane.add(new Text("Green"), 0, 1);
        sliderPane.add(greenSlider, 1, 1);
        sliderPane.add(new Text("Blue"), 0, 2);
        sliderPane.add(blueSlider, 1, 2);
        sliderPane.add(new Text("Opacity"), 0, 3);
        sliderPane.add(opSlider, 1, 3);
        
        // Create a border pane to hold text and scroll bars
        BorderPane pane = new BorderPane();
        pane.setCenter(circlePane);
        pane.setBottom(sliderPane);
        
        // Add listeners for all of our sliders to adjust the color/opacity every time
        redSlider.valueProperty().addListener(list -> 
            circle.setFill(Color.rgb((int)redSlider.getValue(),(int)greenSlider.getValue(),(int)blueSlider.getValue(),opSlider.getValue()))
        );
        
        greenSlider.valueProperty().addListener(list -> 
            circle.setFill(Color.rgb((int)redSlider.getValue(),(int)greenSlider.getValue(),(int)blueSlider.getValue(),opSlider.getValue()))
        );
        
        blueSlider.valueProperty().addListener(list -> 
            circle.setFill(Color.rgb((int)redSlider.getValue(),(int)greenSlider.getValue(),(int)blueSlider.getValue(),opSlider.getValue()))
        );
        
        opSlider.valueProperty().addListener(list -> 
            circle.setFill(Color.rgb((int)redSlider.getValue(),(int)greenSlider.getValue(),(int)blueSlider.getValue(),opSlider.getValue()))
        );

        
        
        /*==================================================
                    DISPLAY THE STAGES
        ====================================================*/
        // Create our scene
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Change the Color"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage 
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
