//package app;

import java.util.Scanner;

//import books.Book;
//import books.Order;

/*
 * Class:		Menu
 * Description:	The class a menu and is used to interact with the user. 
 * Author:		Anson Go Guang Ping
 */
public class Menu {
	private Scanner console = new Scanner(System.in);
    private final String numericRegex = "[0-9]+";	
    private BookStore bookStore = new BookStore();
	
    /*
	 * Runs the menu in a loop until the user decides to exit the system.
	 */
	public void run() {
		
		String input;
		int choice = -1;
		
		do {
			
			printMenu();
            input = console.nextLine();
            
            System.out.println();

			switch (input) {
			case "1":				
				promptUserToAddItem();
				break;
				
			case "2":
				bookStore.displayCart();
				break;
				
			case "3":
				promptUserToRemoveItem();
				break;
				
			case "4":
				bookStore.checkout();
				break;
				
			case "5":
				bookStore.displayAll();
				break;
				
			case "0":
				choice = 0;	
				System.out.println("Exiting Program ... Goodbye!");
				break;
			
			default:
				System.out.println("Error, invalid option selected!");
				System.out.println("Please try Again...");
            
			}
	        
		}while(choice != 0);

	}

	
	/*
	 * Prints the menu.
	 */
	public void printMenu() {
		
		System.out.printf("\n******* Welcome to Daintree! *******\n\n");

		System.out.printf("%-30s\n", "1. Add a book to shopping cart");
		System.out.printf("%-30s\n", "2. View shopping cart");
		System.out.printf("%-30s\n", "3. Remove a book from shopping cart");
		System.out.printf("%-30s\n", "4. Checkout");
		System.out.printf("%-30s\n", "5. List all books");
		System.out.printf("%-30s\n", "0. Quit");
		System.out.println("\nPlease make a selection: ");
		System.out.println("(Hit enter to cancel any operation)");
	}
	
    private void promptUserToAddItem() {
		
		System.out.println("Enter title to search for: ");
		String input = console.nextLine();
		boolean found = bookStore.searchBookByTitle(input);
		boolean match = false;
		Book[] selection = bookStore.getSelection();
		int num;

		
		if(found) {
			
			bookStore.displaySearch(input);
			System.out.println("Which number do you wish to purchase?");
			
			while(!match) {
				
				input = console.nextLine();
				match = input.matches(numericRegex);

                if(input.equals("0")) {
					
					break;
				}
				if(match) {
					
					num = Integer.parseInt(input);
					
					if(num > 0 && num < selection.length) {
										
					    System.out.println("\nPurchasing: " + selection[num - 1].getTitle());
					    purchaseEbook(input, selection, num);
					    					    			    
					}
					else {

						System.out.println("Invalid option! Please try again.");
						match = false;
					}
				}
				
				else {
					
					System.out.println("Invalid option! Please try again.");
				}
			}			
		}
		else {
			
			System.out.println("There is no match for that title.");
		}
	}
    
    public void purchaseEbook(String input, Book[] selection, int num) {
    	
    	boolean match2 = false;
    	boolean purchaseEbook = false;
    	boolean purchasePhyicalCopy = false;
    	
    	if(selection[num - 1].getNum() == 0) {
	    	
	    	System.out.println("Out of stock. Purchase failed");
	    }
	    else {
	    	
	    	purchasePhyicalCopy = true;
	    }
	    
	    System.out.println("Do you want to buy this as an eBook(y/n): ");
	    
    	while(!match2) {
	    	
	    	input = console.nextLine();
	    	if(input.equalsIgnoreCase("y")) {
	    		
	    		purchaseEbook = true;
	    		match2 = true;
	    		System.out.println("Ebook purchased successfully.");
	    	}
	    	else if(input.equalsIgnoreCase("n")) {
	    		
	    		purchaseEbook = false;
	    		match2 = true;
	    		System.out.println("Ebook not purchased.");
	    	}
	    	else {
	    		
	    		System.out.println("Invalid option. Please try again.");
	    	}
	    }
    	
    	if(purchasePhyicalCopy || purchaseEbook) {
	    	
	    	Order order = new Order(selection[num - 1], purchasePhyicalCopy, purchaseEbook);
		    bookStore.addBookToCart(order);
	    }	
    }
    
    private void promptUserToRemoveItem() {
    	
    	boolean isNumeric = false;
    	Order[] cart = bookStore.getCart();
    	
    	if(cart[0] != null) {
    		
    		System.out.println("Your shopping cart contains the following: ");
        	bookStore.displayCart();
        	System.out.println("0. cancel");
        	System.out.println("Which number item do you want to remove: ");
        	String input = console.nextLine();
        	isNumeric = input.matches(numericRegex);
        	
        	if(isNumeric) {
        		
        		int num = Integer.parseInt(input);
        		
        		if(num != 0) {
        			
        			bookStore.removeItemFromCart(num - 1);
        		}
        	}
        	else {
        		
        		System.out.println("Invalid option.");
        	}
        }
    	else {
    		
    		System.out.println("Your shopping cart is empty.");
    	}
    }
    	
}
