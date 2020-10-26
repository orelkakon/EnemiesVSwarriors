package hw3;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GenerateRandomActions implements ActionReader , RandomGenerator {
	private Scanner input =  new Scanner (System.in);
	private static LinkedList<String> userTxt = new LinkedList<String>();
	private static LinkedList<Integer> numTxt = new LinkedList<Integer>();
	private int userTxtNum = 0; 
	private int numTxtNum = 0; 
	private boolean debugMode = false;
	public GenerateRandomActions(boolean debug, String path) {
		if(debug) {
			debugMode = true;
			readFromUserTxt();
			readFromNumberTxt();
		}
	}
	public static void readFromUserTxt() {
		try {
			List<String> actions = Files.readAllLines(Paths.get("user_actions.txt"));
			for (int i = 0; i < actions.size(); i++) {
				userTxt.add(actions.get(i));
			}
		} catch (IOException e1) {
			System.out.println("FILE NOT FOUND");
		}
	}
	
	public static void readFromNumberTxt() {
		try {
			List<String> ranNum = Files.readAllLines(Paths.get("random_numbers.txt"));
			for (int i = 0; i < ranNum.size(); i++) {
				numTxt.add(Integer.parseInt(ranNum.get(i)));
			}
		} catch (IOException e1) {
			System.out.println("FILE NOT FOUND");
		}
	}

	@Override
	public int nextInt(int n) {
		Random rand = new Random();
		if(debugMode) { //debugMode
			numTxtNum++;
			return numTxt.get(numTxtNum - 1);
		}
		else {
			return rand.nextInt(n);
		}
	}

	@Override
	public String nextAction() {
		if(debugMode) {
			userTxtNum++;
			return userTxt.get(userTxtNum - 1);
		}
		else {
			return input.next();
		}
	}
}
