package hachristopher.ols.network;

import java.util.ArrayList;
import java.util.ListIterator;

public class Ols {

	public int numNeurons;
	public int numLayers;

	ArrayList<Layer> layers = new ArrayList<Layer>();

	private double[][] inData;
	private double[][] outData;
	private double[][] results;

	/**
	 * 
	 * @param numLayers hidden layers
	 * @param numNeurons
	 */
	public Ols(double[][] inData, double[][] outData) {
		super();
		this.inData = inData;
		this.outData = outData;
		initalize();
	}

	/**
	 * Initalize arrays
	 */
	private void initalize() {
		this.results = new double[outData.length][outData[0].length];
	}

	/**
	 * Adds a Layer to the network
	 * 
	 * @param inputSize
	 * @param numNeurons
	 * @param actfunc
	 */
	public void addLayer(int inputSize, int numNeurons, Activation actfunc) {
		addLayer(inputSize, numNeurons, actfunc, false);
	}

	/**
	 * Adds a Layer with bias to the network
	 * 
	 * @param inputSize
	 * @param numNeurons
	 * @param actfunc
	 * @param bias
	 * 
	 */
	public void addLayer(int inputSize, int numNeurons, Activation actfunc, boolean hasBias) {
		layers.add(new Layer(inputSize, numNeurons, actfunc, hasBias));
	}

	/**
	 * Foreward activate all layers
	 * 
	 * @return the output of the last layer
	 */
	public double[] activateForeward(double[] data) {

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

		return layer.outputs;
	}

	/**
	 * Activates all data points through the network and saves the results
	 */
	private void activateAllData() {

		for (int data = 0; data < inData.length; data++) {
			results[data] = activateForeward(inData[data]);
		}

	}

	/**
	 * Starts the training of the network
	 */
	public void train() {
		activateAllData();

	}

	public double[][] getResults() {
		return results;
	}

}
