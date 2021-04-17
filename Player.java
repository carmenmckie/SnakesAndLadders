// Student Number: 2137259M

import java.util.Random; 
import java.util.Arrays; // Used in a test in '.movePlayersOnBoard()' to print all Players at a Square, to check only the correct Players are there.

public class Player {
	private char identity; // "attribute char that allows Players to be identified"
	private Square referenceToSquare; // "hold a reference to the Square in which they are currently located" 
	
	
	// Player constructor - sets the identifying char of each Player:
	public Player(char identity) {
		this.identity = identity; 
	}
	
	
	// Stage 1 Requirement: 
	// "the toString method of the Player class just returns this (attribute char) as a String" 
	public String toString() {
		String line = "";
		line += identity; 
		return line; 
	}
	
	
	// Stage 3 Requirements: 
	// Decided to split '.move()' into two methods to avoid repetition of code in HumanPlayer 
	// now '.move()' calls '.movePlayerOnBoard()'
	public boolean move(Board a) {
		Random r = new Random(); 
		int count = r.nextInt(6)+1; 
		if (movePlayerOnBoard(count, a) == true) {
			return true; // If a Player wins, return true  
		} else {
			return false; // If a Player hasn't won / hasn't won yet, return false 
		}
	}
			
	
	// Called by '.move()' of Player and HumanPlayer: 
	public boolean movePlayerOnBoard(int count, Board a) {
		// Create a Square reference that refers to the Square the Player is currently on: 
		Square squarePlayerIsOn = this.getReferenceToSquare();												
		// Access the total number of positions on the Board we are currently playing on to determine when someone wins: 							
		int totalPositionsOfThisBoard = a.getTotalPositionsOnBoard();
		// Get the position the Player is currently on (will be 0 at the start of the game, for example): 
		int originalPosition = this.getReferenceToSquarePosition();
		// Set a new position value to include the value from the dice/user input: this is the position on the board where the Player will be moving to:					
		int newPosition = originalPosition + count; 
		// initialise this variable we will be using if a delta changes the position: 
		int newPositionWithDelta = 0; 
		// To help debug / test: 
		System.out.println("TEST: " + this + " : " + count + " has been rolled, taking " + this + " from " + originalPosition + " to: " + newPosition);
		// Check whether the Position the Player will be moving to will result in them winning: (if the position is greater than or equal to the highest position on the Board): 
		if(newPosition >= totalPositionsOfThisBoard) {
			// We do not need to check for any deltas on the final position of the Board, because there can never be a delta on the final position in Snakes and Ladders
			System.out.println("The total number of rows and columns is: " + totalPositionsOfThisBoard + " ... Player " + this + "' is at "+  newPosition + " so Player " + this + " is the Player to get to the final square (or beyond it) first ..." + "\n");
			// Remove the Player from the Position they were on before the dice/user input rolled: 
			// (.removePlayer() also removes the Player from the relevant Square's Player[] 'playerReferences' too): 
			squarePlayerIsOn.removePlayer(this, squarePlayerIsOn.getPlayerReference());
			// Add the Player to the final position of the Board: 
			// If the Player reaches a value greater than the final position, this will just add them to the final position too.
			// Just like normal snakes and ladders - so it's visualised that the Player won the game (even though technically they may have fell off the Board if they reached 50+, etc) 
			a.returnSquareFromPosition(totalPositionsOfThisBoard).assignPlayer(this);
			// So that the Board prints, visualising the winning Player on the last Square: 
			System.out.println(a);
			// "If a move method returns true, that player has won and takeTurns should immediately terminate": (termination happens in takeTurns() which calls move()): 
			return true; 
		// If the Player didn't win, check for deltas: 
		} if(a.returnSquareFromPosition(newPosition).getDelta() == 0) {
			// Nothing is changed (Delta = 0) 
			newPositionWithDelta = newPosition; 
			// remove the Player from their original position (before dice / user input): 
			squarePlayerIsOn.removePlayer(this, squarePlayerIsOn.getPlayerReference());
			// add the Player to the new position: 
			a.returnSquareFromPosition(newPositionWithDelta).assignPlayer(this);					
		// If the Player's new position contains a delta:		
		} if(a.returnSquareFromPosition(newPosition).getDelta() != 0) {
			// Get the final new position by adding the delta value to the newPosition: 
			newPositionWithDelta = newPosition + a.returnSquareFromPosition(newPosition).getDelta();
			// Remove the Player from the Square they were on: 
			squarePlayerIsOn.removePlayer(this, squarePlayerIsOn.getPlayerReference());
			// Add the Player to the Square which has the relevant updated position (position including the delta value): 
			a.returnSquareFromPosition(newPositionWithDelta).assignPlayer(this);	
			// Print the relevant output to the user: 
			if(a.returnSquareFromPosition(newPosition).getDelta() > 0) {
				System.out.println("Oh! " + this + " has landed on a ladder! They jump from " + newPosition + " to: " + newPositionWithDelta);
			} else {
				System.out.println("Oh! " + this + " has landed on a SNAKE! They fall from " + newPosition + " to: " + newPositionWithDelta);
			}
			System.out.println("TEST: " + this + " is now at the position: " + this.getReferenceToSquarePosition() + "\n");				
		}				
		// TEST: To test the delta worked and that the Square contains the correct Player(s) in its 'playerReferences' attribute: 
		System.out.println("TEST: Square " + "(" + newPositionWithDelta + ") " + Arrays.toString(a.returnSquareFromPosition(newPositionWithDelta).getPlayerReference()) + "\n");
		// "Otherwise, it should return false" (if the Player doesn't win) 
		return false; 
	}
	
	
	// Extra methods added: 
	
	
	// Getter: get the identity of the Player (used in Square's '.removePlayer()' method: 
	public char getIdentity() {
		return this.identity; 
	}
		
	
	// Getter: get the Player's reference to the Square they're on (used in '.movePlayerOnBoard()' method): 
	public Square getReferenceToSquare() {
		return this.referenceToSquare;
	} 
		
		
	// Getter: get the Player's position of the Square they're on (also used in '.movePlayerOnBoard()' method): 
	public int getReferenceToSquarePosition() {
		return this.referenceToSquare.getPosition();
	}	
		
	
	// Setter: set the Player's 'Square attribute: referenceToSquare' (used in Square's .assignPlayer() method):
	public void setReferenceToSquare(Square a) {
		this.referenceToSquare = a; 
	}	
	

	// Stage 1 Requirements: 
	public static void main(String[] args) {
		// "In your Player class, include a main method that demonstrates the creation of a Player":
		Player playerOne = new Player('a');
		// "the creation of a Square": (default Square constructor used so default values: position = 0, delta = 0, and Player[] 
		// reference = null because no Player has been assigned to the Square yet)
		Square squareOne = new Square(); 
		// "the assignment of the Player to the Square": 
		// '.assignPlayer()' method: will update the Player's 'referenceToSquare'
		// and will also update the Square's 'Player[] playerReferences': 
		squareOne.assignPlayer(playerOne);
		// "and the toString methods of both the player and the square.":
		System.out.println(playerOne); 
		System.out.println(squareOne); 	
	}
	
}