package hw3;

public class Warrior extends Player{
	public int cooldown;
	public int remaining;
	public Warrior(String name, int cooldown, int health, int attack, int defense, Pair pos) {
		super(name, health, attack, defense, pos);
		this.cooldown = cooldown;
		this.remaining = 0;
	}


	@Override
	public void onGameTick() {
		if(this.remaining > 0)
			this.remaining = this.remaining - 1;
	}

	@Override
	public char[][] specialAbility(char[][]board) {
		if(this.remaining > 0) {
			System.out.println("Error: has a cooldown between actions");
			return board;
		}
		else {
			this.remaining = this.cooldown;
			int num = Math.min(this.Health[0], this.Health[1] + (2 * this.DefensePoints));
			System.out.println(this.Name+" cast Heal, healing for "+(num - this.Health[1]));
			this.Health[1] = num;
		}
		return board;
	}
	@Override
	public void specificUponLevel() {
		this.remaining = Integer.valueOf(0);
		this.Health[0] = this.Health[0] + (5 * this.Level);
		this.DefensePoints = this.DefensePoints + (1 * this.Level);
	}


	@Override
	public String toPrint() {
		return Name +"		Health: "+Health[1]+"		Attack damage: "+AttackPoints+"		Defense: "+DefensePoints+
				"\n"+"		Level: "+Level+"		Experience: "+Experience+"/"+50 * Level+"		Ability cooldown: "+cooldown+"		Remaining: "+remaining;

	}
}
