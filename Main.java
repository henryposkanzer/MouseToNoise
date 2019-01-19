import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
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
		SawtoothOscillatorBL osc2 = new SawtoothOscillatorBL();
		LineOut lineOut = new LineOut();
		Synthesizer synth = JSyn.createSynthesizer();
		//synthesizer needed as container for oscillator.
		//oscillator determines which sound is played
		//lineOut accesses speakers.
		
		synth.start();
		synth.add(lineOut);
		synth.add(osc);
		synth.add(osc2);
		//turn on synth and put the osc and lineOut in it.
		
		InputThread userCommands = new InputThread(lineOut);
		userCommands.start();
		
		osc.output.connect(0, lineOut.input, 0);
		osc.output.connect(0, lineOut.input, 1);
		osc2.output.connect(0, lineOut.input, 0);
		osc2.output.connect(0, lineOut.input, 1);
		lineOut.start();
		//connect the osc to the lineOut's output first
		//then turn on the lineOut to allow sound to play.
		System.out.println("Press q to quit.");
		
		final DataCollector dataCollector = new DataCollector();
		osc2.frequency.set(0);
		osc2.amplitude.set(0);

		while(userCommands.isRunning()) {
			try{
				TimeUnit.MILLISECONDS.sleep(50);
			}
			catch(InterruptedException e){
				System.out.println("Whoop, there it is.");
			}
			double[] data = dataCollector.collect();
			if(data[2] > 0) { //if the mouse is moving, play sound.
				osc.frequency.set(userCommands.getPitchOffset() + data[1]);
				osc.amplitude.set(userCommands.getVolumeOffset() * data[2]);
				osc.frequency.set(userCommands.getVolumeOffset() + data[1]);
				osc.amplitude.set(0.05 * data[2]);
				
				if(userCommands.overdrive()) {
					osc2.start();
					osc2.frequency.set(userCommands.getVolumeOffset() + data[1]-200);
					osc2.amplitude.set(0.05 * data[2]);
				} else {
					osc2.stop();
				}
				
			} else { //if it isn't, don't play sound.
				osc.frequency.set(0);
				osc.amplitude.set(0);
				
				if(userCommands.overdrive()) {
					osc2.frequency.set(0);
					osc2.amplitude.set(0);
				} else {
					osc2.stop();
				}
				
			}
		}
		
		synth.stop();
		//System.out.println("Stopping the sound.");
		
		//System.out.println("Closing the main thread.");
		return;
	}
	
}
