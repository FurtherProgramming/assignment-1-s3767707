package com.mycompany.app;

/*
 * Class:		Order
 * Description:	An order object is create when customer add a book to shopping cart 
 * Author:		Anson Go Guang Ping
 */
public class Order {

	//order attributes
	private Book book;
	private boolean phyicalCopy;//return true if user buy a physical copy of the book
	private boolean eBook;//return true if user buy a physical copyof the book
	
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
