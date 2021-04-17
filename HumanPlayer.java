// Student Number: 2137259M

import java.util.Scanner; 

// "Create a subclass of your Player class, call it HumanPlayer" 
public class HumanPlayer extends Player {
	
	
	// Call the super constructor of Player, so we can give HumanPlayers an identifying 'char' to use on the Board too:
	public HumanPlayer(char identity) {
		super(identity);
	}
	
	
	// Over-riding Player's .move() method to meet HumanPlayer requirements:
	public boolean move(Board a) {
		// Initialise a Scanner to get the needed user input: 
		Scanner s = new Scanner(System.in);
		// "Writes a message to the standard output requesting an input integer and then waits until the player enters an integer between 1 and 6." 
		System.out.println("Human Player - your turn! Please enter an int between 1 and 6, followed by enter: ");
		// Get the int entered by the user and save it as a variable so we can use it:	
		int count = s.nextInt(); 		
		// Pass this user input to Player's '.movePlayerOnBoard()' method: 
		if (movePlayerOnBoard(count, a) == true) {
			return true; // If the HumanPlayer wins, return true 
		} else {
			return false;  // If the HumanPlayer hasn't won / hasn't won yet, return false 
		}
	}
}
	
