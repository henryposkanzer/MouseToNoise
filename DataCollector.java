import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class DataCollector {
	double newY, speed;
	int numSamples = 0;
	List<Double> xSamples = new LinkedList<Double>();
	List<Double> ySamples = new LinkedList<Double>();
	List<Double> speedSamples = new LinkedList<Double>();
	int maxSamples = 2;
	public DataCollector() {
		xSamples.add(0.0);
		ySamples.add(0.0);
		speedSamples.add(0.0);
	}
	public double[] collect() {
		Point startPosition = MouseInfo.getPointerInfo().getLocation();
		xSamples.add(startPosition.getX());
		ySamples.add(startPosition.getY());
		double xChange = xSamples.get(0) - xSamples.get(1);
		double yChange = ySamples.get(0) - ySamples.get(1);
		speedSamples.add(Math.sqrt(xChange * xChange + yChange * yChange));
		//maxSamples = 2 + xSamples.get(0).intValue() / 100;
		maxSamples = 2 + (int) mean(xSamples) / 100;
		while(xSamples.size() > maxSamples) {
			xSamples.remove(0);
			ySamples.remove(0);
			speedSamples.remove(0);
		}
		double[] data = new double[3];
		data[0] = mean(xSamples);
		data[1] = mean(ySamples);
		data[2] = mean(speedSamples);
		return data;
	}
	private static double mean(List<Double> data) {
		double sum = 0;
		for(double datum : data) {
			sum += datum;
		}
		return sum / data.size();
	}
}
