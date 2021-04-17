// Student Number: 2137259M

public class Board { 
	private int row;  // Rows
	private int col;  // Columns
	private Square[][] squareObjects; // "The board should have an attribute that is an array of Square objects." 
	private Player[] allPlayersPlayingGame = new Player[0]; // "A Board object should also store references to all players playing the game." 
		// (allPlayersPlayingGame Starts at length '0' and increases when a Player is added to the board)
		// (I'm assuming max 4 Players overall in terms of formatting the Board though) 
	// Extra attributes used: 
	private int totalPositionsOnBoard; // used in '.movePlayerOnBoard()' to determine if a Player wins by reaching final position (or beyond)
	private int playerCounter; // used in Board's '.addPlayerToBoardArray()' method to count how many Players have been added to the game / added to the Board.
	
	
	public Board(int row, int col) {
		// "The Board constructor should take integers representing the number of rows and columns." 
		this.row = row; 
		this.col = col; 
		// To set the array of squareObjects to the corresponding rows and cols: 
		squareObjects = new Square[this.row][this.col]; 
		// Set totalPositionsOnBoard to be rows*cols-1 (taking into account positions on the Board will start at 0) 
		// This will be used to find out if a Player has won the game in Player's '.movePlayerOnBoard()' 
		this.totalPositionsOnBoard = this.row * this.col - 1; 
		// Fill the squareObjects array with Square objects: 
		for (int i=0; i<row; i++) {
			for (int j=0; j<col; j++) {
				squareObjects[i][j] = new Square();
			}
		}
		// setBoard() sets the positions of the Board in a wrapping pattern:  
		setBoard();		
	}
		
		
	// Stage 2 Requirements: 
	// 1. "The board should have a toString method that returns a string representation of the board"
	public String toString() {
		String output = ""; 
			for (int i = this.row - 1; i >= 0; i--) {
				for (int j = 0; j < this.col; j++) {
					// I'm assuming a maximum of four Players per game 
					// With max four Players, a padding of 20 looks best: 
					output += String.format("%20s", this.squareObjects[i][j]); 
					// (making use of the toString method of the Square class with 'squareObjects' here)
				}
				// So that each time the inner (j) loop is finished, the next row prints on a new line
				output += "\n";
			}
		return output;
	}	

		
	// 2. "The Board class should have helper methods that return the ROW and COLUMN of a particular POSITION": 
	// 2.a Return ROW from Position: 
	public int returnRow(int a) {
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				if (squareObjects[i][j].getPosition() == a) {
					int rowFromPosition = i; 
					return rowFromPosition;		
				}
			}
		} return 0; // fix compiler issue 
	}
		
		
	// 2.b Return COL from Position. 
	public int returnCol(int a) {
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				if (squareObjects[i][j].getPosition() == a) {
					int colFromPosition = j; 
					return colFromPosition;		
				}
			}
		} return 0; // fix compiler issue 
	}
	
		
	// 3. "and the position corresponding to a particular row, column pair." 
	public int returnPositionOfRowCol(int row, int col) {
		return this.squareObjects[row][col].getPosition(); 
	}
		
		
	// 4. "The Board class should have a method for adding players" 
	// (this method is different from '.assignPlayer()' (which can assign a Player
	// to any Square) because this method specifically adds Players to the 
	// starting square only, with help from '.assignPlayer()'
	public void addPlayerToGame(Player name) {
		// "place them on the starting square." (The starting square is: [0][0])
		squareObjects[0][0].assignPlayer(name);
		// "Hold a reference to them for future use". 
		// Player references are held in the Board's attribute 'Player[] allPlayersPlayingGame'.
		// 'allPlayersPlayingGame' starts at length 0, and increases when a Player is added to the game.
		// '.addPlayerToBoardArray()' adds the Player to the Board's reference by 
		// increasing the Board's Player[] array appropriately then adding the Player to the array: 
		addPlayerToBoardArray(name);
	}
		
		
	// 5. "The Board class should have a method that returns a reference to the Square object at any integer position." 
	public Square returnSquareFromPosition(int a) {
		Square result = null; 
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				if (squareObjects[i][j].getPosition() == a) {
					result = squareObjects[i][j];				  	
				} 
			}
		}
		return result; // returns a Square 
	}
			
		
	// 6. "The rows of the board should wrap" 
	// This method wraps the rows when a Board is made (it is called from the constructor of Board):
	public void setBoard() {
		int pos = 0; 
		for(int i=0; i<this.row; i++) {
			if(i%2==0) {
				// if the row begins with an even number, print to the right  (j++)
				for(int j=0; j<this.col; j++) {
					this.squareObjects[i][j].setPosition(pos);
					pos++;
				}
			} else {
				// if the row begins with an odd number, print to the left  (j--) 
				for (int j = this.col-1; j>=0; j--) {
					this.squareObjects[i][j].setPosition(pos); 
					pos++;
				}
			}		
		}
	}		
		
		
	// 'Stage 3: Requirement' - "Add a method to the Board (takeTurns)"
	public boolean takeTurns(Player[]a) {
		// Used to display the round: 
		int counter = 1; 
		System.out.println("\n" + "START OF GAME: " + "\n");
		// To print the starting (empty) board: 
		System.out.println(this);
		boolean playing = true; 
		while(playing) {
			// "that loops through the players"
			for(int i=0; i<a.length; i++) {
				//" and calls each of their move methods."
				if(a[i].move(this) == true) {
					// "If a move method returns true, that player has won and takeTurns should immediately terminate, returning true. 
					System.out.println("\n" + "Player " + a[i] + " has won! Congratulations :) " + "\n");
					// to "terminate" and break the loop, change the boolean to false to stop the 'while(playing)' loop: 
					playing = false;
					// return true: 
					System.out.println("TEST: boolean = true"); // To test that true should be returned after this. 
					return true;  
				}
				// To print the board each time a move is made
				System.out.println(this);
			}
			// To display the rounds to make output tidy: 
			System.out.println("END OF ROUND: " + counter++);
			System.out.println("TEST: boolean = false"); // To test that false should be returned after this.
		}
		// "otherwise it should return false" (if a Player doesn't win) 
		return false; 
	}
		
		
	// Extra Methods added to help run program: 
		
		
	// Access the references to Players playing the game: 
	// Used in Play.java to test if the addPlayerToBoardArray() method works:
	public int getAllPlayersPlayingGameLength() {
		return this.allPlayersPlayingGame.length; 
	}
		
		
	// Called from '.addPlayerToGame()' 
	public Player[] addPlayerToBoardArray(Player a) {
		// A new Board object starts with an array of 0 Players. If a Player is added to the Board, this array needs to increase.
		// 1. Get the length/number of Players currently in the game, so we can increase this by 1 because a Player is being added:
		int currentLength = allPlayersPlayingGame.length;
		// 2. Increase the length by one (we only add one Player at a time)
		int newLength = currentLength + 1; 
		// 3. Make a new array with this increased length: 
		Player[] newArray = new Player[newLength];		
		// 4. Drop any existing values into the new array:
		for(int i = 0; i < currentLength; i++) {
			newArray[i] = allPlayersPlayingGame[i];
		}
		// 5. reset reference:
		allPlayersPlayingGame = newArray;
		// 6. Add Player 'a' to the new (longer) array we have created: 
		allPlayersPlayingGame[playerCounter++] = a;
		// playerCounter is increased to help repeat the process: if another Player is added to the array - they will be inserted in the next slot up. 
		// return the new array which now has the Player that was passed through the argument of '.addPlayerToGame()' 
		return allPlayersPlayingGame;
	}
			
		
	// Access the Player[] reference of all Players playing the game: - used in Play.java to call TakeTurns on all the Players:
	public Player[] getAllPlayersPlayingGame() {
		return this.allPlayersPlayingGame; 
	}
		
		
	// Access the total number of positions on the Board
	// Used in Player's '.movePlayerOnBoard()' to determine if a Player has won (by reaching the final position or beyond):
	public int getTotalPositionsOnBoard() {
		return this.totalPositionsOnBoard;
	}
		
		
	// Access a Board's 'squareObjects' attribute
	// (used in 'Play.java' to set a delta on the chosen Square):
	public Square[][] getSquareObjects() { 
		return this.squareObjects;
	}
			
				
	// Stage 2 Requirements: 
	public static void main(String[] args) {
		// 1. "In the Board class, include a main method that creates a board with 10 rows and 5 columns. " 
		Board newBoard = new Board(10, 5); 		
		// 2. "The main method should also create two players"
		Player playerA = new Player('A');
		Player playerB = new Player('B');
		// 3. "add them to the Board" 
		newBoard.addPlayerToGame(playerA); 
		newBoard.addPlayerToGame(playerB); 
		// 4. "and print the board. "
		System.out.println(newBoard);	
	}
					
}	

	