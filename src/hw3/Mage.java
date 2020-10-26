package hw3;
import java.util.LinkedList;
import java.util.Random;

public class Mage extends Player {
	public int spellPower;
	public int manaPool;
	public int currentMana;
	private int cost;
	private int hitTimes;
	private int range;

	public Mage(String name, int spellPower , int manaPool , int cost , int hitTimes , int range,int health, int attack, int defense, Pair pos) {
		super(name, health, attack, defense, pos);
		this.spellPower = spellPower;
		this.manaPool = manaPool;
		this.currentMana = this.manaPool / 4;
		this.cost = cost;
		this.hitTimes = hitTimes;
		this.range = range;
	}

	@Override
	public void specificUponLevel() {
		this.manaPool = this.manaPool + (25 * this.Level);
		this.currentMana = Math.min(this.currentMana + (this.manaPool / 4), this.manaPool);
		this.spellPower = this.spellPower + (10 * this.Level); 
		System.out.println("		+"+ Math.min(this.currentMana + (this.manaPool / 4), this.manaPool)+" maximum mana, +"+(10 * this.Level)+" spell power");
	}

	@Override
	public char[][] specialAbility(char[][]board) {
		if(this.currentMana < this.cost) {
			System.out.println("Error: cost is higher than current mana");
			return board;
		}
		else {
			this.currentMana = this.currentMana - this.cost;
			int hits = 0;
			Random rand = new Random();
			LinkedList<Enemies> enemyOptional = hasEnemy(board,this.range);
			System.out.println(this.Name+" cast Blizzard.");
			while(hits < this.hitTimes && enemyOptional.size() > 0) {
				board = AttackPlayerSpecialAbility(board,this, enemyOptional.get(rand.nextInt(enemyOptional.size())), spellPower );
				hits++;
			}
		}
		return board;
	}


	@Override
	public void onGameTick() {
		this.currentMana = Math.min(this.manaPool, this.currentMana + 1);
	}

	public String toPrint() {
		return Name +"		Health: "+Health[1]+"		Attack damage: "+AttackPoints+"		Defense: "+DefensePoints+
				"\n"+"		Level: "+Level+"		Experience: "+Experience+"/"+Level * 50+"		SpellPower: "+spellPower+"		Mana: "+currentMana+"/"+manaPool;
	}

}
