package hachristopher.ols.solver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import hachristopher.ols.network.utils.MathUtils;

public class CgSolver {

	public static final double DEFAULT_MAX_ERROR = 1.0e-10;

	// size of the matricies
	int size;

	// Ax = b
	public double[][] A;
	public double[] x;
	public double[] b;

	// Residiuums
	public double[] residuum;
	public double[] residuumOld;

	public double[] p;

	boolean haschanged;

	public CgSolver(int size) {
		super();
		this.size = size;
		this.A = new double[this.size][this.size];
		this.x = new double[this.size];
		this.b = new double[this.size];
		this.residuum = new double[this.size];
		this.residuumOld = new double[this.size];
		this.p = new double[this.size];
		this.haschanged = false;
		// randomInitX();
	}

	public void randomInitX() {
		for (int n = 0; n < this.size; n++) {
			this.x[n] = 0;
		}
	}

	/**
	 * Resets the matrix A
	 */
	public void resetMatrix() {
		this.A = new double[this.size][this.size];
	}

	/**
	 * TODO
	 * 
	 * @param inVector
	 * @param target
	 */
	public void leastSquaresAdd(double[] inVector, double target) {

		for (int n = 0; n < this.size; n++) {
			this.b[n] += inVector[n] * target;
		}

		for (int column = 0; column < this.size; column++) {
			for (int row = 0; row < this.size; row++) {
				this.A[column][row] += inVector[column] * inVector[row];
			}
		}

		this.haschanged = true;

	}

	/**
	 * Calculate the residuum = Ax-b
	 */
	public void calculateResiduum() {

		double[] Ax = new double[this.size];

		// calculate b - A * x
		for (int row = 0; row < this.size; row++) {
			// A * x
			for (int column = 0; column < this.size; column++) {
				Ax[row] += this.A[row][column] * this.x[column];
			}
			// b - Ax
			this.residuum[row] = this.b[row] - Ax[row];
		}
	}

	public void initOptimization() throws FileNotFoundException, UnsupportedEncodingException {

		calculateResiduum();

		// start first step in the direction of the residuum
		for (int n = 0; n < this.size; n++) {
			this.p[n] = this.residuum[n];
		}

		// TODO remove also import errors

		PrintWriter writer = new PrintWriter("data_A_13.txt", "UTF-8");

		for (int i = 0; i < A.length; i++) {
			String result = "";
			for (int j = 0; j < A[0].length; j++) {
				if (j != A[0].length - 1) {
					result = result + String.valueOf(A[i][j]) + ",";
				}else {
					result = result + String.valueOf(A[i][j]);
				}
			}
			writer.println(result);
		}

		writer.close();

	}

	/**
	 * caluclates the error
	 * 
	 * @return error
	 */
	public double getError() {
		double error = 0;
		double[] Ax = new double[this.size];

		// calculate A * x - b
		for (int row = 0; row < this.size; row++) {
			// A * x
			for (int column = 0; column < this.size; column++) {
				Ax[row] += this.A[row][column] * this.x[column];
			}
			// Ax - b
			error += Math.pow(this.b[row] - Ax[row], 2);
		}
		return error;
	}

	public void optimize() {
		optimize(A.length);
	}

	public void optimize(int maxIterations) {

		int iterations = 0;
		double rTr = 0;

		while (iterations < maxIterations) {
			// System.out.println("Here: " + Math.sqrt(rTr));
			// calculate alpha
			double alpha = 0;
			
			// rT * r
			rTr = 0;
			for (int n = 0; n < this.size; n++) {
				rTr += this.residuum[n] * this.residuum[n];
			}

			// pT * A * p
			double[] Ap = new double[this.size];
			double pTAp = 0;
			for (int row = 0; row < this.size; row++) {
				// A * p
				for (int column = 0; column < this.size; column++) {
					Ap[row] += this.A[row][column] * this.p[column];
				}
				// p * Ap
				pTAp += this.p[row] * Ap[row];
			}

			// check for division by zero
			if (pTAp != 0) {
				alpha = rTr / pTAp;
			} else {
				alpha = rTr / 0.00000000001;
			}

			// optimization step
			for (int n = 0; n < this.size; n++) {
				this.x[n] += alpha * this.p[n];
			}

			// update residuum
			for (int n = 0; n < this.size; n++) {
				this.residuumOld[n] = this.residuum[n];
				this.residuum[n] -= alpha * Ap[n];
			}

			// calculate beta = rt * r/rOldT * rOld
			double beta = 0;

			// rTr
			for (int n = 0; n < this.size; n++) {
				beta += this.residuum[n] * this.residuum[n];
			}

			if (rTr != 0) {
				beta /= rTr;
			} else {
				beta /= 0.00000000001;
			}

			// update p
			for (int n = 0; n < this.size; n++) {
				p[n] = this.residuum[n] + beta * this.p[n];
			}

			iterations++;

		}
	}

	/**
	 * Optimizing with Conjugate Gradient using the Jacobi Preconditioning TODO
	 * remove in both optimization functions the calculations which only have to be
	 * done once outside of the do while
	 * 
	 * @param number
	 *            of steps to be done
	 */
	public void optimmizeJacobi(int maxIterations) {

		int iterations = 0;
		// Takes pretty long ...
		double[][] M = createMInverse();

		
		// calculate p = M*r
		this.p = new double[this.size];
		for (int row = 0; row < this.size; row++) {
			for (int column = 0; column < this.size; column++) {
				this.p[row] += M[row][column] * residuum[column];
			}
		}

		while (iterations < maxIterations) {

			double alpha = 0;
			// calculate M * r
			double[] Mr = new double[M.length];
			for (int row = 0; row < this.size; row++) {
				for (int column = 0; column < this.size; column++) {
					Mr[row] += M[row][column] * residuum[column];
				}
			}
			
			// rT * Mr
			double rTMr = 0;
			for (int n = 0; n < this.size; n++) {
				rTMr += this.residuum[n] * Mr[n];
			}

			// pT * A * p
			double pTAp = 0;
			double[] Ap = new double[this.size];
			for (int row = 0; row < this.size; row++) {
				// A * p
				for (int column = 0; column < this.size; column++) {
					Ap[row] += this.A[row][column] * this.p[column];
				}
				// p * Ap
				pTAp += this.p[row] * Ap[row];
			}

			// check for division by zero
			if (pTAp != 0) {
				alpha = rTMr / pTAp;
			} else {
				alpha = rTMr / 0.00000000001;
			}

			// optimization step
			for (int n = 0; n < this.size; n++) {
				this.x[n] += alpha * this.p[n];
			}

			// update residuum
			for (int n = 0; n < this.size; n++) {
				this.residuumOld[n] = this.residuum[n];
				this.residuum[n] -= alpha * Ap[n];
			}

			// calculate beta = rt * r/rOldT * rOld
			double beta = 0;

			// calculate M * r
			Mr = new double[this.size];
			for (int row = 0; row < this.size; row++) {
				for (int column = 0; column < this.size; column++) {
					Mr[row] += M[row][column] * residuum[column];
				}
			}

			// New rTMr
			for (int n = 0; n < this.size; n++) {
				beta += this.residuum[n] * Mr[n];
			}

			if (rTMr != 0) {
				beta /= rTMr;
			} else {
				beta /= 0.00000000001;
			}

			// update p
			for (int n = 0; n < this.size; n++) {
				p[n] = Mr[n] + beta * this.p[n];
			}

			iterations++;
		}
	}

	/**
	 * Creates the matrix M for the Jacobi Preconditioning
	 * 
	 * @return inverted M
	 */
	public double[][] createMInverse() {
		double[][] M = new double[A.length][A[0].length];
		for (int i = 0; i < A.length; i++) {
			M[i][i] = A[i][i];
		}
		return MathUtils.invert(M);
	}

	/**
	 * checks if the Matrix A is Diagonal Dominant
	 * 
	 * @return true if diagonal dominant
	 */
	public boolean isDiagonalDominant() {
		double dia = 0;

		for (int row = 0; row < A.length; row++) {
			dia = Math.abs(A[row][row]);
			double rowSum = 0;
			for (int column = 0; column < A[0].length; column++) {
				if (column != row) {
					rowSum += Math.abs(A[row][column]);
				}
			}
			if (rowSum > dia) {
				return false;
			}
		}
		return true;
	}

}
