package application.sysad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import application.orders.Order;
import application.orders.items.OrderItem;

/**
 * <h2> Exporter Class </h2>
 * 
 * Defines Exporter object
 * Allows for exporting of Orders and OrderItems to .csv format
 * 
 * @author Lucille Ablett
 *
 */

public class Exporter extends SysAdController {

	/**
	 * Exports file "OrderExport.csv" using BufferedWriter
	 * The first line of the file defines the column headers for the Order object
	 * Orders are then written to the file using a for loop
	 * @throws Exception
	 */
	public void exportCSV() throws Exception {
		Writer writer = null;
		try {
			File file = new File("OrderExport.csv");
			writer = new BufferedWriter(new FileWriter(file));

			String columnHeader = "OrderID,TableNumber,Waiter,Date,Time,Total,Comments\n";
			writer.write(columnHeader);

			for (Order order : orders) {
				String orderText = order.getOrderID() + "," + order.getTableNumber() + "," + order.getWaiter() + "," + order.getOrderDate() + "," + order.getOrderTime() + "," + order.getTotal() + "," + order.getOrderComments() + "\n";
				writer.write(orderText);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			System.out.println("Export Complete");
			writer.flush();
			writer.close();
		} 
	}

	/**
	 * Exports file "OrderItemsExport.csv" using BufferedWriter
	 * The first line of the exported file contains the Order details from the Order object
	 * The second line defines the column headers for the OrderItems defined within the Order object
	 * OrderItems are then written to the file using a for loop
	 * @param order Order instance to export to .csv format
	 * @throws Exception
	 */

	public void exportCSV(Order order) throws Exception {
		Writer writer = null;
		try {
			File file = new File("OrderItemsExport.csv");
			writer = new BufferedWriter(new FileWriter(file));

			String columnHeader = "OrderID: " + order.getOrderID() + ", TableNumber: " + order.getTableNumber() + ", Waiter:" + order.getWaiter() + ", Date: " + order.getOrderDate() + ", Time: " + order.getOrderTime() + ", Total: " + order.getTotal() + ", Comments: " + order.getOrderComments() + "\n\n";
			writer.write(columnHeader);
			String columnSubHeader = "Item Name: ,Item Quantity: , Item Total\n";
			writer.write(columnSubHeader);

			for (OrderItem item : order.orderItems) {
				String orderText = item.getItemName() + "," + item.getItemQuantity() + "," + item.getItemTotal() + "\n";
				writer.write(orderText);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			System.out.println("Export Complete");
			writer.flush();
			writer.close();
		} 
	}
}
