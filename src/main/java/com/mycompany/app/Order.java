//package books;

/*
 * Class:		Order
 * Description:	An order object is create when customer add a book to shopping cart 
 * Author:		Anson Go Guang Ping
 */
public class Order {

	//order attributes
	private Book book;
	private boolean phyicalCopy;//act as a tracker for price calculation during checkout later
	private boolean eBook;//act as a tracker for price calculation during checkout later
	
	public Order(Book book, boolean phyicalCopy, boolean eBook) {
		
		this.book = book;
		this.phyicalCopy = phyicalCopy;
		this.eBook = eBook;
	}
	
	public Book getBook() {
		
		return book;
	}
	
	public boolean getPhysicalCopy( ) {
		
		return phyicalCopy;
	}
	
	public boolean getEbook() {
		
		return eBook;
	}
}
