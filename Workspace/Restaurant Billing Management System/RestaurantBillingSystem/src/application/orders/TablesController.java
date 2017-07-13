package application.orders;

import java.io.IOException;

import application.options.OptionsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * <h2> TablesController </h2>
 * 
 * Controls behaviour of Tables.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class TablesController extends OptionsController {
	@FXML
	private AnchorPane apTables;
	@FXML
	private Pane pnTables;

	/**
	 * Launches order screen when table is selected:
	 * Selected table number is retrieved from ActionEvent source and showOrder(tableNumber) triggered.
	 * @param e ActionEvent table selection
	 * @throws IOException
	 */

	@FXML
	private void goOrder(ActionEvent e) throws IOException{
		Button t = (Button)e.getSource();
		int tableNumber = Integer.parseInt(t.getText());
		//Order order = new Order(tableNumber);
		showOrder(tableNumber);

	}

}
