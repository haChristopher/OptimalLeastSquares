package hachristopher.ols.network;

import hachristopher.ols.cg.CgSolver;
import hachristopher.ols.network.utils.MathUtils;

public class Layer {

	private int id;
	public int numNeurons;
	public int inputSize;
	public boolean hasBias;
	public Activation actFunc;

	public double weights[][];

	public double inputs[];
	public double outputs[];
	public CgSolver solvers[];

	private double[][] target;

	public Layer(int inputSize, int numNeurons, Activation actFunc, boolean hasBias, int id) {
		super();
		this.numNeurons = numNeurons;
		this.actFunc = actFunc;
		this.inputSize = inputSize;
		this.hasBias = hasBias;
		this.id = id;

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
				this.weights[i][j] = MathUtils.randomBetween(-0.5,0.5);
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
				sum += inputs[fromNeuron] * weights[fromNeuron][toNeuron];
			}
			outputs[toNeuron] = actFunc.activate(sum);
		}
	}

	/**
	 * Adds current inputs to cg solver TODO change so all solvers use the same A
	 * Matrix
	 */
	public void addInputsToSolver(int targetId) {

		for (int solver = 0; solver < solvers.length; solver++) {
			solvers[solver].leastSquaresAdd(this.inputs, target[targetId][solver]);
		}

	}

	public void train(int steps) {
		for (int solver = 0; solver < solvers.length; solver++) {
			CgSolver cg = solvers[solver];
			cg.initOptimization();
			
			//train
			for (int i = 0; i < steps; i++) {
				cg.optimize();
			}
			
			//update weights
			double[] results = cg.x;
			
			for (int fromNeuron = 0; fromNeuron < weights.length; fromNeuron++) {
					weights[fromNeuron][solver] = results[fromNeuron];
			}
			
		}
	}

	/**
	 * TODO not efficient
	 * 
	 * @param data
	 * @throws Exception
	 */
	public double[][] getPropagateTarget(double[][] data) throws Exception {
		double[][] inverse = this.getSquarredWeightsInverse();
		data = MathUtils.applyInvActivation(data, actFunc);
		this.target = data;

		// Append Zeros to match number of neurons in previous layer
		if (data[0].length != inverse.length) {
			double[][] x = new double[data.length][inverse.length];
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < inverse.length; j++) {
					if (j < data[0].length) {
						x[i][j] = data[i][j];
					} else {
						x[i][j] = 0;
					}
				}
			}
			data = x;
		}

		return MathUtils.multiply(data, inverse);
	}

	/**
	 * 
	 * @return inverse of the pseudo squarred weights matrix
	 */
	public double[][] getSquarredWeightsInverse() {
		return MathUtils.invert(MathUtils.makeSquare(this.weights));
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
	
	public int getId() {
		return this.id;
	};

}
