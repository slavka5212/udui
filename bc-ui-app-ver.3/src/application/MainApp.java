package application;

import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFrame;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import aima.gui.applications.search.csp.MapColoringApp;
import aima.gui.applications.search.games.*;
import aima.gui.applications.search.map.RouteFindingAgentApp;
import application.view.RootLayoutController;
import application.view.VisualViewsController;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private RootLayoutController rootController;
	private VisualViewsController viewsController;

	JComponent currPanel;
	PrintStream outStream;
	private SwingNode swingNode = new SwingNode();
	public Locale currLanguage = Messages.enLocale; // Set default language English

	@Override
	public void start(Stage primaryStage) {
		
		Messages.setMessages(currLanguage);
		swingNode = new SwingNode();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Messages.getMessages().getString("MainApp_title"));

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
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml")); //$NON-NLS-1$
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			rootController = loader.getController();
			rootController.setMainApp(this);

			initMenu();
			
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
			loader.setLocation(MainApp.class.getResource("view/VisualViews.fxml")); //$NON-NLS-1$
			AnchorPane visualViews = (AnchorPane) loader.load();
			// Set visual views into the center of root layout.
			rootLayout.setCenter(visualViews);
			// Give the controller access to the main app.
			viewsController = loader.getController();
			viewsController.setMainApp(this);
			viewsController.getSwingPane().getChildren().add(swingNode);
			
			viewsController.setVisibleTitlePane(false); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the menu bar into the root controller.
	 */
	private void initMenu() {
		// Add items to the search menu
		Menu searchMenu = new Menu(Messages.getMessages().getString("menu_search"));
		rootController.addItem(searchMenu, MapColoringApp.class);
		rootController.addItem(searchMenu, RouteFindingAgentApp.class);
		rootController.getMainMenu().getMenus().add(searchMenu);
		// Add items to the games menu
		Menu gamesMenu = new Menu(Messages.getMessages().getString("menu_games"));
		rootController.addItem(gamesMenu, EightPuzzleApp.class);
		rootController.addItem(gamesMenu, NQueensApp.class);
		rootController.getMainMenu().getMenus().add(gamesMenu);
	}

	/**
	 * Returns the main stage.
	 * 
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
		String whereIs = "";

		public AppStarter(Class<?> ac) {
			appClass = ac;
		}

		public AppStarter(Class<?> ac, String subMenu) {
			appClass = ac;
			whereIs = subMenu;
		}

		@Override
		public void handle(ActionEvent event) {
			try {
				Pane viewP = viewsController.getSwingPane();
				viewP.getChildren().remove(swingNode);
				Object instance = appClass.newInstance();
				Method m = appClass.getMethod("constructApplicationFrame", //$NON-NLS-1$
						new Class[0]);
				JFrame af = (JFrame) m.invoke(instance, (Object[]) null);
				currPanel = (JComponent) af.getContentPane().getComponent(0);
				af.getContentPane().remove(currPanel);
				currPanel.setMinimumSize(new Dimension((int) viewP.getWidth(), (int) viewP.getHeight()));
				// Set currPanel as swingNode
				swingNode.setContent(currPanel);
				// Add swingNode to leftPane
				viewP.getChildren().add(swingNode);
				
				viewsController.setVisibleTitlePane(true); 
				viewsController.setTitlePaneText(whereIs + " > " + Messages.getMessages().getString("app_"+appClass.getSimpleName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
