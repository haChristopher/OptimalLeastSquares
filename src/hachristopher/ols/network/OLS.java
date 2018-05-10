package hachristopher.ols.network;

import java.util.ArrayList;

public class Ols {

	public int numNeurons;
	public int numLayers;
	
	ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public double[] inData;
	
	


	/**
	 * 
	 * @param numLayers (hidden layers)
	 * @param numNeurons
	 */
	public Ols(int numLayers, int numNeurons) {
		super();
		this.numNeurons = numNeurons;
		this.numLayers = numLayers;
		initalize();
	}

	/**
	 * Initalize all arrays and weights with random values and
	 */
	private void initalize() {
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
	 * 
	 */
	public void activateForeward() {
		
	}

	/**
	 * Starts the training of the network
	 */
	public void start() {
		initalize();
	}

}
