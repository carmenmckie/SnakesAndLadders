public class Play {

	public static void main(String[] args) {
		// "That will create a board": (this Board has 10 rows, 5 cols) 
		Board newPlayingBoard = new Board(10, 5);
		// "Add some snakes and ladders": 
		newPlayingBoard.getSquareObjects()[8][3].setDelta(-8); 
		newPlayingBoard.getSquareObjects()[5][4].setDelta(-3);
		newPlayingBoard.getSquareObjects()[4][1].setDelta(7);
		newPlayingBoard.getSquareObjects()[2][0].setDelta(9);
		// That will create some Players: 
		Player player1 = new Player('A');
		Player player2 = new Player('B');
		Player player3 = new Player('C');
		HumanPlayer humanPlayer1 = new HumanPlayer('H');
		// TEST: To check that newPlayingBoard should have a Player[] 'allPlayersPlayingGame' length of 0 (no Players have been added to the Board yet): 
		System.out.println("TEST: array length (before any Players are added to the Board): " + newPlayingBoard.getAllPlayersPlayingGameLength());
		// Add HumanPlayer(Stage 5) and Players(Stage 4) to the Board:
		newPlayingBoard.addPlayerToGame(humanPlayer1); 
		newPlayingBoard.addPlayerToGame(player1);
		newPlayingBoard.addPlayerToGame(player2);
		newPlayingBoard.addPlayerToGame(player3);
		// TEST: (allPlayersPlayingGame) should now have a length of 4 after 4 Players were added to the Board: 
		System.out.println("TEST - (seeing if Board's Player[] 'allPlayersPlayingGame' has increased appropriately: " + newPlayingBoard.getAllPlayersPlayingGameLength());
		//TESTS: Board's helper methods
		System.out.println("TEST: Row at position 35: " + newPlayingBoard.returnRow(35));
		System.out.println("TEST: Col at position 18: " + newPlayingBoard.returnCol(18));
		System.out.println("TEST: Position at row 8, col 4: " + newPlayingBoard.returnPositionOfRowCol(8, 4));
		System.out.println("TEST: Square at position 21: " + newPlayingBoard.returnSquareFromPosition(21));
		System.out.println("END OF TESTS IN Play.java");
		
		// Start the game (which also plays until completion): 
		newPlayingBoard.takeTurns(newPlayingBoard.getAllPlayersPlayingGame());

	}
}
