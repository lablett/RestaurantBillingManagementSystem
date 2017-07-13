package application.orders.items;

/**
 * <h2> OrderItem Class </h2>
 * 
 * Defines OrderItem object
 * 
 * @author Lucille Ablett
 *
 */

public class OrderItem {
	
	private Item item;
	private int itemQuantity;

	/**
	 * OrderItem construction - defines new OrderItem instance
	 * @param item	Item instance
	 * @param quantity	Quantity of Item item
	 */
	public OrderItem (Item item, int quantity) {
		this.item = item;
		this.itemQuantity = quantity;
	}
	
	/**
	 * 
	 * @return item
	 */
	public Item getItem(){
		return item;
	}
	
	/**
	 * @return itemName
	 */
	public String getItemName(){
		return item.getItemName();
	}
	
	/**
	 * @return itemQuantity
	 */
	public int getItemQuantity(){
		return itemQuantity;
	}
	
	/**
	 * @return itemTotal
	 */
	public double getItemTotal(){
		double price = item.getItemPrice();
		int quantity = itemQuantity;
		return price*quantity;
	}
	
	/**
	 * Sets item
	 * @param item Item instance
	 */
	public void setItem(Item item){
		this.item = item;
	}
	
	/**
	 * Sets itemQuantity
	 * @param itemQuantity number of particular items in order
	 */
	public void setQuantity(int itemQuantity){
		this.itemQuantity = itemQuantity;
	}
}
