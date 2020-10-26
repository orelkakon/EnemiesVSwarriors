package hw3;

public abstract class GameUnit {
	public String Name;
	public int[] Health; // [0] - Health pool , [1] - Current health
	public int AttackPoints;
	public int DefensePoints;		
	public Pair Position;
	public GameUnit(String name,int health, int attack, int defense,Pair pos) {
		this.Name = name;
		this.Health = new int[2];
		this.Health[0] = health;
		this.Health[1] = this.Health[0];
		this.AttackPoints = attack;
		this.DefensePoints = defense;
		this.Position = new Pair(pos.getFirst(), pos.getSeconed());

	}
	
	protected boolean checkRange(Pair enemy,Pair player,int maxRange) {
		double distance = Math.sqrt (Math.pow(enemy.getFirst() - player.getFirst(), 2) + Math.pow(enemy.getSeconed() - player.getSeconed(), 2));
		if(distance < maxRange)
			return true;
		return false;
	}
	
	public char[][] makeMove(char[][]board,String action,GameUnit player) {
		char[][] newBoard = board;
		switch(action) {
			case "d":  //right
				if(checkIfFree(newBoard, this.Position.getFirst(), this.Position.getSeconed() + 1)) {
					newBoard[this.Position.getFirst()][this.Position.getSeconed() + 1] = '@';
					newBoard[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setSeconed(this.Position.getSeconed() + 1);
				}
				break;	
			case "a":  //left
				if(checkIfFree(newBoard, this.Position.getFirst(), this.Position.getSeconed() - 1)) {
					newBoard[this.Position.getFirst()][this.Position.getSeconed() - 1] = '@';
					newBoard[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setSeconed(this.Position.getSeconed() - 1);
				}
				break;
			case "s":  //down
				if(checkIfFree(newBoard, this.Position.getFirst() + 1, this.Position.getSeconed())) {
					newBoard[this.Position.getFirst() + 1][this.Position.getSeconed()] = '@';
					newBoard[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setFirst(this.Position.getFirst() + 1);
				}
				break;
			case "w":  //up
				if(checkIfFree(newBoard, this.Position.getFirst() - 1, this.Position.getSeconed())) {
					newBoard[this.Position.getFirst() - 1][this.Position.getSeconed()] = '@';
					newBoard[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setFirst(this.Position.getFirst() - 1);
				}
				break;
			case "e":  //special ability
				if(player instanceof Player) {
					newBoard = ((Player)player).specialAbility(board);
				}
				break;
			case "q":  //nothing
				break;
		}		
		return newBoard;
	} 
	public char[][] makeMoveEnemy(char[][]board,String action,GameUnit player) {
		switch(action) {
			case "d":  //right
				if(checkIfFree(board, this.Position.getFirst(), this.Position.getSeconed() + 1)) {
					board[this.Position.getFirst()][this.Position.getSeconed() + 1] = ((Enemies)this).Title;
					board[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setSeconed(this.Position.getSeconed() + 1);
				}
				else if(checkIfItPlayer(board, this.Position.getFirst(), this.Position.getSeconed() + 1)) {
					board = AttackPlayer(board,this,player);
				}
				break;	
			case "a":  //left
				if(checkIfFree(board, this.Position.getFirst(), this.Position.getSeconed() - 1)) {
					board[this.Position.getFirst()][this.Position.getSeconed() - 1] = ((Enemies)this).Title;
					board[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setSeconed(this.Position.getSeconed() - 1);				
				}
				else if(checkIfItPlayer(board, this.Position.getFirst(), this.Position.getSeconed() - 1)) {
					board = AttackPlayer(board,this,player);
				}
				break;
			case "s":  //down
				if(checkIfFree(board, this.Position.getFirst() + 1, this.Position.getSeconed())) {
					board[this.Position.getFirst() + 1][this.Position.getSeconed()] = ((Enemies)this).Title;
					board[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setFirst(this.Position.getFirst() + 1);
				}		
				else if(checkIfItPlayer(board, this.Position.getFirst() + 1, this.Position.getSeconed())) {
					board = AttackPlayer(board,this,player);
				}
				break;
			case "w":  //up
				if(checkIfFree(board, this.Position.getFirst() - 1, this.Position.getSeconed())) {
					board[this.Position.getFirst() - 1][this.Position.getSeconed()] = ((Enemies)this).Title;
					board[this.Position.getFirst()][this.Position.getSeconed()] = '.';
					this.Position.setFirst(this.Position.getFirst() - 1);
				}
				else if(checkIfItPlayer(board, this.Position.getFirst() - 1, this.Position.getSeconed())) {
					board = AttackPlayer(board,this,player);
				}
				break;
		}
		return board;
	} 
	public char[][] AttackPlayer(char[][] board, GameUnit enemy, GameUnit player) {
		System.out.println(enemy.Name +" engaged in battle with "+ player.Name);
		System.out.println(((Enemies)enemy).toPrint());
		System.out.println(((Player)player).toPrint());
		int enemyRoll = CommandLineInterface.GRA.nextInt(this.AttackPoints);
		int playerRoll = CommandLineInterface.GRA.nextInt(this.DefensePoints);
		System.out.println(enemy.Name+" rolled "+enemyRoll+" attack points.");
		System.out.println(player.Name+" rolled "+playerRoll+" defense points.");
		System.out.println(enemy.Name+" hit "+player.Name+" for "+ Math.max(0,(enemyRoll - playerRoll)) +" damage.");
		if(enemyRoll > playerRoll) {
			player.Health[1] = player.Health[1] - (enemyRoll - playerRoll);
			if(player.Health[1] <= 0) {
				board[player.Position.getFirst()][player.Position.getSeconed()] = 'X';
				System.out.println(player.Name+" died.");
			}
		}
		return board;
	}
	
	public char[][] AttackPlayerSpecialAbility(char[][] board,Player player, Enemies enemy,int damage) {
		int enemyRoll = CommandLineInterface.GRA.nextInt(enemy.DefensePoints);
		System.out.println(enemy.Name+" rolled "+enemyRoll+" defense points.");
		if(damage > enemyRoll) {
			enemy.Health[1] = enemy.Health[1] - (damage - enemyRoll);
			System.out.println(player.Name+" hit "+enemy.Name+" for "+(damage - enemyRoll)+" ability damage.");
			if(enemy.Health[1] <= 0) { //dead enemy
				player.Experience = player.Experience + enemy.Experience;
				while(player.Experience >= 50 * player.Level) {
					player.uponLevel();
				}
				board[enemy.Position.getFirst()][ enemy.Position.getSeconed()] = '.';
				System.out.println(enemy.Name+" died. "+player.Name+" gained "+enemy.Experience+" experience!");
				SubjectUnits.units.remove(enemy);
			}
		}
		else {
			System.out.println(this.Name+" hit "+enemy.Name+" for "+0+" ability damage.");
		}
		return board;
	}


	private boolean checkIfFree(char[][]board,int x,int y) { 
		try {
			if(board[x][y] == '.' && SubjectUnits.getUnit(x, y) == null)
				return true;
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	private boolean checkIfItPlayer(char[][]board,int x,int y) {
		if(board[x][y] == '@')
			return true;
		return false;
	}
}
