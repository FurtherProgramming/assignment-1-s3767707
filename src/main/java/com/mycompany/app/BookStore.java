package com.mycompany.app;

/*
 * Class:		BookStore
 * Description:	The system manager the manages the collection of data 
 * Author:		Anson Go Guang Ping
 */
public class BookStore {

	private Book[] bookList = new Book[10]; //array to store all items in bookstore
	private Book[] selection = new Book[10]; // array to store result of user searching title
	private Order[] cart = new Order[10]; // array to store items purchased by user
	private final double physicalPrice = 50.00;
    private final double eBookPrice = 8.00;
    
	public BookStore() {
		
		seedData();
	}
    
    /*
	 * Function to display all items available in bookStore
	 * Book will still appear in bookList when their physical copy number is zero as user can still buy the ebook version
	 */
	public void displayAll() {

		for (int i = 0; i < bookList.length; i++) {
			
			int count = 0;

			if (bookList[i] != null) {

				// Counts the book physical copy order in shopping cart to temporary update the display list later
				for(int j = 0; j < cart.length; j++) {
					
					if(cart[j] != null) {
						
						if(cart[j].getPhysicalCopy() == true) {
							
							if(cart[j].getBook().getTitle().equals(bookList[i].getTitle())) {
								
								count++;
							}
						}					
					}
				}
				System.out.println(String.format("%d. %s -- %s, %d copies, ebook available", i + 1, bookList[i].getTitle(), bookList[i].getAuthor(), bookList[i].getNum() - count));
			}
		}
	}

	public boolean seedData() {

		if (bookList[0] != null) {
			
			return false;
		}

		Book book = new Book("Absolute Java", "Frank", 1, true);
		Book book2 = new Book("JAVA : How to Program", "Duncas", 4, true);
		Book book3 = new Book("Computing Concept swith JAVA 3 Essentials", "Mac", 7, false);
		Book book4 = new Book("Java Software Solutions", "Brendon", 2, true);

		bookList = addBookToArray(book, bookList);
		bookList = addBookToArray(book2, bookList);
		bookList = addBookToArray(book3, bookList);
		bookList = addBookToArray(book4, bookList);

		return true;

	}

	/*
	 * Method to add a book to an array
	 */
	public Book[] addBookToArray(Book book, Book[] books) {

		if (books[books.length - 1] == null) { //if the array is not full

			//add book
			for (int i = 0; i < books.length; i++) {

				if (books[i] == null) {

					books[i] = book;
					break;
				}
			}
		}
		else {

			//Increase array size by one and add item at the end of array if array is full
			Book[] temp = new Book[books.length + 1];

			for (int i = 0; i < books.length; i++) {

				temp[i] = books[i];
			}

			books = temp;
			books[books.length - 1] = book;
			
		}
        return books;
	}
	
	/*
	 * Check if title of items in bookstore contains title input by user(not case-sensitive)
	 */
	public boolean searchBookByTitle(String title) {
		
		selection = new Book[10];
		boolean found = false;
		
		for (int i = 0; i < bookList.length; i++ ) {
			
			if(bookList[i] != null & title != "") {
				
				if (bookList[i].getTitle().toLowerCase().contains(title.toLowerCase())) {
					
					selection = addBookToArray(bookList[i], selection);
					found = true;
				}
			}			
		}
		return found;
	}
	
	/*
	 * Method to display result of searching book title
	 */
	public void displaySearch() {
		
		if(selection[0] != null) {
			
			System.out.println("\nThe following title is a match:");
			
			for (int i = 0; i < selection.length; i++) {
				
				if(selection[i] != null) {
					
					System.out.println(String.format("%d. %s -- %s", i+1, selection[i].getTitle(),
							selection[i].getAuthor()));
				}			
			}
			System.out.println("0. cancel");
		}
		else {
			
			System.out.println("No match found.");
		}
		
	}
	
	/*
	 * return bookList array
	 */
    public Book[] getBookList() {
		
		return bookList;
	}

    /*
	 * return selection array
	 */
	public Book[] getSelection() {
		
		return selection;
	}
	
	/*
	 * return cart array
	 */
    public Order[] getCart() {
		
		return cart;
	}
    
    /*
     * Method to add item to shopping cart
     * Each order consist only of one book object with its physical copy and ebook version
     * If a book of the same book title is bought again, it will be saved to the next order
     */
	public void addBookToCart(Order order) {
		
		if (cart[cart.length - 1] == null) { // if shopping cart array not full

			for (int i = 0; i < cart.length; i++) {

				if (cart[i] == null) {

					cart[i] = order;
					break;
				}
			}
		}
		else {
            // increase the shopping cart size by one and put the item at the end of the array if array is full
			Order[] temp = new Order[cart.length + 1];

			for (int i = 0; i < cart.length; i++) {

				temp[i] = cart[i];
			}

			cart = temp;
			cart[cart.length - 1] = order;
			
		}
	}
	
	/*
	 * Function to display items in shopping cart
	 */
	public void displayCart() {
		
		if(cart[0] == null) {
			
			System.out.println("Shopping cart is empty.");
		}
	
		for (int i = 0; i < cart.length; i++) {

			if (cart[i] != null) {

				if (cart[i].getPhysicalCopy() == true && cart[i].getEbook() == true) {
					
					System.out.println(String.format("%d. %s, physical copy version, ebook version", i + 1, cart[i].getBook().getTitle()));
				}
				else if (cart[i].getPhysicalCopy() == true && cart[i].getEbook() == false) {
					
					System.out.println(String.format("%d. %s, physical copy version", i + 1, cart[i].getBook().getTitle()));
				}
                else if (cart[i].getPhysicalCopy() == false && cart[i].getEbook() == true) {
					
					System.out.println(String.format("%d. %s, ebook version", i + 1, cart[i].getBook().getTitle()));
				}
			}
		}
	}
    
	/*
	 * Method to remove item from shopping cart. 
     * When removing one order from the shopping cart, the physical copy and ebook version in that order will be removed together.
	 */
	public void removeItemFromCart(int num) {
		
		Order[] temp = new Order[cart.length]; //Create a temp array
		int count = 0;
		
		if(cart[0] == null) { //if shoppping cart is empty
			
			System.out.println("Shopping cart is empty.");
		}
		else {
			
			if(num >= 0 && num < cart.length) { // if number selected by user match the list displayed
				
				for (int i = 0; i < cart.length; i++) {
					
					if(num == i) {
						
						cart[i] = null; //remove item from the shopping cart
					}
				}
				
				//Copy all the items from cart array to temp array and make sure there is no empty position between items
				for (int i = 0; i < cart.length; i++) {
					
					if(cart[i] != null) {
						
						temp[count] = cart[i];
						count++;
					}	
				}
				cart = temp; 
				System.out.println("Item removed from shopping cart.");
			}
			else {
				
				System.out.println("Invalid option.");
			}
			
		}
	}
	
	/*
	 * Funtion to checkout
	 */
	public void checkout() {
		
		double checkout = 0;
		
		//calculate total price of item in shopping cart 
		if(cart[0] != null) {
			
			for(int i = 0; i < cart.length; i++) {
				
			    if(cart[i] != null) {
			    	
			    	if(cart[i].getPhysicalCopy() == true) {
				    	
				    	checkout += physicalPrice;
				    }
				    if(cart[i].getEbook() == true) {
				    	
				    	checkout += eBookPrice;
				    }
			    }				
			}
			updateStoreCopy();
			
			for(int i = 0; i < cart.length; i ++) {
				
				cart[i] = null;
			}
			
			System.out.println("You have purchased items to the total value of $" + checkout + ".");
			System.out.println("Thanks for shopping with Daintree!");
		}
		else {
			
			System.out.println("Shopping cart is empty.");
		}	
	}

	/*
	 * Method to update physical copy number after user checkout
	 */
	public void updateStoreCopy() {
		
		
		for(int i = 0; i < cart.length; i++) { //Run through shopping cart array
			
			
			for(int j = 0; j < bookList.length; j++) { //Run through array that stores book in bookStore
				
				if(cart[i] != null && bookList[j] != null) {
					
					
					if(cart[i].getPhysicalCopy() == true) { //If user purchases physical copy of that book
						
						if(cart[i].getBook().equals(bookList[j])) {
							
							
							bookList[j].setNum(bookList[j].getNum() - 1); //subtract number of physical copy of that book in stock by 1
						}
					}				
				}			
			}
		}
	}
	
	/*
	 * Remove all books from bookList array
	 */
	public void deleteBookList() {
		
		for(int i = 0; i < bookList.length; i++) {
			
			bookList[i] = null;
		}
	}
}
