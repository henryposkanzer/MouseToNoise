import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
//import javax.sound.midi.Synthesizer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitGenerator;

class Main{
	public static final int SAMPLE_RATE = 10;
	public static final int BYTES_PER_SAMPLE = 1;
	public static final int BITS_PER_SAMPLE = BYTES_PER_SAMPLE * 8;
	public static final int SAMPLE_BUFFER_SIZE = 1000;

	public static void main(String args[]){

		
		SawtoothOscillatorBL osc = new SawtoothOscillatorBL();
		LineOut lineOut = new LineOut();
		Synthesizer synth = JSyn.createSynthesizer();
		//synthesizer needed as container for oscillator.
		//oscillator determines which sound is played
		//lineOut accesses speakers.
		
		synth.start();
		synth.add(lineOut);
		synth.add(osc);
		//turn on synth and put the osc and lineOut in it.
		
		osc.output.connect(0, lineOut.input, 0);
		osc.output.connect(0, lineOut.input, 1);
		lineOut.start();
		//connect the osc to the lineOut's output first
		//then turn on the lineOut to allow sound to play.
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
				TimeUnit.MILLISECONDS.sleep(50);
			}
			catch(InterruptedException e){
				System.out.println("Whoop, there it is.");
			}
			
			if(speed > 0) { //if the mouse is moving, play sound.
				osc.frequency.set(200+newY);
				osc.amplitude.set(0.05 * speed);
			} else { //if it isn't, don't play sound.
				osc.frequency.set(0);
				osc.amplitude.set(0);
			}
		}
	}
}
