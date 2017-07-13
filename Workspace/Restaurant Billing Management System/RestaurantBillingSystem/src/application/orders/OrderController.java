package application.orders;

import java.io.IOException;
import java.util.Optional;

import application.orders.items.Item;
import application.orders.items.OrderItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * <h2> OrderController </h2>
 * 
 * Controls behaviour of Order.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class OrderController extends TablesController {
	@FXML
	private AnchorPane ancOrder;
	@FXML
	private Label lblNewTitle;
	@FXML
	private Label lblTableNumber;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblTime;
	@FXML
	private Label lblTotal;
	@FXML
	private Label lblComments;
	@FXML
	private ComboBox<String> cbMenu;
	@FXML
	private TextField txtQuantity;
	@FXML
	private Button btnAdd;
	@FXML
	private TableView<OrderItem> tblOrderItems;
	@FXML
	private TableColumn<OrderItem, String> colItem;
	@FXML
	private TableColumn<OrderItem, Integer> colQuantity;
	@FXML
	private TableColumn<OrderItem, Double> colPrice;
	@FXML
	private TextArea txtComments;
	@FXML
	private Label lblOrderID;
	@FXML
	private Button btnAddItem;
	@FXML
	private ComboBox<Order> cbPreviousOrders;


	/**
	 * Sets the table number label to match the table number of the order
	 * @param tableNumber	Number of selected table
	 */
	public void setTableNumber(int tableNumber){
		String stableNumber = Integer.toString(tableNumber);
		lblTableNumber.setText(stableNumber);
	}

	/*
	 * Sets the orderID number label to match the orderID of the order
	 * @param orderID	orderID of order
	 */
	/*	private void setOrderID(int orderID){
		lblOrderID.setText(Integer.toString(orderID));
	}
	 */
	/*
	 * Formats the orderTimeStamp of the specified order and updates date and time labels accordingly
	 * @param orderTimeStamp	order creation time stamp
	 */
	/*	private void setDateTime(Date orderTimeStamp){
		SimpleDateFormat dft = new SimpleDateFormat ("dd/MM/yyyy");
		SimpleDateFormat tft = new SimpleDateFormat ("HH:mm:ss");
		lblDate.setText((String)dft.format(orderTimeStamp));
		lblTime.setText((String)tft.format(orderTimeStamp));
	}*/

	/**
	 * Sets the order total label to display updated current order total
	 */
	private void setLabelTotal(){
		currentOrder.getTotal();
		lblTotal.setText("£" + String.format("%.2f", currentOrder.getTotal()));
	}

	/**
	 * Sets the order comments text area to display the order comments of the specified order
	 * @param orderComments	comments associated with order
	 */
	private void setComments(String orderComments){
		txtComments.setText(orderComments);
	}

	/**
	 * Sets the orderID label to display the order ID of the current order
	 */
	private void setOrderID(){
		lblOrderID.setText(Integer.toString(currentOrder.getOrderID()));
	}

	/**
	 * Sets the table number label to display the table number of the current order
	 */
	private void setTableNumber(){
		lblTableNumber.setText(Integer.toString(currentOrder.getTableNumber()));
	}

	/**
	 * Sets the date and time labels to display the correctly formatted date and time for the current order.
	 */
	private void setDateTime(){
		lblDate.setText((currentOrder.getOrderDate()));
		lblTime.setText(currentOrder.getOrderTime());
	}

	/**
	 * Sets the comments text area to display the comments associated with the current order
	 */
	private void setComments(){
		txtComments.setText(currentOrder.getOrderComments());
	}

	/**
	 * Saves the text in the comments text area to the orderComments variable for the current order
	 * @param e ActionEvent click save comments button
	 */
	@FXML
	private void saveComments(ActionEvent e){
		currentOrder.orderComments = txtComments.getText();
	}

	/**
	 * Disables the 'Add' button - used when no new order has been created or previous order loaded
	 */
	public void setDisable(){
		btnAddItem.setDisable(true);
	}

	/**
	 * Loads combo box of menu items (as loaded on application launch, and updated as required)
	 */
	private void loadMenu(){
		cbMenu.getSelectionModel().selectFirst();
		for(String item : menu.keySet()){
			cbMenu.getItems().add(item);
		}
	}

	/**
	 * Initialises the order items table to display order items associated with currently selected order
	 */
	private void initialiseOrderItems(){
		tblOrderItems.setItems(orderObsItems);
		colItem.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("itemName"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>("itemQuantity"));
		colPrice.setCellValueFactory(new PropertyValueFactory<OrderItem, Double>("itemTotal"));

		tblOrderItems.getColumns().setAll(colItem, colQuantity, colPrice);
	}

	/**
	 * Creates new order instance given selected table number
	 * @param e ActionEvent click new order button
	 */
	public void newOrder(ActionEvent e){
		int tableNumber = Integer.parseInt(lblTableNumber.getText());
		Order order = new Order(tableNumber);
		orders.add(order);
		ordersObsList = FXCollections.observableList(orders);
		currentOrder = order;
		orderObsItems = FXCollections.observableList(currentOrder.orderItems);
		setTableNumber();
		setDateTime();
		setLabelTotal();
		setComments();
		setOrderID();
		initialiseOrderItems();
		loadMenu();
		loadCBPreviousOrders();
		btnAddItem.setDisable(false);
		tblOrderItems.refresh();
	}

	/**
	 * Adds menu item to current order items list
	 * If the quantity box is null or 0, nothing happens, else:
	 * If the order items list is empty, the item is immediately added to the list
	 * If the order items list is not empty, the list is checked for an instance of the order item
	 * 		- if the item is already in the list, the item quantity is updated to match the most recent quantity value
	 * 		- if the item is not in the list, the item is added to the order items list for the current order
	 * If the new item quantity is 0, the item is deleted from the order items list
	 * @param e ActionEvent click add item button
	 * @throws IOException
	 */
	@FXML	
	private void addItem(ActionEvent e) throws IOException{
		Item item = menu.get(cbMenu.getValue());
		try {
			if (txtQuantity.getText().isEmpty() == false){
				int quantity = Integer.parseInt(txtQuantity.getText());
				if ((quantity > 0)  && (orderObsItems.isEmpty() == true)) {
					OrderItem newItem = new OrderItem(item, quantity);
					orderObsItems.add(newItem);
					cbMenu.getSelectionModel().selectFirst();

				} else if (orderObsItems.isEmpty() == false) {
					boolean duplicate = false;

					if (quantity > 0) {
						for (OrderItem i : orderObsItems){
							if (i.getItemName().equals(item.getItemName())){
								i.setQuantity(quantity);
								duplicate = true;
								cbMenu.getSelectionModel().selectFirst();
								break;
							}
						}

					} else if (quantity == 0) {
						for (OrderItem i : orderObsItems){
							if (i.getItemName().equals(item.getItemName())){
								deleteItem(i);
								duplicate = true;
								break;

							} 
						} 

					} if (duplicate == false) {
						OrderItem newItem = new OrderItem(item, quantity);
						orderObsItems.add(newItem);
						cbMenu.getSelectionModel().selectFirst();
					} else {
						// do nothing
					}

				}

			}
			tblOrderItems.refresh();
			txtQuantity.clear();
			setLabelTotal();
			cbMenu.getSelectionModel().clearSelection();
		} catch (Exception ex) {
			// do nothing
		}
	}

	/**
	 * Initialises the combo box of previously created orders
	 */
	public void loadCBPreviousOrders(){
		cbPreviousOrders.getSelectionModel().selectFirst();
		cbPreviousOrders.setItems(ordersObsList);
	}

	/**
	 * Loads the selected order to the order window and updates order information containers to reflect this
	 * @param e ActionEvent click load button
	 */
	@FXML
	private void loadOrders(ActionEvent e){
		try {
			Order order = cbPreviousOrders.getSelectionModel().getSelectedItem();
			currentOrder = order;
			orderObsItems = FXCollections.observableList(currentOrder.orderItems);
			setTableNumber();
			setDateTime();
			setLabelTotal();
			setComments();
			setOrderID();
			initialiseOrderItems();
			loadMenu();
			tblOrderItems.refresh();
			btnAddItem.setDisable(false);
		} catch (Exception ex) {
			// do nothing
		}
	}

	/**
	 * Deletes selected item from order items list of current order
	 * Dialogue box is triggered to confirm action
	 * @param e ActionEvent click on remove item button
	 * @throws IOException
	 */
	@FXML
	private void deleteItem(ActionEvent e) throws IOException {
		try {
			OrderItem item = tblOrderItems.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Item Deletion");
			alert.setHeaderText("Confirm Item Deletion");
			alert.setContentText("Are you sure you want to remove item " + item.getItemName() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				orderObsItems.remove(item);
				tblOrderItems.refresh();
			} else {
				// nothing
			} 
		} catch (Exception ex) {
			// do nothing
		} setLabelTotal();
	}

	/**
	 * Deletes specified item from order items list for current order - used to remove item if quantity is 0
	 * @param i order item in question
	 */
	private void deleteItem(OrderItem i){
		orderObsItems.remove(i);
		tblOrderItems.refresh();
		//setLabelTotal();
	}

}	


