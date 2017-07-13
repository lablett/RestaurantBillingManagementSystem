package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.options.OptionsController;
import application.orders.Order;
import application.orders.OrderController;
import application.orders.items.Item;
import application.orders.items.OrderItem;
import application.sysad.SysAdController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * <h1> Restaurant Billing Management System </h1>
 * This application allows for the creation and management of food and drinks orders at a restaurant.
 * 
 * User accounts can me created and deleted with both administrator and non-administrator access levels.
 * Menu items can be created and deleted.
 * Previous orders can be viewed, updated, deleted, and exported.
 * 
 * @author Lucille Ablett
 * @since 15/12/2016
 *
 */

public class Main extends Application {
	private Stage primaryStage;
	@FXML
	public static BorderPane bpMain;
	@FXML
	private Stage secondaryStage;
	@FXML
	private ComboBox<Order> cbPreviousOrders;

	protected static User currentUser;
	public static Order currentOrder = new Order(0);
	public static int orderCount;

	protected static HashMap<String, User> userList = new HashMap<String, User>();
	protected static ArrayList<Order> orders = new ArrayList<Order>();
	protected static ObservableList<Order> ordersObsList = FXCollections.observableList(orders);
	protected static HashMap<String, Item> menu = new HashMap<>();
	protected static ObservableList<OrderItem> orderObsItems = FXCollections.observableList(currentOrder.orderItems);


	//ObservableList<String> observableList = FXCollections.observableList(menu);
	/**
	 * Starts application - triggered by launch() in main method
	 */

	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Restaurant Billing Management System");
			showMainView();
			showLogin();

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Loads the main layout - triggered on application launch
	 * @throws IOException
	 */

	private void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("load/MainLayout.fxml"));
		bpMain = loader.load();
		Scene scene = new Scene(bpMain);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Loads login screen into centre of main layout BorderPane
	 * @throws IOException
	 */
	protected void showLogin() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Login.fxml"));
		AnchorPane login = loader.load();
		bpMain.setCenter(login);
	}

	/**
	 * Loads application options based on current user access level - if user is not an administrator,
	 * the "System Administration" option will be disabled.
	 * @param currentUser the user that is currently logged in
	 * @throws IOException
	 */
	protected static void showOptions(User currentUser) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("options/Options.fxml"));
		AnchorPane options = loader.load();
		bpMain.setCenter(options);
		OptionsController controller = loader.getController();
		controller.setDisable(currentUser);

	}

	/**
	 * Loads the System Administration window into the centre of the main layout BorderPane
	 * @throws IOException
	 */
	protected static void showSysAd() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("sysad/SysAd.fxml"));
		AnchorPane sysAd = loader.load();
		bpMain.setCenter(sysAd);
		SysAdController controller = loader.getController();
		controller.initialise();
	}

	/**
	 * Loads the graphical representation of the restaurant tables into the centre of the main layout BorderPane.
	 * @throws IOException
	 */
	protected static void showTables() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("orders/Tables.fxml"));
		AnchorPane tables = loader.load();
		bpMain.setCenter(tables);

	}	
	/**
	 * Loads the order screen and sets the value of the table number to the number of the selected table.
	 * @param tableNumber	the selected table number
	 * @throws IOException
	 */
	protected static void showOrder(int tableNumber) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("orders/Order.fxml"));
		AnchorPane order = loader.load();
		bpMain.setCenter(order);
		OrderController controller = loader.getController();
		controller.setTableNumber(tableNumber);
		controller.setDisable();
		controller.loadCBPreviousOrders();
	}

	/*public static void showOrder(Order orderInstance) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("orders/Order.fxml"));
		AnchorPane order = loader.load();
		bpMain.setCenter(order);
		OrderController controller = loader.getController();
		controller.setTableNumber(orderInstance.tableNumber);
		controller.setDateTime(orderInstance.orderTimeStamp);
		controller.setLabelTotal();
		controller.setComments(orderInstance.orderComments);
		controller.setOrderID(orderInstance.orderID);

	}*/

	/**
	 * Loads list of default users on application launch
	 */
	private static void loadUsers() {
		User user1 = new User("user1", "pass1", true);
		User user2 = new User("user2", "pass2", true);
		User user3 = new User("user3", "pass3", false);
		User user4 = new User("user4", "pass4", false);
		User user5 = new User("user5", "pass5", false);
		userList.put(user1.getUsername(), user1);
		userList.put(user2.getUsername(), user2);
		userList.put(user3.getUsername(), user3);
		userList.put(user4.getUsername(), user4);
		userList.put(user5.getUsername(), user5);

	}

	/**
	 * Loads list of default menu items on application launch
	 */
	private static void loadItems() {
		Item i1 = new Item("Sirloin Steak", 19.99);
		Item i2 = new Item("Rump Steak", 18.99);
		Item i3 = new Item("Beef Burger", 11.99);
		Item i4 = new Item("Cheese Burger", 12.49);
		Item i5 = new Item("Chicken Burger", 12.49);
		Item i6 = new Item("Lamb Chops", 13.99);
		Item i7 = new Item("Pork Chops", 10.99);
		Item i8 = new Item("Lamb Burger", 12.99);
		Item i9 = new Item("Wild Boar Burger", 12.99);
		Item i10 = new Item("Bean Burger", 9.99);
		Item i11 = new Item("Beef Burrito", 8.99);
		Item i12 = new Item("Chicken Burrito", 8.99);
		Item i13 = new Item("Three Bean Burrito", 7.99);
		Item i14 = new Item("Chilli Con Carne", 6.99);
		Item i15 = new Item("Refried Beans", 5.99);
		Item i16 = new Item("Strawberry Ice Cream", 4.99);
		Item i17 = new Item("Chocolate Ice Cream", 4.99);
		Item i18 = new Item("Vanilla Ice Cream", 4.99);
		Item i19 = new Item("Toffee Ice Cream", 4.99);
		Item i20 = new Item("Ice Cream Sunade", 6.99);
		Item i21 = new Item("Fanta", 2.99);
		Item i22 = new Item("Pepsi", 2.99);
		Item i23 = new Item("Cream Soda", 2.99);
		Item i24 = new Item("7up", 2.99);
		Item i25 = new Item("Spring Water", 1.99);

		menu.put(i1.getItemName(), i1);
		menu.put(i2.getItemName(), i2);
		menu.put(i3.getItemName(), i3);
		menu.put(i4.getItemName(), i4);
		menu.put(i5.getItemName(), i5);
		menu.put(i6.getItemName(), i6);
		menu.put(i7.getItemName(), i7);
		menu.put(i8.getItemName(), i8);
		menu.put(i9.getItemName(), i9);
		menu.put(i10.getItemName(), i10);
		menu.put(i11.getItemName(), i11);
		menu.put(i12.getItemName(), i12);
		menu.put(i13.getItemName(), i13);
		menu.put(i14.getItemName(), i14);
		menu.put(i15.getItemName(), i15);
		menu.put(i16.getItemName(), i16);
		menu.put(i17.getItemName(), i17);
		menu.put(i18.getItemName(), i18);
		menu.put(i19.getItemName(), i19);
		menu.put(i20.getItemName(), i20);
		menu.put(i21.getItemName(), i21);
		menu.put(i22.getItemName(), i22);
		menu.put(i23.getItemName(), i23);
		menu.put(i24.getItemName(), i24);
		menu.put(i25.getItemName(), i25);

		//ArrayList<OrderItem> test = new ArrayList<OrderItem>();
		//test.add(new OrderItem(i1, 1));
		//Order order1 = new Order(999, 1, test, "test comment");
		//orders.add(order1);
	}

	/**
	 * Application main method - initialises the application by launching method start().
	 * Loads default user list and menu items.
	 * Initialises listener for ObservableList orderObsItems to ensure orderItems ArrayList is updated to reflect any changes
	 * @param args
	 */
	public static void main(String[] args) {
		loadUsers();
		loadItems();
		orderCount = 0;
		//SysAdController controller = new SysAdController();
		//controller.initListeners();

		orderObsItems.addListener(new ListChangeListener<OrderItem>(){
			@Override
			public void onChanged(Change<? extends OrderItem> c) {
				while (c.next()) {
				} if (c.wasRemoved()) {
					for (OrderItem removedItem : c.getRemoved()) {
						currentOrder.orderItems.remove(removedItem);
					}
				} else if (c.wasAdded()) {
					for (OrderItem addedItem : c.getAddedSubList()) {
						currentOrder.orderItems.add(addedItem);
					}
				} 
			}
		});


		launch(args);

	}
}

