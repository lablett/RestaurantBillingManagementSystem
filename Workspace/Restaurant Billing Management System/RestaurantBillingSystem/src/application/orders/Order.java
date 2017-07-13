package application.orders;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.Main;
import application.User;
import application.orders.items.OrderItem;

/**
 * <h2> Order Class </h2>
 * 
 * Defines Order object
 * 
 * @author Lucille Ablett
 *
 */

public class Order extends Main {

	private int orderID;
	private int tableNumber;
	private Date orderTimeStamp;
	public ArrayList<OrderItem> orderItems;
	private double orderTotal;
	protected String orderComments;
	private User waiter;

	/**
	 * Order constructor
	 * @param tableNumber	Table number to be assigned to new order instance
	 */
	public Order(int tableNumber){

		orderCount = orderCount + 1;
		orderID = orderCount;
		waiter = currentUser;
		this.tableNumber = tableNumber;
		orderTimeStamp = new Date();
		orderTotal = 0.0;
		orderItems = new ArrayList<OrderItem>();
		orderComments = "";
	}

	/*
	public Order(int orderID, int tableNumber, ArrayList<OrderItem> orderItems, String orderComments){

		this.orderID = orderID;
		waiter = currentUser;
		this.tableNumber = tableNumber;
		orderTimeStamp = new Date();
		orderTotal = 0.0;
		this.orderItems = orderItems;
		this.orderComments = orderComments;
	}
	 */

	/**
	 * @return	orderID
	 */
	public int getOrderID(){
		return orderID;
	}

	/**
	 * @return tableNumber
	 **/
	public int getTableNumber(){
		return tableNumber;
	}

	/**
	 * Returns username of waiter responsible for order
	 * @return username
	 **/
	public String getWaiter(){
		return waiter.getUsername();
	}

	/**
	 * Formats orderTimeStampe and returns the value in dd/MM/yy format
	 * @return	 formatted orderTimeStamp
	 */
	public String getOrderDate(){
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy");
		return ft.format(orderTimeStamp);
	}

	/**
	 * Formats orderTimeStamp and returns the vale in HH:mm:ss format
	 * @return formatted orderTimeStamp
	 */
	public String getOrderTime(){
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		return ft.format(orderTimeStamp);
	}

	/**
	 * Calculates the order total based on items and quantity of items in order
	 * @return orderTotal
	 */
	public double getTotal(){
		this.orderTotal = 0.0;
		for (OrderItem i : this.orderItems){
			this.orderTotal = this.orderTotal + (i.getItemTotal());
		}
		return orderTotal;
	}

	/**
	 * @return orderComments
	 */
	public String getOrderComments(){
		return orderComments;
	}

	/*
	public void recalculateTotal(){
		this.orderTotal = 0.0;
		for (OrderItem i : this.orderItems){
			this.orderTotal = this.orderTotal + (i.getItemTotal());
		}
	}
	 */
	/**
	 * Overrides toString() method in order to display String details of Order objects in previous orders ComboBox
	 */
	@Override
	public String toString() {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy");
		String orderName = "Date : " + ft.format(orderTimeStamp) + "  Table: " + tableNumber + "  ID: " + orderID;
		return orderName;
	}

}
