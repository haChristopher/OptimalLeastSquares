package hachristopher.ols.network.utils;

import org.apache.commons.math3.linear.MatrixUtils;

public class MathUtils {
	
	/**
	 * @param min
	 * @param max
	 * @return random number between min and max
	 */
	public static double randomBetween (double min, double max) {
		return (double) (Math.random() * (max - min)) + min;
	}

	/**
	 * 
	 * @param matrix to invert
	 * @return inverted matrix
	 */
	public static double[][] invert(double[][] matrix){	
		return MatrixUtils.inverse(MatrixUtils.createRealMatrix(matrix)).getData();	
	}
}
