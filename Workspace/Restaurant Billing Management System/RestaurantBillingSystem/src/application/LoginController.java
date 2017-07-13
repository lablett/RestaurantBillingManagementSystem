package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * <h2> LoginController </h2>
 * 
 * Controls behaviour of Login.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class LoginController extends Main {
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private AnchorPane ancLogin;

	/**
	 * Triggers validateCredentials() method
	 * @param e ActionEvent click Login button
	 * @throws Exception
	 */
	@FXML
	public void Login(ActionEvent e) throws Exception {
		validateCredentials();
	}

	/**
	 * Triggers validateCredentials() method if enter key is pressed when in Login window
	 * @param e KeyEvent
	 * @throws Exception
	 */
	@FXML
	public void handleEnterPressed(KeyEvent e) throws IOException{
		if (e.getCode() == KeyCode.ENTER) {
			validateCredentials();
		}
	}

	/**
	 * Validated input username and password against username and password details contained within application
	 * @throws IOException
	 */
	@FXML
	public void validateCredentials() throws IOException{
		if (userList.containsKey(txtUsername.getText())) {
			application.User user = userList.get(txtUsername.getText());
			if (user.getPassword().equals(txtPassword.getText())){
				currentUser = user;
				user.setLastLogin();
				showOptions(currentUser);
			} else {
				lblStatus.setText("Incorrect credentials, please try again");
			}
		} else {
			lblStatus.setText("Incorrect credentials, please try again");
		}
	}
}