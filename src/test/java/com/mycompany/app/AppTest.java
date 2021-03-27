package com.mycompany.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;;
	private static App bs;
	private static Menu menu;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
				
		bs = new App();
		menu = new Menu();
	}

	@BeforeEach
	public void setUp() throws Exception {
		
		System.setOut(new PrintStream(outContent));
	}
 
	@AfterEach
	public void restore() {
		
		System.setOut(originalOut);
		bs = new App();
		
	}
	
	@org.junit.jupiter.api.Test
	public void testDisplayAllFunction_returnList_ifListNotEmpty() {
		
		bs.displayAll();
		assertEquals("1. Absolute Java -- Frank, 1 copies, ebook available\n"
				+ "2. JAVA : How to Program -- Duncas, 4 copies, ebook available\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac, 7 copies, no ebook\n"
				+ "4. Java Software Solutions -- Brendon, 2 copies, ebook available\n"
				, outContent.toString());
		
	} 
	
	@org.junit.jupiter.api.Test
	public void testDisplayAllFunction_returnList_ifBookIsAddToCartButNotCheckout() {
		
		Book[] bookList = bs.getBookList();
		Order order = new Order(bookList[3], true, true);
		Order order2 = new Order(bookList[3], true, true);
		
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
        bs.displayAll();
        
		assertEquals("1. Absolute Java -- Frank, 1 copies, ebook available\n"
				+ "2. JAVA : How to Program -- Duncas, 4 copies, ebook available\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac, 7 copies, no ebook\n"
				+ "4. Java Software Solutions -- Brendon, 0 copies, ebook available\n"
				, outContent.toString());
		
	} 
	
	@org.junit.jupiter.api.Test
	public void testDisplayAllFunction_returnList_ifBookIsAddToCartAndCheckout() {
		
		Book[] bookList = bs.getBookList();
		Order order = new Order(bookList[2], true, true);
		Order order2 = new Order(bookList[3], true, true);
		
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.checkout();
		bs.displayAll();

		assertEquals("You have purchased items to the total value of $116.0.\n"
				+ "Thanks for shopping with Daintree!\n"
				+ "1. Absolute Java -- Frank, 1 copies, ebook available\n"
				+ "2. JAVA : How to Program -- Duncas, 4 copies, ebook available\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac, 6 copies, no ebook\n"
				+ "4. Java Software Solutions -- Brendon, 1 copies, ebook available\n"
				, outContent.toString());
		
	} 

	@org.junit.jupiter.api.Test
	public void testDisplayAllFunction_returnEmptyString_ifListIsEmpty() {
		
		bs.deleteBookList();
		bs.displayAll();
		assertEquals("", outContent.toString());
		
	} 
	
	@org.junit.jupiter.api.Test
    public void addBookToArrayFunction_returnNewList_ifAddOneNewBook() {
		
		Book book = new Book("Junit 5", "Edward", 9, false);
		bs.addBookToArray(book, bs.getBookList());
		bs.displayAll();
		assertEquals("1. Absolute Java -- Frank, 1 copies, ebook available\n"
				+ "2. JAVA : How to Program -- Duncas, 4 copies, ebook available\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac, 7 copies, no ebook\n"
				+ "4. Java Software Solutions -- Brendon, 2 copies, ebook available\n"
				+ "5. Junit 5 -- Edward, 9 copies, no ebook\n", outContent.toString());
		
	}
	
	@org.junit.jupiter.api.Test
    public void addBookToArrayFunction_returnNewList_ifAddTwoNewBook() {
		
		Book book = new Book("Junit 5", "Edward", 9, false);
		Book book2 = new Book("Java Class", "Savannh", 4, true);
		bs.addBookToArray(book, bs.getBookList());
		bs.addBookToArray(book2, bs.getBookList());
		bs.displayAll();
		assertEquals("1. Absolute Java -- Frank, 1 copies, ebook available\n"
				+ "2. JAVA : How to Program -- Duncas, 4 copies, ebook available\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac, 7 copies, no ebook\n"
				+ "4. Java Software Solutions -- Brendon, 2 copies, ebook available\n"
				+ "5. Junit 5 -- Edward, 9 copies, no ebook\n"
				+ "6. Java Class -- Savannh, 4 copies, ebook available\n", outContent.toString());
	}

	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnFalse_IfTitleIsempty() {
		
		Book[] selection = bs.getSelection();
		assertFalse(bs.searchBookByTitle(""));
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnTrue_IfTitleIsValidAndLowerCase() {
		
		Book[] selection = bs.getSelection();
		assertTrue(bs.searchBookByTitle("java"));
		
		
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnTrue_IfTitleIsValidAndUpperCase() {
		
		Book[] selection = bs.getSelection();
		assertTrue(bs.searchBookByTitle("JAVA"));
		
		
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnTrue_IfTitleIsValidAndMixCase() {
		
		Book[] selection = bs.getSelection();
		assertTrue(bs.searchBookByTitle("Java"));
				
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnFalse_IfTitleIsInvalid() {
		
		Book[] selection = bs.getSelection();
		assertFalse(bs.searchBookByTitle("Python"));
		
		
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnFalse_IfTitleIsNumericAndValid() {
		
		Book[] selection = bs.getSelection();
		assertTrue(bs.searchBookByTitle("3"));
		
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnFalse_IfTitleIsNumericAndInvalid() {
		
		Book[] selection = bs.getSelection();
		assertFalse(bs.searchBookByTitle("12"));
		
	}
	
	@org.junit.jupiter.api.Test
	public void searchBookByTitleFunction_returnFalse_IfTitleIsNewBook() {
		
		Book book = new Book("Anonymous", "Edward", 10, true);
		bs.addBookToArray(book, bs.getBookList());
		Book[] selection = bs.getSelection();
		assertTrue(bs.searchBookByTitle("Anonymous"));
		
	}
	
	@org.junit.jupiter.api.Test
	public void displaySearchFunction_returnSelectionList_IfSearchTitleFound() {
		
		Book[] selection = bs.getSelection();
		bs.searchBookByTitle("Java");
		bs.displaySearch();
		assertEquals("\nThe following title is a match:\n"
				+ "1. Absolute Java -- Frank\n"
				+ "2. JAVA : How to Program -- Duncas\n"
				+ "3. Computing Concepts with JAVA 3 Essentials -- Mac\n"
				+ "4. Java Software Solutions -- Brendon\n"
				+ "0. cancel\n"
				, outContent.toString());
		
	}
	
	@org.junit.jupiter.api.Test
	public void displaySearchFunction_returnSelectionList_IfSearchTitleNotFound() {
		
		Book[] selection = bs.getSelection();
		bs.searchBookByTitle("Anonymous");
		bs.displaySearch();
		assertEquals("No match found.\n", outContent.toString());
		
	}
	
	@org.junit.jupiter.api.Test
	public void addBookToCartFunction_returnItemAddedToCart_IfNewPhysicalBookAdded() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[0], true, false);
		bs.addBookToCart(order);
		assertNotNull(cart[0]);
	}
	
	@org.junit.jupiter.api.Test
	public void addBookToCartFunction_returnItemAddedToCart_IfNewEBookAdded2() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[0], false, true);
		bs.addBookToCart(order);
		assertNotNull(cart[0]);
	}
	
	@org.junit.jupiter.api.Test
	public void addBookToCartFunction_returnItemAddedToCart_IfNewBookAdded() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		assertEquals("Absolute Java", cart[0].getBook().getTitle());
		assertEquals("JAVA : How to Program", cart[1].getBook().getTitle());
	}
	
	@org.junit.jupiter.api.Test
	public void displayCartFunction_returnList_IfCartHasOneItemWithPhysicalCopyAndEbookVersion() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[2], true, true);		
		bs.addBookToCart(order);
		bs.displayCart();
		assertEquals("1. Computing Concepts with JAVA 3 Essentials, physical copy version, ebook version\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void displayCartFunction_returnList_IfCartHasTwoItem() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.displayCart();
		assertEquals("1. Absolute Java, physical copy version, ebook version\n"
				+ "2. JAVA : How to Program, physical copy version, ebook version\n"
				, outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void displayCartFunction_returnList_IfCartHasOneItemWithPhysicalCopyVersion() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[2], true, false);		
		bs.addBookToCart(order);
		bs.displayCart();
		assertEquals("1. Computing Concepts with JAVA 3 Essentials, physical copy version\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void displayCartFunction_returnList_IfCartHasOneItemWithEbookVersion() {
		
		Book[] books= bs.getBookList();
		Order[] cart = bs.getCart();
		Order order = new Order(books[2], false, true);		
		bs.addBookToCart(order);
		bs.displayCart();
		assertEquals("1. Computing Concepts with JAVA 3 Essentials, ebook version\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void displayCartFunction_returnMessage_IfCartIsEmpty() {
		
		bs.displayCart();
		assertEquals("Shopping cart is empty.\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void removeItemFromCartFunction_returnUpdatedCart_IfOneItemRemoved() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.removeItemFromCart(0);
		Order[] cart = bs.getCart();
		assertEquals("JAVA : How to Program", cart[0].getBook().getTitle());
	}
	
	@org.junit.jupiter.api.Test
	public void removeItemFromCartFunction_returnUpdatedCart_IfTwoItemRemoved() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.removeItemFromCart(0);
		bs.removeItemFromCart(0);
		Order[] cart = bs.getCart();
		assertEquals(null, cart[0]);
	}
	
	@org.junit.jupiter.api.Test
	public void removeItemFromCartFunction_returnMessage_IfTItemRemovedFromEmptycart() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.removeItemFromCart(0);
		bs.removeItemFromCart(0);
		bs.removeItemFromCart(0);
		Order[] cart = bs.getCart();
		assertEquals("Item removed from shopping cart.\n"
				   + "Item removed from shopping cart.\n"
				   + "Shopping cart is empty.\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void checkoutFunction_returnCorrectValue_IfCheckoutOneBook() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, false);
		bs.addBookToCart(order);
		bs.checkout();
		assertEquals("You have purchased items to the total value of $50.0.\n"
				   + "Thanks for shopping with Daintree!\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void checkoutFunction_returnCorrectValue_IfCheckoutTwoBook1() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, true);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.checkout();
		assertEquals("You have purchased items to the total value of $116.0.\n"
				   + "Thanks for shopping with Daintree!\n", outContent.toString());
		
	}
	
	@org.junit.jupiter.api.Test
	public void checkoutFunction_returnCorrectValue_IfCheckoutTwoBook2() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, true);
		Order order2 = new Order(books[1], true, false);
		bs.addBookToCart(order);
		bs.addBookToCart(order2);
		bs.checkout();
		assertEquals("You have purchased items to the total value of $108.0.\n"
				   + "Thanks for shopping with Daintree!\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void checkoutFunction_returnCorrectValue_IfNoBookToCheckout() {
		
		Book[] books= bs.getBookList();
		bs.checkout();
		assertEquals("Shopping cart is empty.\n", outContent.toString());
	}
	
	@org.junit.jupiter.api.Test
	public void updateStoreCopyFunction_returnChanges_IfBuyOneBookOneTime() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[0], true, false);
		bs.addBookToCart(order);
		bs.updateStoreCopy();
		assertEquals(0, books[0].getNum());
	}
	
	@org.junit.jupiter.api.Test
	public void updateStoreCopyFunction_returnChanges_IfOneBookIsBoughtTwoTimes() {
		
		Book[] books= bs.getBookList();
		Order order = new Order(books[1], true, false);
		bs.addBookToCart(order);
		bs.addBookToCart(order);
		bs.updateStoreCopy();
		assertEquals(2, books[1].getNum());
	}
}
