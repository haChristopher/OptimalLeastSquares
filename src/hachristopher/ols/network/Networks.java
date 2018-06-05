package hachristopher.ols.network;

import hachristopher.ols.dataset.Datasets;

public enum Networks {
	
	BIGSPIRAL, SMALLSPIRALDEEP, SMALLSPIRALREALLYDEEP;
	
	public Ols getNet() {

		switch (this) {
		case BIGSPIRAL:
			return net1();
		case SMALLSPIRALDEEP:
			return net2();
		case SMALLSPIRALREALLYDEEP:
			return net3();
		default:
			throw new AssertionError("Unknown network " + this);
		}
	}
	
	private Ols net1() {
		
		boolean bias = true;
		Datasets data = Datasets.BIGSPIRAL;
		data.load();
		Ols net = new Ols(data.getX(), data.getY());

		net.addLayer(2, 200, Activation.TANH, bias);
		net.addLayer(200, 200, Activation.TANH, bias);
		net.addLayer(200, 200, Activation.TANH, bias);
		net.addLayer(200, 1, Activation.TANH);
		
		return net;
	}
	
	private Ols net2() {
		
		boolean bias = true;
		Datasets data = Datasets.SMALLSPIRAL;
		data.load();
		Ols net = new Ols(data.getX(), data.getY());

		net.addLayer(2, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 1, Activation.TANH);
		
		return net;
	}
	
	
	private Ols net3() {
		
		boolean bias = true;
		Datasets data = Datasets.SMALLSPIRAL;
		data.load();
		Ols net = new Ols(data.getX(), data.getY());

		net.addLayer(2, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 100, Activation.TANH, bias);
		net.addLayer(100, 1, Activation.TANH);
		
		return net;
	}
	
}
