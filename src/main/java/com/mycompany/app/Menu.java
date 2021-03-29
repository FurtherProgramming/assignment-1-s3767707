package com.mycompany.app;

import java.util.Scanner;


/*
 * Class:		Menu
 * Description:	The class a menu and is used to interact with the user. 
 * Author:		Anson Go Guang Ping
 */
public class Menu {
	private Scanner console = new Scanner(System.in);
    private final String numericRegex = "[0-9]+";	
    private App bookStore = new App();
	
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
	
    /*
     * Prompt user to enter title to search for
     * IF found
       * DISPLAY search result
       * PROMPT user to select what item to purchase
       * WHILE user input incorrect(match = false)
         * IF user input = 0
           * THEN exit function
         * IF user input is numeric
           *IF user input is within range of selection list length
             * IF user input matches search result list
               * RUN purchaseEbook(String input, Book[] selection, int num) function
             * ELSE print error message (match = false)
           * ELSE print error message (match = false)
         * ELSE print error message (match = false)
       * ELSE print error message (match = false)
     */
	private void promptUserToAddItem() {
		
		System.out.println("Enter title to search for: ");
		String input = console.nextLine();
		boolean found = bookStore.searchBookByTitle(input);
		boolean match = false;
		Book[] selection = bookStore.getSelection();		
		int num;
		

		
		if(found) {
			
			bookStore.displaySearch();
			System.out.println("Which number do you wish to purchase?");
			
			while(!match) {
				
				input = console.nextLine();
				match = input.matches(numericRegex);

                if(input.equals("0")) {
					
					break;
				}
				if(match) {
					
					num = Integer.parseInt(input);
					
					//only one item can be selected to purchase each time
					if(num > 0 && num < selection.length) {	
						
						if(selection[num - 1] != null) {
							
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
    
	/*
	 * FOR shopping cart array
	   * IF cart item not null
	     * IF cart item title equals to selection item title
	       * INCREASE count by 1
	     * ELSE exit loop
	   * ELSE exit loop      
	 * IF selected book item has 0 hard copy
	   * PRINTS error message
	 * ELSE boolean purchasePhysicalCopy = true 
	 * PROMPTS user if he wants to but eBook version
	 * WHILE not match2 
	   * GET user input
	   * IF user input equals to y or Y 
	     * IF book has an ebook version
	       * BOOLEAN purchaseEbook = true
	       * PRINTS success purchase message
	     * ELSE
	       * PRINTS no ebook for that book
	     * BOOLEAN match2 = true   
	   * ELSE IF user input equals to n or N
	     * IF book has an ebook version
	       * BOOLEAN purchaseEbook = false
	       * PRINTS "Ebook not purchased."
	     * ELSE
	       * PRINTS no ebook for that book
	     * BOOLEAN match2 = true   
	   * ELSE prints error message
	 * IF user buy physical copy or eBook version
	   * CREATE a new order object
	   * ADD order to cart  
	 */
    public void purchaseEbook(String input, Book[] selection, int num) {
    	
    	boolean match2 = false;//returns true if user input equals ignore case to y or n
    	boolean purchaseEbook = false;// variable to indicate whether user buy eBook versiom
    	boolean purchasePhyicalCopy = false;// variable to indicate whether user buy physical copy
    	Book[] bookList = bookStore.getBookList();
		Order[] cart = bookStore.getCart();
		int count = 0;
		
    	for(int i = 0; i < cart.length; i++) {
	    	
	    	if(cart[i] != null) {
	    		
	    		if(cart[i].getBook().getTitle().equals(selection[num - 1].getTitle())) {
		    		
		    		count++;
		    	}
	    	}
	    }
    	
        if(selection[num - 1].getNum() == 0 || count == selection[num - 1].getNum()) {
	    	
	    	System.out.println("Out of stock. Purchase failed");
	    }
        
	    else {
	    	
	    	purchasePhyicalCopy = true;
	    }
	    
 
	    System.out.println("Do you want to buy this as an eBook(y/n): ");
	    
    	while(!match2) {
	    	
	    	input = console.nextLine();
	    	if(input.equalsIgnoreCase("y")) {
	    		
	    		if(selection[num -1].eBookAvailability() == true) {
	    			
	    			purchaseEbook = true;
		    		System.out.println("Ebook purchased successfully.");
	    		}
	    		else {
	    			
	    			System.out.println("There is no ebook available for that title.");
	    		}
	    		match2 = true;
	    	}
	    	else if(input.equalsIgnoreCase("n")) {
	    		
	    		if(selection[num -1].eBookAvailability() == true) {
	    			
	    			purchaseEbook = false;
		    		System.out.println("Ebook not purchased.");    		
	    		}
                else {
	    			
	    			System.out.println("There is no ebook available for that title.");
	    		}
	    		match2 = true;
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
    
    /*
     * Function to indicate which item user want to remove from shopping cart
     */
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
        	
        	//Make sure user input is numeric so no exception when run
        	if(isNumeric) {
        		
        		int num = Integer.parseInt(input);
        		
        		//Check if item selected is valid
        		if(num > 0 && num < cart.length) {	
					
					if(cart[num - 1] != null) {						
        			
        			    bookStore.removeItemFromCart(num - 1);
        		    }
					else {
						
						System.out.println("Invalid option.");
					}
        		}
        		else {
					
					System.out.println("Invalid option.");
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
