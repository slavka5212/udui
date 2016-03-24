package application;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JFrame;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import aima.gui.applications.search.games.*;
import application.view.RootLayoutController;
import application.view.VisualViewsController;

public class MainApp extends Application {
	private Stage primaryStage;
    private BorderPane rootLayout;
    private RootLayoutController rootController;
    private VisualViewsController viewsController;
    
    JComponent currPanel;
   	PrintStream outStream;
   	final SwingNode swingNode = new SwingNode();
    

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("UIApp");

        
        initRootLayout();

        showVisualViews();
        
    	// add items to Menu
		Menu gamesMenu = new Menu("Games");
		rootController.addItem(gamesMenu, EightPuzzleApp.class);
		rootController.addItem(gamesMenu, NQueensApp.class);
		rootController.getMainMenu().getMenus().add(gamesMenu);
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
            
            // Give the controller access to the main app.
            rootController = loader.getController();
            rootController.setMainApp(this);
           
            
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

            // Set visual views into the center of root layout.
            rootLayout.setCenter(visualViews);
            
            // Give the controller access to the main app.
            viewsController = loader.getController();
            viewsController.setMainApp(this);            
            
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
	
	/**
	 * Implements an action listener which starts an agent application.
	 * 
	 */
	public class AppStarter implements EventHandler<ActionEvent> {
		Class<?> appClass;

		public AppStarter(Class<?> ac) {
			appClass = ac;
		}

		@Override
		public void handle(ActionEvent event) {
			try {
				if (currPanel != null){
//					Alert alert = new Alert(AlertType.INFORMATION);
//			        alert.setTitle(appClass.getSimpleName());
//			        alert.showAndWait();
			        viewsController.getLeftPane().getChildren().remove(swingNode);
				}
				System.setOut(outStream);
				Object instance = appClass.newInstance();
				Method m = appClass.getMethod("constructApplicationFrame",
						new Class[0]);
				JFrame af = (JFrame) m.invoke(instance, (Object[]) null);
				currPanel = (JComponent) af.getContentPane().getComponent(0);
				af.getContentPane().remove(currPanel);
				
				// set currPanel as swingNode
				swingNode.setContent(currPanel);
				// add swingNode to leftPane
				System.out.println("swingNode " + swingNode);
				viewsController.getLeftPane().getChildren().add(swingNode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
