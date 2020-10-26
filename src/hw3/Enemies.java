package hw3;
public abstract class Enemies extends GameUnit{
	
	public int Experience;
	protected char Title;
	
	public Enemies(String name,char title,int health, int attack, int defense,Pair pos,int XP) {
		super(name,health, attack, defense, pos);
		this.Title = title;
		this.Experience = XP;
	}
	
	public abstract char[][] onGameTick(char[][] board,Enemies me,Player player);
	
	public Pair getPlayerPos(char[][] board) {
		for(int i = 0; i < board.length; i++ ) {
			for(int j = 0; j < board[i].length; j++ ) {
				if(board[i][j] == '@')
					return new Pair(i,j);
			}
		}
		return null;
	}

	
	public abstract String toPrint();
}
