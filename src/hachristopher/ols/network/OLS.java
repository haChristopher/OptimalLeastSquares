package hachristopher.ols.network;

public class OLS {

	double[] inData;
	Activation actFunc;

	/**
	 * 
	 * @param numLayers 
	 * @param numNeurons
	 * @param actFunc
	 */
	public OLS(int numLayers, int numNeurons, Activation actFunc) {
		super();
		this.actFunc = actFunc;
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
