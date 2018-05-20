package hachristopher.ols.dataset;

public enum Datasets {
	XOR, SMALLSPIRAL, BIGSPIRAL, MNIST;

	public double[][] getX() {
		switch (this) {
		case XOR : return getXorIn();
		case SMALLSPIRAL: return getXorIn();
		case BIGSPIRAL: return getXorIn();
		case MNIST: return getXorIn();
		default:
			throw new AssertionError("Unknown dataset " + this);
		}
	}
	
	public double[][] getY() {
		switch (this) {
		case XOR : return getXorOut();
		case SMALLSPIRAL: return getXorOut();
		case BIGSPIRAL: return getXorOut();
		case MNIST: return getXorOut();
		default:
			throw new AssertionError("Unknown dataset " + this);
		}
	}


	private double[][] getXorIn() {
		double[][] data = {{0.01, 0.01}, {0.99, 0.01}, {0.01, 0.99}, {1.0, 1.0}};
		return data;
	}
	
	private double[][] getXorOut(){
		double outData[] = {-0.5, 0.5, 0.5, -0.5};
		
		double[][] transformed = new double[outData.length][1];
		for (int i = 0; i < outData.length; i++) {
			transformed[i][0] =  outData[i];
		}
		
		return transformed;
	}

}