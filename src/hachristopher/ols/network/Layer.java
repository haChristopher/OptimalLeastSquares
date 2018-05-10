package hachristopher.ols.network;

import hachristopher.ols.network.utils.MathUtils;

public class Layer {

	public int numNeurons;
	public int inputSize;
	public Activation actFunc;

	public double weights[][];

	public double inputs[];
	public double outputs[];

	public Layer(int inputSize, int numNeurons, Activation actFunc) {
		super();
		this.numNeurons = numNeurons;
		this.actFunc = actFunc;

		initalize();
	}

	/**
	 * Initalize input, output and set random weights
	 */
	private void initalize() {
		this.inputs = new double[this.inputSize];
		this.outputs = new double[this.numNeurons];
		this.weights = new double[this.inputSize][this.numNeurons];
		randomizeWeights();
	}
	
	/**
	 * Initalize weights with random values
	 */
	private void randomizeWeights() {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				this.weights[i][j] = MathUtils.randomBetween(-1, 1);
			}
		}
	}

	/**
	 * Foreward activation of the layer
	 * @return the outputs of the layer
	 */
	public double[] activate() {
		
		for (int toNeuron = 0; toNeuron < outputs.length; toNeuron++) {
			double sum = 0;
			for (int fromNeuron = 0; fromNeuron < inputs.length; fromNeuron++) {
				sum += this.inputs[fromNeuron] * this.weights[fromNeuron][toNeuron];
			}
			this.outputs[toNeuron] = this.actFunc.activate(sum);
		}

		return this.outputs;
	};
}
