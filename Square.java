public class Square {
	private int position; // "an integer position (default 0)
	private Player[] playerReferences = new Player[0]; // "references to any Player objects that are currently at a Square"
		// Default is zero because 'playerReferences' only changes if a Player object is *currently* at a Square object
	private int delta; // "an integer delta" (default 0) 
	
	
	// Default constructor
	public Square() {
		// default values given to each Square
		// position is changed with '.setPosition()', delta is changed with '.setDelta()', 
		// and playerReferences is updated with '.addPlayerToSquareArray()'(to add a Player) and '.removePlayer()'(to remove a Player). 
		// I decided to make the default position for a Square to be 0 because this task mainly involves creating an array of Squares which can be 
		// altered all at once with their own method ('.setBoard()' in Board) - we only make one individual Square as part of Task 1.
	}
	
		
	// Stage 1 Requirements: 
	// 1. "Square objects need a setDelta method" 
	public void setDelta(int delta) {
		// (If this isn't used, default value for a Square is delta = 0)
		this.delta = delta; 
	}
		
		
	// 2. "Squares should have a toString method" 
	public String toString() {
		// deltaPresent will print the delta in brackets padded to length four.
		// Used String.format here so that if double digit / single digit 
		// numbers were used as deltas, the Board would  
		// still align properly as opposed to using "(" + delta + ")" which 
		// could become messy if deltas are different lengths.
		String deltaPresent = "";
		deltaPresent += String.format("(%4d)", delta);
		// If delta is 0: Just a bracket with no delta - padded to the same length as
		// the delta in brackets to keep the board tidy.
		String noDelta = "";
		noDelta += String.format("(%4s)", "");
		if(delta != 0) {
			// '.printPlayerArray()' makes use of Player's toString() method
			// (which returns the char) but '.printPlayerArray()' is added here
			// just to ensure that (with the assumption of max 4 Players per game)
			// each Player in an array on the Board has a space between their displayed char - 
			// so 'printPlayerArray()' essentially just makes the Board display more tidy. 
			return printPlayerArray(playerReferences) + position + deltaPresent;
		} else {
			return printPlayerArray(playerReferences) + position + noDelta;
		} 
	}
	
		
	// Extra methods added:
		
		
	// to set a Square's position, otherwise the default for a Square object is 0
	// (Used in Board's '.setBoard()' method for getting the rows to wrap): 
	public void setPosition(int position) {
		this.position = position;
	}
		
		
	// to access a Square's position:
	// (Used in the helper methods of Board): 
	public int getPosition() {
		return this.position;
	}	
		

	// Used in the toString of Square to format the Player array to look more tidy on the Board: 
	// (Static because it is not operating on an instance of the Square class)
	public static String printPlayerArray(Player[] array){
		String output =""; 
			for(int i = 0; i < array.length; i++) {
				output += String.format("%s %1s" , array[i], ""); 
				// To create a space each Player when displayed on the Board
				// This is especially useful if more than one Player(s) is at the same Square - so they are not
				// joined together with no space in-between their 'char' identifiers.
			}
		return output; 
	}
				
	
	// Get the delta of a Square: 
	// (Used in Player's '.movePlayerOnBoard()' to determine if there is a snake / ladder): 
	public int getDelta() {
		return this.delta; 
	}
		
		
	// Getter for a Square's Player[] reference:
	// (Used in Player '.movePlayerOnBoard' when '.removePlayer()' is called) 
	public Player[] getPlayerReference() {
		return this.playerReferences; 
	}		
		
		
	// Used to update Square's Player[] playerReferences if a Player lands on that Square:
	// (used in Square's '.assignPlayer()' because a Square needs to hold references (an array)
	// to all Player(s) currently at the Square, which will change throughout the game):
	public Player[] addPlayerToSquareArray(Player a) {
		// get the length of the array: (will start at 0 at the beginning of the game)
		int currentLength = this.getPlayerReferenceLength();
		// add length of 1 (Players are only added one at a time):
		int newLength = currentLength + 1; 
		// Make an array with this increased length: 
		Player[] newArray = new Player[newLength];
		// Drop any old values into new array:
		for(int i = 0; i < currentLength; i++) {
			newArray[i] = playerReferences[i];
		}
		// reset the playerReferences
		playerReferences = newArray; 
		// add the passed Player argument to the array: 
		playerReferences[currentLength] = a; 
		// return the new array of increased length:
		return playerReferences; 
	}
		
	
	//Method that removes a Player from a Square's 'playerReference' array (used in .movePlayerOnBoard() method of Player):
	public Player[] removePlayer(Player name, Player[] array) {
		// Remove the Player from the Square's playerReference array: 
		for(int i=0; i < this.getPlayerReferenceLength(); i++) {
			// delete the Player we're removing from the array:
			// (make it null) 
			if (array[i].getIdentity() == name.getIdentity()) {
				array[i] = null; 
			}
			// call the deleteNull method to remove the null from the array: 
			playerReferences = deleteNull(array);
		}
		// Reset the Player's referenceToSquare to be null again (default value)
		name.setReferenceToSquare(null); 
		// return the new array: 
		return playerReferences;
	}				
				
						
	// Getter for the length of playerReferences in Square 
	// (Used in '.removePlayer()' and '.addPlayerToSquareArray()'
	public int getPlayerReferenceLength() {
		return this.playerReferences.length; 
	}	
	
		
	// Method to add Players to a specific Square: 
	// '.assignPlayer()' is called in Board's '.addPlayerToGame()' - The method for adding Players to the starting square 
	//  and holding a reference of Players for future use. 
	// '.assignPlayer()' is also used throughout Player's '.movePlayerOnBoard()' to move a Player to the relevant Square on the Board.
	public void assignPlayer(Player name) {
		// This is where an expandable array needs to come in - if a Player is assigned to this Square, Add 1 to the length of the array:
		addPlayerToSquareArray(name);
		// '.addPlayerToSquareArray()' adds the Player to the Square's Player[] playerReferences.
		// A Player can only hold reference to one Square, so now we just add the Square to the Player's 
		// referenceToSquare (Square object): 
		name.setReferenceToSquare(this);
	}
		
	
	// Method to remove the 'null' involved with a Square's 'Player[] playerReferences'. 
	// Without this method, 'NullPointerException' occurs when a Player is removed from the array.
	// '.deleteNull()' is called from '.removePlayer()' because a null element will occur if a Player is
	// removed from a Square's 'Player[] playerReferences' (when a Player moves Square on the Board):
	public Player[] deleteNull(Player[] a) {
		int counter = 0;
		// Step One: push the non-null element to the front of the array: 
		boolean found = false;
		// This 'for loop' goes through the array, and pushes the non-null element to the front: 
		// (The front of the array needs to contain all the non-null elements, to help us
		// make a new array of only the non-null elements later on in this method):  
		for(int i = 0; i < a.length; i++) {
			if(found)
				a[i-1] = a[i];
			if(a[i] == null)
				found = true; 
			if(i == a.length - 1 && found)
				// If the last element is not null, this 'if' condition is needed, otherwise as the non-null element is brought to the 
				// front of the array, it will change the 'null' values to it's value too, rather than replacing its position
				// with a null value. This fixes this: 
				a[i] = null;
		}
		// Step Two: Count how many 'null' values are in the array: 
		for (int i = 0; i < a.length; i++) { 
			if (a[i] == null) {
				counter++;
			}
		}
		// Step Three: create a new array, with the length of how many NON NULL values there are, 
		// (by subtracting the value of 'counter'): 
		Player[] newPlayerArray = new Player[a.length - counter];													
		for (int i = 0; i < newPlayerArray.length; i++) {
			for (int j = 0; j < newPlayerArray.length; j++) {								
				if (a[i] != null) {
					// if the value in the Player[] 'a' (the array passed to the method) is NOT null, 
					// add it to the new array: 
					// This works because the previous part of this method moved the non-null elements to the
					// front of the array. So the non-null elements can now be inserted into this new array. 
					// Now, this new array is the correct size and contains only non-null values: 
					newPlayerArray[j] = a[i];
					i++;
				}
			}
		}
		return newPlayerArray; // the new updated array (with no nulls) is returned! 
	} // This method works when called only once because Players are only removed one at a time:  
	// (There should only be one null at any time, which this method then removes).
		
}
	
