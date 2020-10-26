package hw3;
public class Monster extends Enemies{
	private int visionRange;
	public Monster(String name,char title,int health, int attack, int defense,Pair pos,int vision,int XP) {
		super(name,title,health, attack, defense, pos,XP);
		this.visionRange = vision;
	}

	public char[][] onGameTick(char[][]board, Enemies me, Player player) {
		if(checkRange(me.Position, player.Position, visionRange)) {
			int dy = me.Position.getFirst() - player.Position.getFirst();
			int dx = me.Position.getSeconed() - player.Position.getSeconed();
			if(Math.abs(dx) > Math.abs(dy)) {
				if(dx > 0) {
					return makeMoveEnemy(board, "a", player);
				}
				else {
					return makeMoveEnemy(board, "d", player);
				}
			}
			else {
				if(dy > 0) {
					return makeMoveEnemy(board, "w", player);
				}
				else {
					return makeMoveEnemy(board, "s", player);
				}
			}
		}
		else {
			int action = CommandLineInterface.GRA.nextInt(5);
			switch (action) { 
			case 0 : //up
				return makeMoveEnemy(board, "w", player);
			case 1: //down
				return makeMoveEnemy(board, "s", player);
			case 2 : //right
				return makeMoveEnemy(board, "d", player);
			case 3: //left
				return makeMoveEnemy(board, "a", player);
			default:
				break;
			}
		}
		return board;
	}

	@Override
	public String toPrint() {
		return this.Name+"		Health: "+this.Health[1]+"		Attack damage: "+this.AttackPoints+"		Defense: "+this.DefensePoints;
	}
}
