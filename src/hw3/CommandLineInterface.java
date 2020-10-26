package hw3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class CommandLineInterface {
	private static boolean toStop = false;
	private static SubjectUnits allUnits = new SubjectUnits();
	private static boolean debugMode = false;
	public static GenerateRandomActions GRA; 
	public static Player player = null;

	public static void main(String[]args) {
		Scanner input = new Scanner(System.in);
		int level = 1;
		char[][] GameBoardNow;
		GameBoardNow = loadLevel(args[0],level);
		try{
			if(args[1].equals("-D"))
				debugMode = true;
		} catch(Exception e){
			
		}
		GRA = new GenerateRandomActions(debugMode,args[0]);
		// start printing
		System.out.println("Select player: ");
		System.out.println("1.Jon Snow		Health: 300		Attack damage: 30		Defense: 4");
		System.out.println("		Level: 1		Experience: 0/50		Ability cooldown: 6		Remaining: 0");
		System.out.println("2. The Hound		Health: 400		Attack damage: 20		Defense: 6");
		System.out.println("		Level: 1		Experience: 0/50		Ability cooldown: 4		Remaining: 0");
		System.out.println("3. Melisandre		Health: 160		Attack damage: 10		Defense: 1");
		System.out.println("		Level: 1		Experience: 0/50		SpellPower: 15		Mana: 75/300");
		System.out.println("4. Thoros of Myr		Health: 250		Attack damage: 25		Defense: 3");
		System.out.println("		Level: 1		Experience: 0/50		SpellPower: 20		Mana: 37/150");
		System.out.println("5. Arya Stark		Health: 150		Attack damage: 40		Defense: 2");
		System.out.println("		Level: 1		Experience: 0/50		Energy: 100/100");
		System.out.println("6. Bronn		Health: 250		Attack damage: 35		Defense: 3");
		System.out.println("		Level: 1		Experience: 0/50		Energy: 100/100");

		int choice = -1;
		try {
			choice = Integer.parseInt(GRA.nextAction());
			if(choice<1 || choice > 6) {
				input.close();
				throw new Exception();
			}
			System.out.println("You have selected:");

		} catch(Exception e) {
			System.out.println("Illegal input - run program again");
			System.exit(0);
		}
		Pair position = getPosition(GameBoardNow);
		switch(choice) {
		case 1:
			player = new Warrior("Jon Snow",6,300,30,4,position);
			break;
		case 2:
			player = new Warrior("The Hound",4,400,20,6,position);
			break;
		case 3:
			player = new Mage("Melisandre",40,300,30,5,6,160,10,1,position);
			break;
		case 4:
			player = new Mage("Thoros of Myr",15,150,50,3,3,250,25,3,position);
			break;
		case 5:
			player = new Rogue("Arya Stark",20,150,40,2,position);
			break;
		case 6:
			player = new Rogue("Bronn",60,250,35,3,position);
			break;
		}
		System.out.println(player.toPrint());
		updateObserverEnemies(GameBoardNow);

		while(!toStop) {
			System.out.println("Use w/s/a/d to move.");
			System.out.println("Use e for special ability or q to pass.");
			printBoard(GameBoardNow);
			System.out.println("");
			System.out.println("");
			System.out.println(player.toPrint());
			String action = GRA.nextAction();
			if( action.equals("w") || action.equals("s") || action.equals("a") || action.equals("d") || action.equals("e") || action.equals("q")) {
				if(action.equals("w") && allUnits.checkCombat(GameBoardNow,player.Position.getFirst() - 1, player.Position.getSeconed())){
					GameBoardNow = allUnits.getCombat(player, new Pair(player.Position.getFirst() - 1, player.Position.getSeconed()), GameBoardNow);
				}
				else if(action.equals("s") && allUnits.checkCombat(GameBoardNow,player.Position.getFirst() + 1, player.Position.getSeconed())){
					GameBoardNow = allUnits.getCombat(player, new Pair(player.Position.getFirst() + 1, player.Position.getSeconed()), GameBoardNow);
				}
				else if(action.equals("a") && allUnits.checkCombat(GameBoardNow,player.Position.getFirst(), player.Position.getSeconed() - 1)){
					GameBoardNow = allUnits.getCombat(player, new Pair(player.Position.getFirst() , player.Position.getSeconed() - 1), GameBoardNow);					
				}
				else if(action.equals("d") && allUnits.checkCombat(GameBoardNow,player.Position.getFirst(), player.Position.getSeconed() + 1)){
					GameBoardNow = allUnits.getCombat(player, new Pair(player.Position.getFirst() , player.Position.getSeconed() + 1), GameBoardNow);
				}
				else {
					GameBoardNow = player.makeMove(GameBoardNow, action, player);
				}
				player.onGameTick();
				GameBoardNow = allUnits.notifyAllUnit(GameBoardNow,player);
			}
			else {
				System.out.println("Try Again");
			}

			if(allEnemiesDied(GameBoardNow)) {
				level++;
				if(finishAllLevels(args[0],level)) {
					System.out.println("Game is finished. You won!");
					System.exit(0);
				}
				else {
					GameBoardNow = loadLevel(args[0],level);
					player.Position = getPosition(GameBoardNow);
					allUnits.reset();
					updateObserverEnemies(GameBoardNow);
					continue;
				}
			}
			else if(playerIsDead(GameBoardNow)) {
				System.out.println( "You Lost.");
				System.exit(0);
			}
		}
		input.close();
	}


	private static boolean finishAllLevels(String path, int level) {
		File file = new File(path+"\\level "+level+".txt"); 
		return !file.isFile();
	}

	public static boolean playerIsDead(char[][] board) {
		for(int i = 0; i < board.length; i++ ) {
			for(int j = 0; j < board[i].length; j++ ) {
				if(board[i][j] == 'X')
					return true;
			}
		}
		return false;
	}

	public static boolean allEnemiesDied(char[][] board) {
		if(!allUnits.emptyList())
			return false;
		return true;
	}

	private static void updateObserverEnemies(char[][] board) {
		for(int i = 0; i < board.length; i++ ) {
			for(int j = 0; j < board[i].length; j++ ) {
				if(board[i][j] == 's')
					allUnits.attach(new Monster("Lannister Solider", 's', 80, 8, 3, new Pair(i,j), 3, 25));
				else if(board[i][j] == 'k')
					allUnits.attach(new Monster("Lannister Knight", 'k', 200, 14, 8, new Pair(i,j), 4, 50));
				else if(board[i][j] == 'q')
					allUnits.attach(new Monster("Queen's Guard", 'q', 400, 20, 15, new Pair(i,j), 5, 100));
				else if(board[i][j] == 'z')
					allUnits.attach(new Monster("Wright", 'z', 600, 30, 15, new Pair(i,j), 3, 100));
				else if(board[i][j] == 'b')
					allUnits.attach(new Monster("Bear-Wright", 'b', 1000, 75, 30, new Pair(i,j), 4, 250));
				else if(board[i][j] == 'g')
					allUnits.attach(new Monster("Giant-Wrightr", 'g', 1500, 100, 40, new Pair(i,j), 5, 500));
				else if(board[i][j] == 'w')
					allUnits.attach(new Monster("White Walker ", 'w', 2000 , 150, 50, new Pair(i,j), 6, 1000));
				else if(board[i][j] == 'M')
					allUnits.attach(new Monster("The Mountain", 'M', 1000,60, 25, new Pair(i,j), 6, 500));
				else if(board[i][j] == 'C')
					allUnits.attach(new Monster("Queen Cersei", 'C', 100,10, 10, new Pair(i,j), 1, 1000));
				else if(board[i][j] == 'K')
					allUnits.attach(new Monster("Night's King", 'K', 5000, 300, 150, new Pair(i,j), 3, 5000));
				else if(board[i][j] == 'B')
					allUnits.attach(new Trap("Bonus \"Trap\"", 'B', 1, 1, 1, new Pair(i,j), 5, 6,2,250));
				else if(board[i][j] == 'Q')
					allUnits.attach(new Trap("Queen's Trap", 'Q', 250, 50, 10, new Pair(i,j), 4, 10, 4,100));
				else if(board[i][j] == 'D')
					allUnits.attach(new Trap("Death Trap", 'D', 500, 150, 20, new Pair(i,j), 6, 10,3,250));
			}
		}	
	}

	private static char[][] loadLevel(String path, int level){
		File file = new File(path+"\\level "+level+".txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int height = 0;  int width = 0;  boolean first = true;  String num;
			while ((num = reader.readLine()) != null) {
				if(first) {
					width = num.length();
					first = false;
				}
				height++;
			}
			reader.close();
			char [][] newBoard = new char[height][width];
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;  int j = 0;
			while ((line = br.readLine()) != null) {
				for(int i=0;i<width;i++) {
					newBoard[j][i] = line.charAt(i);
				}
				j++;
			}
			br.close();
			return newBoard;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static void printBoard(char[][] board) {
		for(int i = 0; i<board.length;i++ ) {
			for(int j = 0; j < board[i].length;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public static Pair getPosition(char[][]board) {
		for(int i = 0; i<board.length;i++ ) {
			for(int j = 0; j < board[i].length;j++) {
				if(board[i][j] == '@') {
					return new Pair(i,j);
				}
			}
		}
		return null;
	}
}
