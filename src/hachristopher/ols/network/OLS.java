package hachristopher.ols.network;

import java.util.ArrayList;
import java.util.ListIterator;

import hachristopher.ols.network.utils.MathUtils;

public class Ols {

	public int numNeurons;
	public int numLayers;

	ArrayList<Layer> layers = new ArrayList<Layer>();

	private double[][] inData;
	private double[][] outData;
	private double[][] results;

	/**
	 * 
	 * @param numLayers
	 *            hidden layers
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
	public double[] activateForeward(double[] data, int targetId) {

		ListIterator<Layer> iterator = layers.listIterator();

		Layer layer = iterator.next();
		layer.setInputs(data);
		layer.activate();
		layer.addInputsToSolver(targetId);

		while (iterator.hasNext()) {
			int prevIndex = iterator.previousIndex();
			layer = iterator.next();
			layer.setInputs(layers.get(prevIndex).getOutputs());
			layer.activate();
			layer.addInputsToSolver(targetId);
		}

		return layer.outputs;
	}

	/**
	 * Activates all data points through the network and saves the results
	 */
	private void activateAllData() {

		// TODO improve result array
		for (int data = 0; data < inData.length; data++) {
			double[] result = activateForeward(inData[data], data);
			for (int i = 0; i < result.length; i++) {
				this.results[data][i] = result[i];
			}
		}

	}

	/**
	 * Propagate the Target back and save it in each layer
	 * @throws Exception 
	 */
	private void propagateTarget() throws Exception {
		double[][] targets = outData;
		for (int layer = layers.size() -1 ; layer >= 0; layer--) {
			targets = layers.get(layer).getPropagateTarget(targets);
		}
	}

	/**
	 * Starts the training of the network
	 * 
	 * @throws Exception
	 */
	public void train(int steps) throws Exception {
		propagateTarget();
		activateAllData();
		
		layers.get(layers.size()-2).train(steps);
		activateAllData();
	}

	public double[][] getResults() {
		return results;
	}

}
