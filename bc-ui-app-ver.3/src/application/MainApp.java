package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.embed.swing.SwingNode;

import aima.gui.applications.AimaDemoApp;
import aima.gui.applications.search.games.*;
import application.view.VisualViewsController;

public class MainApp extends Application {
	private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UIApp");

        
        initRootLayout();

        showVisualViews();
    }
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the visual views inside the root layout.
     */
    public void showVisualViews() {
        try {
            // Load visual views.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/VisualViews.fxml"));

            AnchorPane visualViews = (AnchorPane) loader.load();
            
//            Button fxbutton = new Button("Fx button");
//            
//            final SwingNode swingNode = new SwingNode();
//            SwingFx.createSwingContent(swingNode);
//            
//            visualViews.getChildren().add(fxbutton);

//            leftPane.setStyle("-fx-background: #0000FF;");
//            leftAnchor.setCenterShape(true);
//            leftPane.getChildren().add(leftAnchor);
//            


          

            // Set visual views into the center of root layout.
            rootLayout.setCenter(visualViews);
            
            // Give the controller access to the main app.
            VisualViewsController controller = loader.getController();
            controller.setMainApp(this);            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
