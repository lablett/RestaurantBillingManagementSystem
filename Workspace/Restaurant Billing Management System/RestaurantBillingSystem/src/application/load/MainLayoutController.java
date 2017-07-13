package application.load;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * <h2> MainLayoutController </h2>
 * 
 * Controls behaviour of MainLayout.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class MainLayoutController extends Main {

	/**
	 * Exits application upon trigger - confirmation is required
	 * @param e ActionEvent click on Exit button
	 */

	public void exitProgram(ActionEvent e){

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Exit Application");
		alert.setHeaderText("Confirm Exit Application");
		alert.setContentText("Are you sure you want to exit the application?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Platform.exit();
		} else {
			//do nothing
		}
	}

	/**
	 * Returns current user to options window, with available options depending on access level
	 * @param e ActionEvent click on Home button
	 * @throws IOException
	 */
	public void goHome(ActionEvent e) throws IOException {
		if (bpMain.getCenter().getId().equals("ancLogin")){
			showLogin();
			currentOrder = null;
		} else {
			showOptions(currentUser);
		}
	}

	/**
	 * Logs out the user by returning user to Login window and resetting current user to null
	 * @param e ActionEvent click on Logout button
	 * @throws IOException
	 */
	public void goLogOut(ActionEvent e) throws IOException {
		currentUser = null;
		currentOrder = null;
		showLogin();
	}
}
