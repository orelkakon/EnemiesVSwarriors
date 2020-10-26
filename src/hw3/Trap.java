package hw3;
import java.util.LinkedList;
import java.util.Random;

public class Trap extends Enemies{

	private int relocationRange;
	private int relocationTime;
	private int visibilityTime;
	private int ticksCount;

	public Trap(String name,char title, int health, int attack, int defense, Pair pos, int range, int reTime , int visibilty, int XP) {
		super(name, title, health, attack, defense, pos, XP);
		this.relocationRange = range;
		this.relocationTime = reTime;
		this.visibilityTime = visibilty;
		this.ticksCount = 0;
	}

	@Override
	public char[][] onGameTick(char [][] board, Enemies me,Player player) {
		if(this.ticksCount == this.relocationTime) {
			this.ticksCount = 0;
			Random x = new Random();
			LinkedList<Pair> freePos = getFreePosition(board, relocationRange, me);
			if(freePos.size() > 0) {
				int rollPos = x.nextInt(freePos.size());
				board[freePos.get(rollPos).getFirst()][freePos.get(rollPos).getSeconed()] = me.Title;
				board[me.Position.getFirst()][me.Position.getSeconed()] = '.'; // delete the old place;
				me.Position.setFirst(freePos.get(rollPos).getFirst());
				me.Position.setSeconed(freePos.get(rollPos).getSeconed());
			}
		}
		else {
			this.ticksCount++;
			if(checkRange(me.Position,getPlayerPos(board), 2)) {
				board = AttackPlayer(board, me, player);
			}
		}
		if(this.ticksCount < this.visibilityTime) {
			board[me.Position.getFirst()][me.Position.getSeconed()] = me.Title; //visible
		}
		else {
			board[me.Position.getFirst()][me.Position.getSeconed()] = '.'; //invisible
		}
		return board;
	}
	private LinkedList<Pair> getFreePosition(char[][]board,int range,Enemies me){
		LinkedList<Pair> toRet = new LinkedList<Pair>();
		for(int i = me.Position.getFirst() - range ; i < me.Position.getFirst() + range ; i++) {
			for(int j = me.Position.getSeconed() - range ; j < me.Position.getSeconed() + range ; j++) {
				try {
					if(board[i][j] == '.')
						toRet.add(new Pair(i,j));
				} catch(Exception e) {

				}
			}
		}
		return toRet;
	}

	@Override
	public String toPrint() {
		return this.Name+"		Health: "+this.Health[1]+"		Attack damage: "+this.AttackPoints+"		Defense: "+this.DefensePoints;
	}
}
