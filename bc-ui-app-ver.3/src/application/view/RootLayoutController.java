package application.view;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class RootLayoutController {
	// Reference to the main application.
    private MainApp mainApp;
    @FXML
    private MenuBar mainMenu;
    private Menu searchMenu, gamesMenu, setMenu;
//    private static final long serialVersionUID = 1L;
//	JComponent currPanel;
//	PrintStream outStream;
	

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
    	assert getMainMenu() != null : "fx:id=\"mainMenu\" was not injected: check your FXML file 'RootLayout.fxml'.";
    	
    	// initialize menu
    	Menu searchMenu = new Menu("Search");
    	searchMenu.getItems().add(new MenuItem("Map coloring"));
    	searchMenu.getItems().add(new MenuItem("Route finding"));
    	getMainMenu().getMenus().add(searchMenu);
    	
    	Menu gamesMenu = new Menu("Games");

//    	MenuItem item1 = new MenuItem("N queens");
//    	item1.setOnAction(new AppStarter(NQueensApp.class));
//    	gamesMenu.getItems().add(item1); 
    	
    	// add item to Menu
//    	addItem(gamesMenu, EightPuzzleApp.class);
//    	addItem(gamesMenu, NQueensApp.class);
    	
    	
    	getMainMenu().getMenus().add(gamesMenu);
    	
    	Menu setMenu = new Menu("Settings");
    	setMenu.getItems().add(new MenuItem("Language"));
    	setMenu.getItems().add(new SeparatorMenuItem());
    	
    	MenuItem aboutItem = new MenuItem("About");
    	aboutItem.setOnAction(handleAbout()); //.addEventHandler(ActionEvent.class, handleAbout());

    	setMenu.getItems().add(aboutItem);
    	getMainMenu().getMenus().add(setMenu);
    
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Opens an about dialog.
     * @return 
     */
    @FXML
    private EventHandler<ActionEvent> handleAbout() {
		return new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
        	  Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("UDUI App");
		        alert.setHeaderText("About");
		        alert.setContentText("Author: Slávka Ivaničová\nWebsite: http://github.com/slavka5212/udui");
		        alert.showAndWait();
          }
		};
    }
    
    
    /**
	 * Adds a new agent application to the menu. The class is expected to be
	 * part of a package and to provide a <code>constructApplicationFrame</code>
	 * method.
	 */
	public void addItem(Menu subMenu, Class<?> appClass) {
		MenuItem item = new MenuItem(appClass.getSimpleName()); // addAppToMenu(subMenu, appClass);
		subMenu.getItems().add(item);
		item.setOnAction(mainApp.new AppStarter(appClass));
	}

	public MenuBar getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuBar mainMenu) {
		this.mainMenu = mainMenu;
	}

}
