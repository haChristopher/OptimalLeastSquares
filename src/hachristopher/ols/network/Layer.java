package hachristopher.ols.network;

import hachristopher.ols.cg.CgSolver;
import hachristopher.ols.network.utils.MathUtils;

public class Layer {

	public int numNeurons;
	public int inputSize;
	public boolean hasBias;
	public Activation actFunc;

	public double weights[][];

	public double inputs[];
	public double outputs[];
	public CgSolver solvers[];

	public Layer(int inputSize, int numNeurons, Activation actFunc, boolean hasBias) {
		super();
		this.numNeurons = numNeurons;
		this.actFunc = actFunc;
		this.inputSize = inputSize;
		this.hasBias = hasBias;

		if (hasBias) {
			this.inputSize += 1;
		}

		initalize();
	}

	/**
	 * Initalize input, output and set random weights
	 */
	private void initalize() {
		this.inputs = new double[this.inputSize];
		this.outputs = new double[this.numNeurons];
		this.weights = new double[this.inputSize][this.numNeurons];
		this.solvers = new CgSolver[this.numNeurons];
		randomizeWeights();
		if (hasBias) {
			resetBias();
		}

		initSolvers();
	}

	/**
	 * Initalize CgSolver for every neuron
	 */
	private void initSolvers() {
		for (int neuron = 0; neuron < this.numNeurons; neuron++) {
			this.solvers[neuron] = new CgSolver(this.inputSize);
		}
	}

	/**
	 * Reset the bias weights to 1
	 */
	private void resetBias() {
		this.inputs[this.inputSize - 1] = 1;
	}

	/**
	 * Initalize weights with random values
	 */
	private void randomizeWeights() {
		for (int i = 0; i < this.inputSize; i++) {
			for (int j = 0; j < this.numNeurons; j++) {
				this.weights[i][j] = MathUtils.randomBetween(-1, 1);
			}
		}
	}

	/**
	 * Foreward activation of the layer
	 */
	public void activate() {

		for (int toNeuron = 0; toNeuron < outputs.length; toNeuron++) {
			double sum = 0;
			for (int fromNeuron = 0; fromNeuron < inputs.length; fromNeuron++) {
				sum += this.inputs[fromNeuron] * this.weights[fromNeuron][toNeuron];
			}
			this.outputs[toNeuron] = this.actFunc.activate(sum);
		}
	}

	public double[] getInputs() {
		return inputs;
	}

	public double[] getOutputs() {
		return outputs;
	}

	public void setInputs(double[] newInputs) {
		for (int i = 0; i < newInputs.length; i++) {
			this.inputs[i] = newInputs[i];
		}
	}

	public void setOutputs(double[] outputs) {
		this.outputs = outputs;
	};

}
