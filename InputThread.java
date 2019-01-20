//package libs;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class InputThread extends Thread {
	private Scanner keyboard;
	private boolean quit;
	private String nextLine;
	private char nextCommand;
	private LineOut lineOut;

	private double pitchOffset;
	private double volumeOffset;
	private boolean overdrive = false;

	public InputThread(LineOut lineOut) {
		this.keyboard = new Scanner(System.in);
		this.quit = false;
		this.lineOut = lineOut;
		pitchOffset = 200.0;
		volumeOffset = 0.05;
	}

	public void run() {
		while(!quit) {
			this.nextLine = keyboard.nextLine();
			while(nextLine.length() > 0) {
				this.nextCommand = nextLine.charAt(0);
				this.nextLine = this.nextLine.substring(1);

				switch(this.nextCommand) {
				case('s'):
					this.quit = true;
				break;
				case('o'):
					this.overdrive = true;
				System.out.println("OVERDRIVE MODE ACTIVATE");
				break;
				case('n'):
					System.out.println("Never gonna give you up.");
				break;
				case('p'):
					this.raisePitch();
				break;
				case('P'):
					this.lowerPitch();
				break;
				case('v'):
					this.raiseVolume();
				break;
				case('V'):
					this.lowerVolume();
				break;
				case('q'):
					lineOut.stop();
				System.out.println("Quitting");
				try{
					TimeUnit.SECONDS.sleep(5);
				} catch(InterruptedException e){
					System.out.println("Whoop, there it is.");
				}
				System.out.println("There is no escape.");
				lineOut.start();
				break;
				
				default:
				System.out.println("Incorrect command");
				break;
				}
			}
		}
		System.out.println("Closing thread.");
		return;
		//System.out.println("You forgot to kill your children.");

	}

	public boolean overdrive() {
		return this.overdrive;
	}

	private void raisePitch() {
		if(this.pitchOffset < 2000) {
			pitchOffset *= 2;
		}
	}

	private void lowerPitch() {
		if(this.pitchOffset > 100) {
			pitchOffset /= 2;
		}
	}

	private void raiseVolume() {
		if(this.volumeOffset < 0.15) {
			volumeOffset += 0.05;
		}
	}

	private void lowerVolume() {
		if(this.volumeOffset > 0.05) {
			volumeOffset -= 0.05;
		}
	}

	public boolean isRunning() {
		return !this.quit;
	}

	public double getVolumeOffset() {
		return this.volumeOffset;
	}

	public double getPitchOffset() {
		return this.pitchOffset;
	}

}
