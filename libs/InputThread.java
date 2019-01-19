package libs;
import java.util.Scanner;

public class InputThread implements Runnable {
	Scanner keyboard;
	boolean quit;
	String nextLine;
	char nextCommand;
	
	public InputThread() {
		keyboard = new Scanner(System.in);
		quit = false;
	}
	
	public void run() {
		while(!quit) {
			nextLine = keyboard.nextLine();
			nextCommand = nextLine.charAt(0);
			
			switch(nextCommand) {
			case('q'):
				quit = true;
				break;
			case('n'):
				System.out.println("Never gonna give you up.");
				break;
			default:
				System.out.println("Invalid command.");
			}
		}
		
	}

}
