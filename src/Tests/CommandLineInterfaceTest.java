package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import hw3.*;

public class CommandLineInterfaceTest {

	@Test
	public void testGetPosition() {
		char [][] board = {{'#','#','#','#','#'},{'#','.','@','s','#'},{'#','.','.','.','#'},{'#','M','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(CommandLineInterface.getPosition(board).getFirst() == 1 &&
				CommandLineInterface.getPosition(board).getSeconed() == 2);
		assertTrue(!(CommandLineInterface.getPosition(board).getFirst() == 3 &&
				CommandLineInterface.getPosition(board).getSeconed() == 2));
		assertTrue(!(CommandLineInterface.getPosition(board).getFirst() == 1 &&
				CommandLineInterface.getPosition(board).getSeconed() == 4));
		
	}
	
	@Test
	public void testPlayerIsDead() {
		char [][] board = {{'#','#','#','#','#'},{'#','.','@','s','#'},{'#','.','@','.','#'},{'#','M','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(!CommandLineInterface.playerIsDead(board));
		char [][] board2 = {{'#','#','#','#','#'},{'#','.','X','.','#'},{'#','.','.','.','#'},{'#','.','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(CommandLineInterface.playerIsDead(board2));
		char [][] board3 = {{'#','#','#','#','#'},{'#','s','@','s','#'},{'#','.','.','.','#'},{'#','M','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(!CommandLineInterface.playerIsDead(board3));
		char [][] board4 = {{'#','#','#','#','#'},{'#','.','s','s','#'},{'#','X','s','M','#'},{'#','.','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(CommandLineInterface.playerIsDead(board4));
	}
	
	@Test
	public void testAllEnemiesDied() {
		char [][] board = {{'#','#','#','#','#'},{'#','#','#','#','#'}}; 
		assertTrue(CommandLineInterface.allEnemiesDied(board));
		SubjectUnits s = new SubjectUnits();
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		s.attach(new Monster("Lannister Knight", 'k', 200, 14, 8, new Pair(3,4), 4, 50));
		s.attach(new Monster("Bear-Wright", 'b', 1000, 75, 30, new Pair(2,2), 4, 250));
		assertTrue(!CommandLineInterface.allEnemiesDied(board));
		s.reset();
		assertTrue(CommandLineInterface.allEnemiesDied(board));
		
	}

}
