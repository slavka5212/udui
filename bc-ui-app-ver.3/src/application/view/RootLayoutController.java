package application.view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class RootLayoutController {
    @FXML
    private MenuBar mainMenu;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootLayoutController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// initialize menu
    	Menu searchMenu = new Menu("Search");
    	searchMenu.getItems().add(new MenuItem("Map coloring"));
    	searchMenu.getItems().add(new MenuItem("Route finding"));
    	mainMenu.getMenus().add(searchMenu);
    	
    	Menu gamesMenu = new Menu("Games");
    	gamesMenu.getItems().add(new MenuItem("8 puzzle"));
    	gamesMenu.getItems().add(new MenuItem("N queens"));
    	mainMenu.getMenus().add(gamesMenu);
    	
    	Menu setMenu = new Menu("Settings");
    	setMenu.getItems().add(new MenuItem("Language"));
    	setMenu.getItems().add(new SeparatorMenuItem());
    	setMenu.getItems().add(new MenuItem("About"));
    	mainMenu.getMenus().add(setMenu);
    	
        // Prepare left-most 'File' drop-down menu  
//        Menu fileMenu = new Menu("File");
//        fileMenu.getItems().add(new MenuItem("New"));
//        fileMenu.getItems().add(new MenuItem("Open"));
//        fileMenu.getItems().add(new MenuItem("Save"));
//        fileMenu.getItems().add(new MenuItem("Save As"));
//        fileMenu.getItems().add(new SeparatorMenuItem());
//        fileMenu.getItems().add(new MenuItem("Exit"));
        
//        MenuItem item1 = new MenuItem("About");
//        item1.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//                System.out.println("About");
//            }
//        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
//        personTable.setItems(mainApp.getPersonData());
    }
}
