package application.view;

import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JFrame;

import aima.gui.applications.AimaDemoFrame;
import aima.gui.applications.search.games.EightPuzzleApp;
import application.MainApp;
import application.SwingFx;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
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
        
        final SwingNode swingNode = new SwingNode();
        SwingFx.createSwingContent(swingNode);

        AimaDemoFrame aima = new AimaDemoFrame(); 
//        new AppStarter();
        Class<EightPuzzleApp> appClass = EightPuzzleApp.class;
        try {
			Object instance = appClass.newInstance();
			Method m = appClass.getMethod("constructApplicationFrame",
					new Class[0]);
			JFrame af = (JFrame) m.invoke(instance, (Object[]) null);
			JComponent currPanel = (JComponent) af.getContentPane().getComponent(0);
			af.getContentPane().remove(currPanel);
			// set currPanel as swingNode
			swingNode.setContent(currPanel);
			// add swingNode to leftPane
			leftPane.getChildren().add(swingNode);
//			getContentPane().add(currPanel, BorderLayout.CENTER);
//			validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
//        leftPane.getChildren().add(swingNode);
    	
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
