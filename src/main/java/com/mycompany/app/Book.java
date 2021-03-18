//package books;

/*
 * Class:		Book
 * Description:	The class represents a book in a book purchasing system. 
 * Author:		Anson Go Guang Ping
 */
public class Book {
	
	//book attributes
	private String title;
	private String author;
	private int numOfPhysicalCopy;
	private boolean eBookAvailability;

	public Book(String title, String author, int numOfPhysicalCopy, boolean eBookAvailability) {

		if (title == null) {
			
			throw new IllegalArgumentException("invalid argument null");
		}	
		this.title = title;
		this.author = author;
		this.numOfPhysicalCopy = numOfPhysicalCopy;
		
		//a book which has hard copy always has a eBook version
		if(this.numOfPhysicalCopy > 0) {
			
			this.eBookAvailability = true;
		}
		else {
			
			this.eBookAvailability = eBookAvailability;
		}
		
	}

	public String getTitle() {

		return title;
	}
 
	public String getAuthor() {

		return author;
	}

	public int getNum() {

		return numOfPhysicalCopy;
	}
	
	public boolean eBookAvailability() {
		
		return eBookAvailability;
	}

	public void setNum(int numOfPhysicalCopy) {
	
		this.numOfPhysicalCopy = numOfPhysicalCopy;
	}
	
	public String toString() {
		String string = "";
		if(eBookAvailability == true) {
			
			string = String.format("%s -- %s, %d copies, ebook available", title, author, numOfPhysicalCopy);
		}
		else {
			
			string = String.format("%s -- %s, %d copies, no ebook", title, author, numOfPhysicalCopy);
		}
		return string;
	}
}
