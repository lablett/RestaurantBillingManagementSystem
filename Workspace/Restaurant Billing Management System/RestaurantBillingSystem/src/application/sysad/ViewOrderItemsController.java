package application.sysad;

import application.orders.items.OrderItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * <h2> ViewOrderItemsController </h2>
 * 
 * Controls behaviour ViewOrderItems.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class ViewOrderItemsController extends SysAdController {

	@FXML
	private BorderPane bpOrderItems;
	@FXML
	private AnchorPane ancOrderItems;
	@FXML
	private TableView<OrderItem> tblViewItems;
	@FXML
	private TableColumn<OrderItem, String> colItem;
	@FXML
	private TableColumn<OrderItem, Integer> colQuantity;
	@FXML
	private TableColumn<OrderItem, Double> colTotal;
	@FXML
	private Button btnClose;

	// private static ObservableList<OrderItem> viewItems = FXCollections.observableList(currentOrder.orderItems);


	public void initialiseTable(){
		tblViewItems.setItems(orderObsItems);
		colItem.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemName"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("itemQuantity"));
		colTotal.setCellValueFactory(new PropertyValueFactory<OrderItem, Double>("itemTotal"));

		tblViewItems.getColumns().setAll(colItem, colQuantity, colTotal);

	}

	@FXML
	private void exportOrderItems(ActionEvent e) throws Exception{
		Exporter exporter = new Exporter();
		exporter.exportCSV(currentOrder);
	}

	@FXML
	private void closeStage(ActionEvent e){
		currentUser = null;
		Stage stage = (Stage)btnClose.getScene().getWindow();
		stage.close();
	}
}
