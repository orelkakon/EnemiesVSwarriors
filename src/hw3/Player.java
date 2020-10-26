package hw3;
import java.util.LinkedList;

public abstract class Player extends GameUnit {
	public int Experience;
	public int Level;
	
	public Player(String name, int health,int attack, int defense, Pair pos){
		super(name,health, attack, defense, pos);
		this.Experience = 0;
		this.Level = 1;
	}
	public void uponLevel() {
		this.Experience = this.Experience - (50 * this.Level);
		this.Level = this.Level + 1;
		this.Health[0] = this.Health[0] + (10 * this.Level);
		this.Health[1] = this.Health[0];
		this.AttackPoints = this.AttackPoints + (5 * this.Level);
		this.DefensePoints = this.DefensePoints + (2 * this.Level);
		System.out.println("Level up: +"+10 * this.Level+" Health, +"+5 * this.Level+" Attack, +"+2 * this.Level+" Defense ");
		specificUponLevel();
	}
	abstract void specificUponLevel();
	abstract void onGameTick();
	abstract char[][] specialAbility(char[][] board);
	abstract String toPrint();
	protected LinkedList<Enemies> hasEnemy(char[][] board,int range) {
		LinkedList<Enemies> toRet = new LinkedList<>();
		for(int i = this.Position.getFirst() - range ; i < this.Position.getFirst() + range ;i++) {
			for(int j = this.Position.getSeconed() - range ; j < this.Position.getSeconed() + range ;j++) {
				try {
					if(board[i][j] != '@' && board[i][j] != '.' && board[i][j] != '#') {
						toRet.add(SubjectUnits.getUnit(i, j));
					}
				}
				catch(Exception e) {
					
				} 
			}
		}
		return toRet;
	}
}
