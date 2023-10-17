import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


public class OOPH1 {
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	ArrayList<String> movieNames = new ArrayList<String>();
	ArrayList<String> roomTypes = new ArrayList<String>();
	ArrayList<Integer> priceList = new ArrayList<Integer>();
	ArrayList<String> ticketID = new ArrayList<String>();
	ArrayList<String> usernames = new ArrayList<String>();
	ArrayList<Integer> seatsList = new ArrayList<Integer>();
	ArrayList<Integer> seatsBook = new ArrayList<Integer>();
	
	String movieName;
	String roomType;
	String id;
	String key;
	String username;
	Integer seats;
	Integer seatBook;
	Integer price;
	
	public OOPH1() {
		int command;
		int seats = 0;
		do {
			do {
				clearScreen();
				startMenu();
				command = scan.nextInt();
			}while(command < 0 || command > 4);
			
			switch (command) {
			case 1:
				do {
					System.out.print("Input the movie name [must be between 3 - 15 characters] : ");
					movieName = scan.nextLine();
					if(movieName.length() < 3 || movieName.length() > 15) {
						System.out.println("movie name must be between 3 - 15 characters");
					}
				}while(movieName.length() < 3 || movieName.length() > 15);
				
				do {
				    System.out.print("Input the room type [must be either (Regular | Premier) (case insensitive)]: ");
				    roomType = scan.nextLine();
				    if(!(roomType.equalsIgnoreCase("Regular") || roomType.equalsIgnoreCase("Premier"))) {
				        System.out.println("room type must be either (Regular | Premier)");
				    }
				}while(!(roomType.equalsIgnoreCase("Regular") || roomType.equalsIgnoreCase("Premier")));
				
				do {
					System.out.print("Input price [must be more than 0] : ");
					price = scan.nextInt();
					if(price < 0) {
						System.out.println("Price must be more than 0");
					}
				}while(price < 0);
				
				movieNames.add(movieName);
				roomTypes.add(roomType);
				priceList.add(price);
				
				if(roomType.equalsIgnoreCase("Regular")) {
		        	seats = 30;
		        } else if(roomType.equalsIgnoreCase("Premier")) {
		        	seats = 15;
		        }
				seatsList.add(seats);
				seatsBook.add(0);
				
				int randomDigit = rand.nextInt(1000);
				id = String.format("TI%03d", randomDigit);
				ticketID.add(id);
				
				systemPause();
				System.out.println("");
			
				break;
			case 2:
				clearScreen();
				headerCustomerList();
				if(usernames.isEmpty()) {
					System.out.println("| There's no data....                                                     |");
					System.out.println("===========================================================================");
				} else {
					for (int i = 0 ; i < usernames.size() ; i++) {
						System.out.printf("| %-10s | %-11s | %-13s | %-13d | %-12d |\n", usernames.get(i), ticketID.get(i), roomTypes.get(i), seatsBook.get(i), priceList.get(i) * seatsBook.get(i));
						System.out.println("============================================================================");
				    }
				}
				systemPause();
				System.out.println("");
				break;
			case 3:
				clearScreen();
				headerOwnerList();
				if(movieNames.isEmpty()) {
					System.out.println("| There's no data....                                                      |");
					System.out.println("============================================================================");;
					systemPause();
					break;
				} else {
					for (int i = 0 ; i < movieNames.size() ; i++) {
						System.out.printf("| %-9s | %-25s | %-13s | %-7d | %-10d |\n", ticketID.get(i), movieNames.get(i), roomTypes.get(i), seatsList.get(i), priceList.get(i));
                        System.out.println("================================================================================");
                    }
                    do {
                    	System.out.print("Input the customer name [must be between 3 - 10 characters] [0 to exit]: ");
                        username = scan.nextLine();
                        if(username.equals("0")) {
                        	break;
                        }
                    }while(username.length() < 3 || username.length() > 10);
                    
                    if(username.equals("0")) {
                    	clearScreen();
                    	System.out.println("");
                    	break;
                    }
					
                    boolean validID = false;
                    do {
                    	System.out.print("Choose the ID for booking the ticket : ");
    					id = scan.nextLine();
    					if(ticketID.contains(id)) {
    						validID = true;
    					} else {
    						System.out.println("Invalid ticket ID...");
    					}
                    }while(!validID);
					
                    int index = ticketID.indexOf(id); 
                    int selectedSeats = seatsBook.get(index); 
                    
					System.out.print("Input the total seats that want to be book : ");
					int maxAvailableSeats = seatsList.get(index);
					int maxSeats = Math.min(maxAvailableSeats, 30); 

					do {
					    seatBook = scan.nextInt();
					    if (seatBook <= 0) {
					        System.out.println("Seats cannot be negative. Please enter a non-negative number.");
					    } else if (seatBook > maxSeats) {
					        System.out.println("Seats cannot exceed the maximum available seats (" + maxAvailableSeats + ").");
					    }
					} while (seatBook < 0 || seatBook > maxSeats);

					if (seatBook == 0) {
					    break; 
					}
					
					if (seatBook > 0) {
					    seatsList.set(index, maxAvailableSeats - seatBook);
					    seatsBook.set(index, selectedSeats + seatBook);
					}
					
					usernames.add(username);
					
					systemPause();
					System.out.println("");
				}
				break;
			case 4:
			    int option;
			    do {
			        do {
			            clearScreen();
			            sortingMenu();
			            option = scan.nextInt();
			        } while (option < 0 || option > 2);

			        switch (option) {
			            case 1:
			                bubbleSortAscending();
			                displayMovieData();
			                break;
			            case 2:
			            	bubbleSortDescending();
			                displayMovieData();
			                break;
			            case 0:
			                break;
			        }
			    } while (option != 0);

			    systemPause();
			    System.out.println("");
			    break;
			    
			case 0:
				exitMenu();
				break;
			}
		}while(command != 0);
	}
	
	public void startMenu() {
		System.out.println("$$\\    $$\\   $$\\     $$$$$$\\");
		System.out.println("$$ |   $$ |  $$ |    \\_$$  _|");
		System.out.println("$$ |   $$ |$$$$$$\\     $$ |  $$\\   $$\\ ");
		System.out.println("\\$$\\  $$  |\\_$$  _|    $$ |  \\$$\\ $$  |");
		System.out.println(" \\$$\\$$  /   $$ |      $$ |   \\$$$$  / ");
		System.out.println("  \\$$$  /    $$ |$$\\   $$ |   $$  $$<");
		System.out.println("   \\$  /     \\$$$$  |$$$$$$\\ $$  /\\$$\\");
		System.out.println("    \\_/       \\____/ \\______|\\__/  \\__|");
		System.out.println("");
		System.out.println("1. Add Ticket");
		System.out.println("2. View List Order");
		System.out.println("3. Manage Order");
		System.out.println("4. Sorting");
		System.out.println("0. Exit");
		System.out.print(">> ");
	}
	
	public void exitMenu() {
		System.out.println("                              Thank you for using the application!");
        System.out.println("                             .:^:.. ..^^:..");
        System.out.println("                          ...   ...:.     .......    .");
        System.out.println("                            .   . ^.   :..:~~77~~~::..::.. ..");
        System.out.println("                          ::::..~..:..:~7~.: :^ ~^:~..:....:^~^:^~^");
        System.out.println("                        ..      .. .^!777^~~!?~^~7:^::.  .5BGB##&#7.");
        System.out.println("                             .:~!!. .:~^.77?P57P7!^::~::?5B&&#GGG5.");
        System.out.println("                          .:^~~^^: YJJGBGP5YY7J7!^^^?7YG##P7^7#&B~:");
        System.out.println("                        .^~~:  ~J55PJ~:.       ..~JPBBBY^. .:.JG!:!!^..");
        System.out.println("                     ..^7~  .::???.         .~!JG&&#J:       .7!:.^77?!^..");
        System.out.println("                   .:~7!:. ^Y^.  ..      .!JPB&&#Y:          .  :7!: ~G5?!:.");
        System.out.println("                 .:~7?7..~!!       .:..^?YP&&&G7         .        ~J: .BP7J!.");
        System.out.println("               .:^777~^::7^          :JB&&#BG^ .                   .J! !YYYJ?^.");
        System.out.println("              .:!JJJJ: :7G.         ?#&&&&B~                         ?5..^!~~!!:.");
        System.out.println("             .:!YYY5~ .J&Y      . .5BBBBBG^                           YP  :?J!~!^.");
        System.out.println("             .:!J?YJ^..P&!        P&&&&&#!             ..              #7 .^55?^~:");
        System.out.println("              .^7!??^..P#? .  .   ~#&&&&&J:..   ...  .   ..  ...     . 5#..:J57~!.");
        System.out.println("              .:!J~~^.?G#5      .:.^JGBBBP5Y!^75GBGP5PJYPGPP5YYJ?!^    !&:.:??~?~:.");
        System.out.println("               .^?5^:.JPBP!       .:::.. .!YYY&&&###&&&&YG##&&&&#B5:   !&:.^~!5J~.");
        System.out.println("                .^??:.^^?77..                 ^:.  ..P&G:^&&&&&&&&7    PG ~7:JP7:.");
        System.out.println("                .:^7? .~~J?7!                 .      7#^ P&&&&&&G^    :&^ 7?5?!:.");
        System.out.println("                  .^J5.:!JPBBY            .:.       ^5^ 7###&#P^      G7..7G5!:.");
        System.out.println("                   .^?5~^~:~PBP~.?!~: ^^7~^!^     .7: ~#&&#5~        ~~:.:PY~:.");
        System.out.println("                    .^!5J7..~!B&PJY?J^J?YGY?7!:..^^:?#@&G7.        .~.::^Y!^.");
        System.out.println("                     .:^??J!77JY57 .: :?#BYGG!7~:7G&#5~.           .~^!J5!:.");
        System.out.println("                       .:~?JY~..... :.^!J^^!7~7PGPJ~.          ... ^?Y57:.");
        System.out.println("                          :~75P~:.  ^^!!^~!JYJ57:..^^....:^!:  ...5GP7^.");
        System.out.println("                          ::.:~^7.JY^?77Y5Y7^..::~??5J???5J: :?Y!J5!:.");
        System.out.println("                           .  .^.7G&###B5!^:.:.::......~?J?YB#BY7^:.");
        System.out.println("                              .^^YJ5###BG5J7!7JYPPGYJ??YPPPY?7~:.");
        System.out.println("                              ..:^~^^........^~!~!!!!~^^::...");
        System.out.println("                       o---------------------------------------------o");
        System.out.println("                       |      Breaking and Overcoming Challenges     |");
        System.out.println("                       |    Through Courage Hardwork and Persistence |");
        System.out.println("                       o---------------------------------------------o");
        System.out.println("");
	}
	
	public void headerOwnerList() {
		System.out.println("================================================================================");
	    System.out.println("| Ticket ID |         Movie Name        |   Type Room   |  Seats  |    Price   |");
	    System.out.println("================================================================================");
	}
	
	public void headerCustomerList() {
		System.out.println("===========================================================================");
	    System.out.println("|  Username  |  Ticket ID  |   Room Type   |  Total Seats  |  Total Price |");
	    System.out.println("===========================================================================");
	}
	
	public void systemPause() {
		scan.nextLine();
		System.out.print("Press any key to back to the main menu...");
		scan.nextLine();
	}
	
	public void sortingMenu() {
		System.out.println("Sorting Menu");
		System.out.println("===============");
		System.out.println("1. Ascending");
		System.out.println("2. Descending");
		System.out.println("0. Exit");
		System.out.print(">> ");
	}
	
	public void bubbleSortAscending() {
	    int n = movieNames.size();
	    for(int i = 0 ; i < n - 1 ; i++){
	        for(int j = 0 ; j < n - i - 1 ; j++){
	            if(movieNames.get(j).compareTo(movieNames.get(j + 1)) > 0) {
	                Collections.swap(movieNames, j, j + 1);
	                Collections.swap(ticketID, j, j + 1);
	                Collections.swap(roomTypes, j, j + 1);
	                Collections.swap(seatsList, j, j + 1);
	                Collections.swap(priceList, j, j + 1);
	            }
	        }
	    }
	}
	
	public void bubbleSortDescending() {
	    int n = movieNames.size();
	    for(int i = 0 ; i < n - 1 ; i++){
	        for(int j = 0 ; j < n - i - 1 ; j++){
	            if(movieNames.get(j).compareTo(movieNames.get(j + 1)) < 0) {
	                Collections.swap(movieNames, j, j + 1);
	                Collections.swap(ticketID, j, j + 1);
	                Collections.swap(roomTypes, j, j + 1);
	                Collections.swap(seatsList, j, j + 1);
	                Collections.swap(priceList, j, j + 1);
	            }
	        }
	    }
	}

	public void displayMovieData() {
	    clearScreen();
	    headerOwnerList();
	    for (int i = 0; i < movieNames.size(); i++) {
	        System.out.printf("| %-9s | %-25s | %-13s | %-7d | %-10d |\n", ticketID.get(i), movieNames.get(i), roomTypes.get(i), seatsList.get(i), priceList.get(i));
	        System.out.println("================================================================================");
	    }
	    systemPause();
	    System.out.println("");
	}

	
	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void main(String[] args) {
		new OOPH1();
	}
	
}
