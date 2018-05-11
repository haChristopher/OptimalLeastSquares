package hachristopher.ols.network;

import java.util.ArrayList;
import java.util.ListIterator;

public class Ols {

	public int numNeurons;
	public int numLayers;

	ArrayList<Layer> layers = new ArrayList<Layer>();

	private double[][] inData;
	private double[] outData;
	private double[] results;

	/**
	 * 
	 * @param numLayers
	 *            (hidden layers)
	 * @param numNeurons
	 */
	public Ols(double[][] inData, double[] outData) {
		super();
		this.inData = inData;
		this.outData = outData;
		initalize();
	}

	/**
	 * Initalize arrays
	 */
	private void initalize() {
		this.results = new double[outData.length];
	}

	/**
	 * Adds a Layer to the network
	 * 
	 * @param inputSize
	 * @param numNeurons
	 * @param actfunc
	 */
	public void addLayer(int inputSize, int numNeurons, Activation actfunc) {
		layers.add(new Layer(inputSize, numNeurons, actfunc));
	}

	/**
	 * Foreward activate all layers
	 * 
	 * @return the output of the last layer
	 */
	private double activateForeward(double[] data) {

		ListIterator<Layer> iterator = layers.listIterator();

		Layer layer = iterator.next();
		layer.setInputs(data);
		layer.activate();

		while (iterator.hasNext()) {
			int prevIndex = iterator.previousIndex();
			layer = iterator.next();
			layer.setInputs(layers.get(prevIndex).getOutputs());
			layer.activate();
		}

		return layer.outputs[0];
	}

	/**
	 * Starts the training of the network
	 */
	public void train() {
		for (int data = 0; data < inData.length; data++) {
			results[data] = activateForeward(inData[data]);
		}
	}

	public double[] getResults() {
		return results;
	}

}
