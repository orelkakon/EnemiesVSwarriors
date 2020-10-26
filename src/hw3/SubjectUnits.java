package hw3;
import java.util.LinkedList;
import java.util.List;

public class SubjectUnits {

	public static List<Enemies> units = new LinkedList<Enemies>();

	public void attach(Enemies unit) {
		units.add(unit);
	}

	public char[][] notifyAllUnit(char[][]board,Player player) {
		for(Enemies unit : units) {
			board = unit.onGameTick(board,unit,player);
		}
		return board;
	}
	
	public void reset() {
		units.clear();
	}

	public int getUnitIndex(int x, int y) {
		for (int i = 0; i < units.size() ; i++) {
			if(units.get(i).Position.getFirst() == x && units.get(i).Position.getSeconed() == y)
				return i;
		}
		return -1;
	}

	public static Enemies getUnit(int x, int y) {
		for (int i = 0; i < units.size() ; i++) {
			if(units.get(i).Position.getFirst() == x && units.get(i).Position.getSeconed() == y)
				return units.get(i);
		}
		return null;
	}
	
	public char [][] getCombat(Player player , Pair enemyPos, char[][] board) {
		int enemy = getUnitIndex(enemyPos.getFirst(), enemyPos.getSeconed());
		System.out.println(player.Name+" engaged in battle with "+units.get(enemy).Name+":");
		System.out.println(player.toPrint());
		System.out.println(units.get(enemy).toPrint());
		int attackRoll = CommandLineInterface.GRA.nextInt(player.AttackPoints);
		System.out.println(player.Name +" rolled "+attackRoll+" attack points");
		int defenseRoll = CommandLineInterface.GRA.nextInt(units.get(enemy).DefensePoints);
		System.out.println(units.get(enemy).Name +" rolled "+defenseRoll+" defense points");
		if(attackRoll - defenseRoll > 0) {
			units.get(enemy).Health[1] = units.get(enemy).Health[1] - (attackRoll - defenseRoll);
			System.out.println(player.Name+" hit "+units.get(enemy).Name+" for "+(attackRoll - defenseRoll)+" damage.");
			if(units.get(enemy).Health[1] <= 0) { //dead enemy
				player.Experience = player.Experience + ((Enemies)units.get(enemy)).Experience;
				while(player.Experience >= 50 * player.Level) {
					player.uponLevel();
				}
				board[enemyPos.getFirst()][ enemyPos.getSeconed()] = '.';
				System.out.println(units.get(enemy).Name+" died. "+player.Name+" gained "+((Enemies)units.get(enemy)).Experience+" experience!");
				units.remove(enemy);
			}
		}
		else {
			System.out.println(player.Name+" hit "+units.get(enemy).Name+" for "+0+" damage.");

		}
		return board;
	}

	public boolean checkCombat(char[][]board,int x,int y) {
		try {
			if(board[x][y] != '#' && board[x][y] != '.' && board[x][y] != '@')
				return true;
			else if(getUnit(x, y) != null) {
				return true;
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}

	public boolean emptyList() {
		return units.size() == 0;
	}
}
