package application.view;

import application.MainApp;
import application.SwingFx;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class VisualViewsController {
    @FXML
    private Pane leftPane;
    @FXML
    private Pane rightPane;
    @FXML
    private AnchorPane visualPane;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public VisualViewsController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	System.out.println("visualPane " + visualPane);
    	System.out.println("leftPane " + leftPane);
    	
    	leftPane.setStyle("-fx-background: #0000FF;");
    	Button fxbutton = new Button("Moj button");
        
        final SwingNode swingNode = new SwingNode();
        SwingFx.createSwingContent(swingNode);
        
        rightPane.getChildren().add(fxbutton);
    	
        // Initialize the person table with the two columns.
//        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
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
