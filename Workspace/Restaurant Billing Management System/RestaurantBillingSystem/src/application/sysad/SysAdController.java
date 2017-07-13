package application.sysad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import application.User;
import application.orders.Order;
import application.orders.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * <h2> SysAdController </h2>
 * 
 * Controls behaviour of SysAd.FXML
 * 
 * @author Lucille Ablett
 *
 */

public class SysAdController extends Main {
	@FXML
	private AnchorPane ancSysAd;
	@FXML
	private TabPane tpSysAd;
	@FXML
	private Tab tabManageAcc;
	@FXML
	private TextField txtNewUsername;
	@FXML
	private TextField txtNewPassword;
	@FXML
	private CheckBox cbIsAdmin;
	@FXML
	private Button btnAddUser;
	@FXML
	private TableView<User> tblUsers;
	@FXML
	private TableColumn<User, String> colUsername;
	@FXML
	private TableColumn<User, String> colPassword;
	@FXML
	private TableColumn<User, Boolean> colAdmin;
	@FXML
	private TableColumn<User, String> colLastLogin;
	@FXML
	private Tab tabViewActivity;
	@FXML
	private Tab tabEditMenu;
	@FXML
	private Tab tabManageOrder;
	@FXML
	private Tab tabImport;
	@FXML
	private Tab tabExport;
	@FXML
	private TableView<Item> tblMenu;
	@FXML
	private TableColumn<Item, String> colItem;
	@FXML
	private TableColumn<Item, Double> colPrice;
	@FXML
	private TextField txtNewItem;
	@FXML
	private TextField txtNewPrice;
	@FXML
	private Button btnAddItem;
	@FXML
	private Button btnDeleteItem;
	@FXML
	private TableView<Order> tblOrderHistory;
	@FXML
	private TableColumn<Order, Integer> colOrderID;
	@FXML
	private TableColumn<Order, Integer> colTableNo;
	@FXML
	private TableColumn<Order, String> colWaiter;
	@FXML
	private TableColumn<Order, String> colDate;
	@FXML
	private TableColumn<Order, String> colTime;
	@FXML
	private TableColumn<Order, Double> colTotal;
	@FXML
	private Label lblItemError;
	@FXML
	private Button btnOK;
	@FXML
	private ImageView imgBilling;
	@FXML
	private ImageView imgSysAd;
	@FXML
	private BorderPane bpOrderItems;

	private static ObservableList<User> userObsList = FXCollections.observableList(new ArrayList<User>(userList.values()));
	private static ObservableList<Item> menuObsList = FXCollections.observableList(new ArrayList<Item>(menu.values()));

	/**
	 * Initialises the TableViews tblUsers, tblMenu, and tblOrderHistory
	 */
	public void initialise() {
		tblUsers.setItems(userObsList);

		colUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		colAdmin.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isAdmin"));
		colLastLogin.setCellValueFactory(new PropertyValueFactory<User, String>("LastLogin"));
		tblUsers.getColumns().setAll(colUsername, colPassword, colAdmin, colLastLogin);
		tblUsers.getSortOrder().add(colUsername);


		tblMenu.setItems(menuObsList);
		colItem.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemName"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Item, Double>("ItemPrice"));
		tblMenu.getColumns().setAll(colItem, colPrice);
		tblMenu.getSortOrder().add(colItem);


		tblOrderHistory.setItems(ordersObsList);
		colOrderID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("OrderID"));
		colTableNo.setCellValueFactory(new PropertyValueFactory<Order, Integer>("TableNumber"));
		colWaiter.setCellValueFactory(new PropertyValueFactory<Order, String>("Waiter"));
		colDate.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderDate"));
		colTime.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderTime"));
		colTotal.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		tblOrderHistory.getColumns().setAll(colOrderID, colTableNo, colWaiter, colDate, colTime, colTotal);
		tblOrderHistory.getSortOrder().add(colDate);

		initListeners();

	}

	/** 
	 * Adds new user to list of users if new user and new password are both specified.
	 * If new user is to be an adnimistrator, a dialogue asks the user to confirm this.
	 * @param e ActionEvent click add user
	 * @throws IOException
	 */
	public void addUser(ActionEvent e) throws IOException{
		if ((txtNewUsername.getText().isEmpty() == false) && (txtNewPassword.getText().isEmpty() ==  false)){
			String newUser = txtNewUsername.getText();
			String newPassword = txtNewPassword.getText();
			Boolean isAdmin = cbIsAdmin.isSelected();

			if (cbIsAdmin.isSelected() == true){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm Administrator Creation");
				alert.setHeaderText("Confirm Administrator Creation");
				alert.setContentText("Are you sure you want give administrator privileges to " + newUser + "?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					User user = new User(newUser, newPassword, isAdmin);
					userObsList.add(user);
					tblUsers.refresh();
					txtNewUsername.clear();
					txtNewPassword.clear();
				} else {
					// User not created
				}
			}  else if (cbIsAdmin.isSelected() == false) {
				User user = new User(newUser, newPassword, isAdmin);
				userObsList.add(user);
				tblUsers.refresh();
				txtNewUsername.clear();
				txtNewPassword.clear();
			} 
		} 

	}
	/**
	 * Deletes selected user from list, asks for confirmation before doing so
	 * @param e ActionEvent click delete user button
	 * @throws IOException
	 */
	public void deleteUser(ActionEvent e) throws IOException {
		try {
			User user = tblUsers.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm User Deletion");
			alert.setHeaderText("Confirm User Deletion");
			alert.setContentText("Are you sure you want to delete user " + user.getUsername() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				userObsList.remove(user);
				tblUsers.refresh();
			} else {
				// nothing
			} 
		} catch (Exception ex) {
			// do nothing
		}
	}

	// Menu Management

	/**
	 * Adds new item to menu, if item name and price are specified, and if price is specified using the correct data type
	 * @param e
	 */
	public void addItem(ActionEvent e){
		try {
			if ((txtNewItem.getText().isEmpty() ==  false) && (txtNewPrice.getText().isEmpty() == false)){
				String itemName = txtNewItem.getText();
				double itemPrice = Double.parseDouble(txtNewPrice.getText());
				Item newItem = new Item(itemName, itemPrice);
				menuObsList.add(newItem);
				lblItemError.setText("");
				txtNewItem.clear();
				txtNewPrice.clear();
				tblMenu.refresh();
			} else {
				lblItemError.setText("Please complete all fields");
			} 
		} catch (Exception ex) {
			lblItemError.setText("Invalid item price");
		}
	}

	/**
	 * Deletes selected item from menu, asks for confirmation before doing so
	 * @param e ActionEvent click delete item button
	 * @throws IOException
	 */
	public void deleteItem(ActionEvent e) throws IOException {
		try {
			Item item = tblMenu.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Item Deletion");
			alert.setHeaderText("Confirm Item Deletion");
			alert.setContentText("Are you sure you want to delete item " + item.getItemName() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				menuObsList.remove(item);
				tblMenu.refresh();
			} else {
				// nothing
			} 
		} catch (Exception ex) {
			// do nothing
		}
	}

	/**
	 * Deletes selected order from order list, asks for confirmation before doing so
	 * @param e ActionEvent click delete order button
	 * @throws IOException
	 */
	public void deleteOrder(ActionEvent e) throws IOException {
		try {
			Order order = tblOrderHistory.getSelectionModel().getSelectedItem();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Order Deletion");
			alert.setHeaderText("Confirm Order Deletion");
			alert.setContentText("Are you sure you want to delete order number " + order.getOrderID() + "?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				ordersObsList.remove(order);
				tblOrderHistory.refresh();
			} else {
				// nothing
			} 
		} catch (Exception ex) {
			// do nothing
		}
	}

	/**
	 * Launches new window to display OrderItems associated with selected Order (if order is selected)
	 * @param e ActionEvent click view order items button
	 * @throws IOException
	 */
	@FXML
	private void viewOrderItems(ActionEvent e) throws IOException{
		Order order = tblOrderHistory.getSelectionModel().getSelectedItem();
		currentOrder = order;
		System.out.println(order.getOrderID());
		Stage viewItems = new Stage();
		viewItems.setTitle("Items in Order: " + order.getOrderID() + "  Date: " + order.getOrderDate());

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("sysad/ViewOrderItems.FXML"));
		bpOrderItems = loader.load();
		Scene scene = new Scene(bpOrderItems);
		ViewOrderItemsController controller = loader.getController();
		controller.initialiseTable();
		viewItems.setScene(scene);
		viewItems.show();
	}

	/**
	 * Exports list of orders to .csv file
	 * @param e ActionEvent click export orders button
	 * @throws Exception
	 */
	@FXML
	private void exportOrders(ActionEvent e) throws Exception {
		Exporter exporter = new Exporter();
		exporter.exportCSV();
	}

	/**
	 * Initialises listeners for ObservableLists to update ArrayLists to reflect changes
	 */
	public void initListeners(){

		userObsList.addListener(new ListChangeListener<User>(){
			@Override
			public void onChanged(Change<? extends User> c) {
				while (c.next()) {
				} if (c.wasRemoved()) {
					for (User removedUser : c.getRemoved()) {
						userList.remove(removedUser.getUsername(), removedUser);
					}
				} else if (c.wasAdded()) {
					for (User addedUser : c.getAddedSubList()) {
						userList.put(addedUser.getUsername(), addedUser);
					}
				} 
			}
		});

		ordersObsList.addListener(new ListChangeListener<Order>(){
			@Override
			public void onChanged(Change<? extends Order> c) {
				while (c.next()) {
				} if (c.wasRemoved()) {
					for (Order removedItem : c.getRemoved()) {
						orders.remove(removedItem);
					}
				} else if (c.wasAdded()) {
					for (Order addedItem : c.getAddedSubList()) {
						orders.add(addedItem);
					}
				} 
			}
		});


		menuObsList.addListener(new ListChangeListener<Item>(){
			@Override
			public void onChanged(Change<? extends Item> c) {
				while (c.next()) {
				} if (c.wasRemoved()) {
					for (Item removedItem : c.getRemoved()) {
						menu.remove(removedItem.getItemName(), removedItem);
					}
				} else if (c.wasAdded()) {
					for (Item addedItem : c.getAddedSubList()) {
						menu.put(addedItem.getItemName(), addedItem);
					}
				} 
			}
		});


	}
}

