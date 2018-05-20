package hachristopher.ols.network.utils;

import org.apache.commons.math3.linear.MatrixUtils;

import hachristopher.ols.network.Activation;

public class MathUtils {

	/**
	 * @param min
	 * @param max
	 * @return random number between min and max
	 */
	public static double randomBetween(double min, double max) {
		return (double) (Math.random() * (max - min)) + min;
	}

	/**
	 * 
	 * @param matrix
	 *            to invert
	 * @return inverted matrix
	 */
	public static double[][] invert(double[][] matrix) {
		return MatrixUtils.inverse(MatrixUtils.createRealMatrix(matrix)).getData();
	}

	/**
	 * TODO check if empty
	 * 
	 * @param m1
	 * @param m2
	 * @return a*b
	 * @throws Exception
	 *             if sizes dont fit
	 */
	public static double[][] multiply(double[][] m1, double[][] m2) throws Exception {
		if (m1[0].length == m2.length) {
			double[][] result = new double[m1.length][m2[0].length];

			for (int m1r = 0; m1r < m1.length; m1r++) {
				for (int m2c = 0; m2c < m2[0].length; m2c++) {
					for (int m2r = 0; m2r < m2.length; m2r++) {
						result[m1r][m2c] += m1[m1r][m2r] * m2[m2r][m2c];
					}

				}
			}
			return result;
		} else {
			throw new Exception("Wrong array sizes");
		}
	}

	/**
	 * @param matrix
	 * @param a
	 * @return matrix with invert function of a applied on all elements
	 */
	public static double[][] applyInvActivation(double[][] matrix, Activation a) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = a.invert(matrix[i][j]);
			}
		}

		return matrix;
	}

	/**
	 * 
	 * @param matrix
	 * @return squared matrix filled up with small random values
	 */
	public static double[][] makeSquare(double[][] matrix) {
		int n = matrix.length;
		double[][] result = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j < matrix[0].length) {
					result[i][j] = matrix[i][j];
				} else {
					result[i][j] = randomBetween(0.00000000000001, 0.00000000000002);
				}
			}
		}
		return result;
	}

}
