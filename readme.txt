Restaurant Billing Management System
##
Author: Lucille Ablett
Date:	19/12/2016
Version: 1.0
##
JavaFX application for the management of restaurant orders.
##

Username:  user1
Password:  pass1

Main Layout:
The main layout is displayed at all times during the running of the application and allows the user to navigate to the home options screen,
log out, or exit the program altogether.

Home button: 	- Returns user to options screen if current view shown is not login screen.
		- Options available depend on current user.

Log Out: 	- Returns user to login screen and sets current user variable to null.

Exit:		- Exits system.


Login interface:
This is the default interface display upon application launch.  It checks credentials against stored usernames and passwords upon clicking on 'Log in' button:
	- if password incorrect, error message displayed on label.
	- if enter key pressed when in password field, credential validation will also be triggered.


Options interface (Home):

Options availabe depend on access level:
	- Billing is available to everyone and allows for creation and editing of orders -> Displays tables in restaurant represented as buttons.

	- System Administration is only available to administrators.


Tables interface:
Allows user to select table for order creation -> Order Screen


Order interface:
Displays details of current order, allows loading of existing orders, and editing of items within the orders themselves.
	- To initialise screen, "New Order" must be selected, or a previous order loaded.
	- Table number is populated according to table selected from Tables view.
	- Order details populated upon order creation, or loading of existing order.
	- New Order -> Creates a new order instance, which automatically initialises the order variables (orderID, table, timestamp, waiter, order total).
		- orderID increases incrementally with the creation of each new order.
	- Load -> Loads selected order, updates labels to reflect details of selected order, and list of order items refreshes dynamically.
	- Add item -> this is not possible unless an order has first been created, or a previous order loaded.
		- if quantity == 0 or is null, item will not be added to order.
		- if quantity is non-numeric, an error message will inform the user.
		- if item is already present in order, quantity will be updated to reflect most recent quantity.
		- quantity text box clears once the item has been added to the order.
		- Item list reverts to "--Select Item--" once item has been added to order.
	- Remove item -> selected item will be removed from the order
		- this will also occur if the item quantity for any item is set to 0.
		- confirmation required to delete item from order.


System Administration interface:

Manage Accounts tab:
	- Displays list of user accounts showing username, password, whether or not they are system administrators, and when they last logged in.
	- New users can be added if the username and password boxes are populated.
		- if new user is to be admin, a dialogue box asks for confirmation.
	- Users can also be deleted, confirmation is also required.
	- Users table updates dynamically.

Edit Menu tab:
	- New items can be added or deleted from the menu, confirmation is required for deletion.
	- if one or neither of the text fields are not populated, an error message appears.
	- if the item price field is non-numeric, an error message appears.
	- Menu table updates dynamically.

Order Management tab:
View Order History:
	- Shows OrderID, Table Number, Waiter, Date, Time, and Total for all stored orders. 
		- This list can be exported to .csv format.
		- Selected orders can be deleted (with confirmation), table updates dynamically.
	- The items associated with each order can be viewed by selecting "View Order Items" -> launches new window.
		- This shows a list of Item Name, Quantity, and Item Total.
		- This can also be exported.
	- Nothing will happen if the buttons are clicked without first selecting an order.

Import:
	- Not available.
