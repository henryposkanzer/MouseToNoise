import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

class Main{
	public static final int SAMPLE_RATE = 10;
	public static final int BYTES_PER_SAMPLE = 1;
	public static final int BITS_PER_SAMPLE = BYTES_PER_SAMPLE * 8;
	public static final int SAMPLE_BUFFER_SIZE = 1000;

	public static void main(String args[]){
		
		System.out.println("Press q to quit.");
		double newX, newY, oldX, oldY, speed;
		oldX = oldY = 0;

		while(true) {
			Point startPosition = MouseInfo.getPointerInfo().getLocation();
			newX = startPosition.getX();
			newY = startPosition.getY();
			System.out.println("Cursor position: x is " + newX + ". y is " + newY + ".");

			speed = Math.sqrt( ((newX-oldX)*(newX-oldX)) +  ((newY-oldY)*(newY-oldY)) );
			System.out.println("Speed is: " + speed);

			oldX = newX;
			oldY = newY;
			try{
				TimeUnit.MILLISECONDS.sleep(100);
			}
			catch(InterruptedException e){
				System.out.println("Whoop, there it is.");
			}
			//MouseInfo.getPointerInfo().getLocation()
			//Returns a point
		}
	}
}
