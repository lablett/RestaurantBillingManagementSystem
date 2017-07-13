package application.options;

import java.io.IOException;

import application.Main;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * <h2> OptionsController </h2>
 * 
 * Controls behaviour of Options.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class OptionsController extends Main {
	@FXML
	private AnchorPane ancOptions;
	@FXML
	private Button btnSysAd;
	
	/**
	 * Enables/Disables System Administration option based on current user access rights
	 * @param currentUser	Defines user currently logged into application
	 */
	
	public void setDisable(User currentUser) {
		if (currentUser.getIsAdmin() == true) {
			btnSysAd.setDisable(false);
		} else if (currentUser.getIsAdmin() == false) {
			btnSysAd.setDisable(true);
		}
		
	}
	
	/**
	 * Launches restaurant table view upon selection of 'Billing' option in options window 
	 * @param e ActionEven click Billing button
	 * @throws IOException
	 */
	public void goBilling(ActionEvent e) throws IOException {
		showTables();
	}
	
	/**
	 * Launches 'System Administration' option upon its selection in options window
	 * @param e ActionEvent click System Administration button
	 * @throws IOException
	 */
	public void goSysAd(ActionEvent e) throws IOException {
		showSysAd();
	}
}
