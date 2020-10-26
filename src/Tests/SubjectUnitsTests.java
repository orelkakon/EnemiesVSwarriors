package Tests;
import static org.junit.Assert.*;
import hw3.*;
import org.junit.Test;

public class SubjectUnitsTests {

	SubjectUnits s = new SubjectUnits();
	@SuppressWarnings("static-access")
	@Test
	public void testAttach() {
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		assertFalse(s.emptyList());
		assertTrue(s.units.size() == 3);
		s.reset();
		assertTrue(s.emptyList());
		s.reset();
	}
	
	@Test
	public void testCheckCombat() {
		char [][] board = {{'#','#','#','#','#'},{'#','.','s','s','#'},{'#','.','k','.','#'},{'#','M','.','.','#'},{'#','#','#','#','#'}}; 
		assertTrue(s.checkCombat(board, 1,2 ));
		assertTrue(s.checkCombat(board, 1,3 ));
		assertTrue(s.checkCombat(board, 2,2 ));
		assertTrue(s.checkCombat(board, 3,1 ));
		assertFalse(s.checkCombat(board, 0,2 ));
		assertFalse(s.checkCombat(board, 4,2 ));
		assertFalse(s.checkCombat(board, 1,4 ));
		assertFalse(s.checkCombat(board, 0,0 ));
	}
	@Test
	public void testEmptyList() {
		assertTrue(s.emptyList());
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		assertFalse(s.emptyList());
		s.reset();
	}

	@Test
	public void testGetUnitIndex() {
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		s.attach(new Monster("Lannister Knight", 'k', 200, 14, 8, new Pair(3,4), 4, 50));
		s.attach(new Monster("Bear-Wright", 'b', 1000, 75, 30, new Pair(2,2), 4, 250));
		assertFalse(s.emptyList());
		assertTrue(s.getUnitIndex(1, 2) == 0);
		assertTrue(s.getUnitIndex(3, 4) == 1);
		assertTrue(s.getUnitIndex(1, 2) != 2);

		s.reset();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testGetUnit() {
		Monster obj = new Monster("Lannister Knight", 'k', 200, 14, 8, new Pair(3,4), 4, 50);
		s.attach(obj);
		Monster obj2 = new Monster("Bear-Wright", 'b', 1000, 75, 30, new Pair(2,2), 4, 250);
		s.attach(obj2);
		s.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(1,2), 3, 25));
		assertFalse(s.emptyList());
		assertTrue(s.getUnit(3, 4).equals(obj));
		assertTrue(s.getUnit(2, 2).equals(obj2));
		assertFalse(s.getUnit(1, 2).equals(obj));
		s.reset();
	}
}
