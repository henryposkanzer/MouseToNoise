//package libs;
import java.util.Scanner;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class InputThread extends Thread {
	Scanner keyboard;
	boolean quit;
	String nextLine;
	char nextCommand;
	LineOut lineOut;
	
	public InputThread(LineOut lineOut) {
		this.keyboard = new Scanner(System.in);
		this.quit = false;
		this.lineOut = lineOut;
	}
	
	public void run() {
		while(!quit) {
			this.nextLine = keyboard.nextLine();
			this.nextCommand = nextLine.charAt(0);
			
			switch(this.nextCommand) {
			case('s'):
				this.quit = true;
				break;
			case('n'):
				System.out.println("Never gonna give you up.");
				break;
			case('q'):
				lineOut.stop();
				lineOut.start(3.0);
				break;
			default:
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Closing thread.");
		return;
		//System.out.println("You forgot to kill your children.");
		
	}
	
	public boolean isRunning() {
		return !this.quit;
	}

}
