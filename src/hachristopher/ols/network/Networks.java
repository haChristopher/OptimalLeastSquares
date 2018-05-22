package hachristopher.ols.network;

import hachristopher.ols.dataset.Datasets;

public enum Networks {
	
	BIGSPIRALFOURHIDDEN, SMALLSPIRALSHALLOW;
	
	public Ols getNet() {

		switch (this) {
		case BIGSPIRALFOURHIDDEN:
			return net1();
		case SMALLSPIRALSHALLOW:
			return net1();
		default:
			throw new AssertionError("Unknown network " + this);
		}
	}
	
	private Ols net1() {
		
		boolean bias = true;
		Datasets data = Datasets.BIGSPIRAL;
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
	
}
