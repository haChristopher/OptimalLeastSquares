package hachristopher.ols.dataset;

public class SpiralGenerator {

	private double[] point = new double[2];
	private static final double z = 1.01;
	private static final double[][] rotationMatrix = { { Math.cos(0.1), Math.sin(0.1) },
													{ -Math.sin(0.1), Math.cos(0.1) } };

	public SpiralGenerator() {
		super();
		reset();
	}
	
	/**
	 * Next spiral point
	 * @return x and y value for the next spiral point
	 */
	public double[] next() {
		for (int row = 0; row < rotationMatrix.length; row++) {
			double sum = 0;
			for (int i = 0; i < point.length; i++) {
				sum += rotationMatrix[row][i] * point[i];
			}
			point[row] = sum *z;
		}
		return point.clone();
	}

	public void reset() {
		point[0] = 0.5;
		point[1] = 0;
	}

}
