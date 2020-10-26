package hw3;

public class Pair {
	
	private Integer first;
	private Integer seconed;
	public boolean visible;
	
	public Pair(int x,int y){
		this.first=x;
		this.seconed=y;
	}
	
	public int getFirst() {
		return this.first;
	}
	
	public int getSeconed() {
		return this.seconed;
	}
	
	public void setFirst(int number) {
		this.first = number;
	}
	
	public void setSeconed(int number) {
		this.seconed = number;
	}
}
