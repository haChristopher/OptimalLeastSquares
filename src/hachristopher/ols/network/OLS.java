package hachristopher.ols.network;

import java.util.ArrayList;
import java.util.ListIterator;

public class Ols {

	public int numNeurons;
	public int numLayers;
	
	ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public double[] inData = {1,2,3,4,5,6,7,8,9,10};
	public double[] results;
	
	


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
	 * Foreward activate all layers and save the results
	 * 
	 */
	public void activateForeward() {
		
		ListIterator<Layer> iterator = layers.listIterator();
	    
	    Layer layer = iterator.next();
	    layer.setInputs(inData);
	    layer.activate();
	    
	    while(iterator.hasNext()){
	    		layer = iterator.next();
	    		int prevIndex = iterator.previousIndex();
	    		layer.setInputs(layers.get(prevIndex).getOutputs());
	    		layer.activate();
	    }	
	    
	    System.out.println(layer.getOutputs().length);
	}

	/**
	 * Starts the training of the network
	 */
	public void start() {
		initalize();
	}

}
