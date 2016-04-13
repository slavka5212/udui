package application.view;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Locale;
import java.util.ResourceBundle;

import application.MainApp;
import application.Messages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;

public class RootLayoutController {
	// Reference to the main application.
    private MainApp mainApp;
    @FXML
    private MenuBar mainMenu;
//    private static final long serialVersionUID = 1L;
    private static ResourceBundle messages;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootLayoutController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * Creates the menu.
     */
    @FXML
    private void initialize() {
    	assert getMainMenu() != null : "fx:id=\"mainMenu\" was not injected: check your FXML file 'RootLayout.fxml'.";
    	messages = Messages.getMessages();
    	Menu settingsMenu = new Menu(messages.getString("menu_settings"));
    	Menu menuLanguage = new Menu(messages.getString("menu_language"));
    	
    	final ToggleGroup groupLanguage = new ToggleGroup();
    	final Entry<String, Locale>[] languages = new Entry [] {
    			new SimpleEntry<String, Locale>("English", Messages.enLocale),
    			new SimpleEntry<String, Locale>("Slovak", Messages.skLocale)
    	};
    	for (Entry<String, Locale> language : languages) {
    	    RadioMenuItem itemEffect = new RadioMenuItem(language.getKey());
    	    itemEffect.setSelected((language.getValue()).equals(messages.getLocale()));
    	    itemEffect.setOnAction(handleLanguage(language.getValue()));
    	    itemEffect.setToggleGroup(groupLanguage);
    	    menuLanguage.getItems().add(itemEffect);
    	}
    	
    	settingsMenu.getItems().add(menuLanguage);
    	settingsMenu.getItems().add(new SeparatorMenuItem());
    	
    	MenuItem aboutItem = new MenuItem(messages.getString("menu_about"));
    	aboutItem.setOnAction(handleAbout());

    	settingsMenu.getItems().add(aboutItem);
    	getMainMenu().getMenus().add(settingsMenu);
    
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
     * 
     * @return EventHandler<ActionEvent> 
     */
    @FXML
    private EventHandler<ActionEvent> handleAbout() {
		return new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
        	  Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("UDUI App");
		        alert.setHeaderText(messages.getString("menu_about"));
		        String about_info = messages.getString("about_author") + " " + messages.getString("MainApp_author") + "\n" + messages.getString("about_website") + " " + messages.getString("MainApp_website");
		        alert.setContentText(about_info);
		        alert.showAndWait();
          }
		};
    }
    
    /**
     * Change language.
     * 
     * @param newLanguage 
     * @return EventHandler<ActionEvent>
     */
    @FXML
    private EventHandler<ActionEvent> handleLanguage(Locale newLanguage) {
		return new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
        	  mainApp.currLanguage = newLanguage; 
        	  mainApp.start(mainApp.getPrimaryStage());
          }
		};
    }
    
    /**
	 * Adds a new agent application to the menu. The class is expected to be
	 * part of a package and to provide a <code>constructApplicationFrame</code>
	 * method.
	 */
	public void addItem(Menu subMenu, Class<?> appClass) {
		MenuItem item = new MenuItem(Messages.getMessages().getString("app_"+appClass.getSimpleName()));
		subMenu.getItems().add(item);
		item.setOnAction(mainApp.new AppStarter(appClass, subMenu.getText()));
	}

	public MenuBar getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MenuBar mainMenu) {
		this.mainMenu = mainMenu;
	}

}
