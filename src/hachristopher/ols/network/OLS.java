package hachristopher.ols.network;

public class Ols {

	Activation actFunc;
	public int numNeurons;
	public int numLayers;

	public double[] inData;
	
	


	/**
	 * 
	 * @param numLayers (hidden layers)
	 * @param numNeurons
	 * @param actFunc
	 */
	public Ols(int numLayers, int numNeurons, Activation actFunc) {
		super();
		this.actFunc = actFunc;
		this.numNeurons = numNeurons;
		this.numLayers = numLayers;
	}

	/**
	 * Initalize all arrays and weights with random values and
	 */
	private void initalize() {

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
