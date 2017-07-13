package application.orders.items;

/**
 * <h2> Item Class </h2>
 * 
 * Defines Item object
 * 
 * @author Lucille Ablett
 *
 */

public class Item {

	private String itemName;
	private double itemPrice;

	public Item(String itemName, double itemPrice){
		this.itemName = itemName;
		this.itemPrice = itemPrice;

	}

	/**
	 * @return itemName
	 */
	public String getItemName(){
		return itemName;	
	}

	/**
	 * @return itemPrice
	 */
	public double getItemPrice(){
		return itemPrice;
	}
}

