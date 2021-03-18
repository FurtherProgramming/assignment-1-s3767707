//package app;

//import books.Book;
//import books.Order;

/*
 * Class:		BookStore
 * Description:	The system manager the manages the collection of data 
 * Author:		Anson Go Guang Ping
 */
public class BookStore {

	private Book[] bookList = new Book[10];
	private Book[] selection = new Book[10];
	private Order[] cart = new Order[10];
	private final double physicalPrice = 50.00;
    private final double eBookPrice = 8.00;
    
	public BookStore() {

		seedData();
	}

	public void displayAll() {

		for (int i = 0; i < bookList.length; i++) {

			if (bookList[i] != null) {

				System.out.println(String.format("%d. %s", i + 1, bookList[i].toString()));
			}
		}
	}

	public boolean seedData() {

		if (bookList[0] != null) {
			
			return false;
		}

		Book book = new Book("Absolute Java", "Frank", 0, true);
		Book book2 = new Book("JAVA : How to Program", "Duncas", 4, true);
		Book book3 = new Book("Computing Concept swith JAVA 3 Essentials", "Mac", 7, false);
		Book book4 = new Book("Java Software Solutions", "Brendon", 2, true);

		bookList = addBookToArray(book, bookList);
		bookList = addBookToArray(book2, bookList);
		bookList = addBookToArray(book3, bookList);
		bookList = addBookToArray(book4, bookList);

		return true;

	}

	public Book[] addBookToArray(Book book, Book[] books) {

		if (books[books.length - 1] == null) {

			for (int i = 0; i < books.length; i++) {

				if (books[i] == null) {

					books[i] = book;
					break;
				}
			}
		}
		else {

			Book[] temp = new Book[books.length + 1];

			for (int i = 0; i < books.length; i++) {

				temp[i] = books[i];
			}

			books = temp;
			books[books.length - 1] = book;
			
		}
        return books;
	}
	
	public boolean searchBookByTitle(String title) {
		
		selection = new Book[10];
		boolean found = false;
		
		for (int i = 0; i < bookList.length; i++ ) {
			
			if(bookList[i] != null) {
				
				if (bookList[i].getTitle().toLowerCase().contains(title.toLowerCase())) {
					
					selection = addBookToArray(bookList[i], selection);
					found = true;
				}
			}			
		}
		return found;
	}
	
	public void displaySearch(String input) {
		
		System.out.println("\nThe following title is a match:");
		
		for (int i = 0; i < selection.length; i++) {
			
			if(selection[i] != null) {
				
				System.out.println(String.format("%d. %s -- %s", i+1, selection[i].getTitle(),
						selection[i].getAuthor()));
			}			
		}
		System.out.println("0. cancel");
		
	}
	
	public Book[] getSelection() {
		
		return selection;
	}
	
    public Order[] getCart() {
		
		return cart;
	}
	public void addBookToCart(Order order) {
		
		if (cart[cart.length - 1] == null) {

			for (int i = 0; i < cart.length; i++) {

				if (cart[i] == null) {

					cart[i] = order;
					break;
				}
			}
		}
		else {

			Order[] temp = new Order[cart.length + 1];

			for (int i = 0; i < cart.length; i++) {

				temp[i] = cart[i];
			}

			cart = temp;
			cart[cart.length - 1] = order;
			
		}
	}
	
	public void displayCart() {
		
		if(cart[0] == null) {
			
			System.out.println("Shopping cart is empty.");
		}
	
		for (int i = 0; i < cart.length; i++) {

			if (cart[i] != null) {

				System.out.println(String.format("%d. %s, %s, %s", i + 1, cart[i].getBook().getTitle(), cart[i].getPhysicalCopy(), cart[i].getEbook()));
			}
		}
	}
	
	public void removeItemFromCart(int num) {
		
		Order[] temp = new Order[cart.length];
		int count = 0;
		
		if(cart[0] == null) {
			
			System.out.println("Shopping cart is empty.");
		}
		else {
			
			if(num >= 0 && num < cart.length) {
				
				for (int i = 0; i < cart.length; i++) {
					
					if(num == i) {
						
						cart[i] = null;
					}
				}
				
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
	
	public void checkout() {
		
		double checkout = 0;
		
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
			
			System.out.println("You have purchased items to the total value of $" + checkout);
			System.out.println("Thanks for shopping with Daintree!");
		}
		else {
			
			System.out.println("Shopping cart isempty.");
		}	
	}

	public void updateStoreCopy() {
		
		for(int i = 0; i < cart.length; i++) {
			
			for(int j = 0; j < bookList.length; j++) {
				
				if(cart[i] != null && bookList[j] != null) {
					
					if(cart[i].getPhysicalCopy() == true) {
						
						if(cart[i].getBook().equals(bookList[j])) {
							
							bookList[j].setNum(bookList[j].getNum() - 1);
						}
					}				
				}			
			}
		}
	}
}
