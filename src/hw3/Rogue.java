package hw3;
import java.util.LinkedList;

public class Rogue extends Player{
	public int cost;
	public int currentEnergy;
	
	public Rogue(String name, int cost,int health, int attack, int defense, Pair pos) {
		super(name, health, attack, defense, pos);
		this.cost = cost;
		this.currentEnergy = 100;
	}
	@Override
	public void specificUponLevel() {
		this.currentEnergy = 100;
		this.AttackPoints = this.AttackPoints + (3 * this.Level);
	}

	@Override
	public char[][] specialAbility(char[][]board) {
		if(this.currentEnergy < this.cost) {
			System.out.println("Error: cost is higher than current Energy");
		       return board;
		}
		else {
			this.currentEnergy = this.currentEnergy - this.cost;
			System.out.println(this.Name+" cast Fan of Knives.");
			LinkedList<Enemies> enemyOptional = hasEnemy(board,2);
			int num = 0;
			while(num < enemyOptional.size()) {
				board = AttackPlayerSpecialAbility(board,this,enemyOptional.get(num),this.AttackPoints);
				num++;
			}
		}
		return board;
	}
	@Override
	public void onGameTick() {
		this.currentEnergy = Math.min(this.currentEnergy + 10, 100);
	}
	
	public String toPrint() {
		return Name +"		Health: "+Health[1]+"		Attack damage: "+AttackPoints+"		Defense: "+DefensePoints+
		"\n"+"		Level: "+Level+"		Experience: "+Experience+"/"+Level * 50+"		Energy: "+currentEnergy+"/100";
	}

}
