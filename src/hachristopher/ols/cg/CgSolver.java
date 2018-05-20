package hachristopher.ols.cg;

public class CgSolver {

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

		// calculate A * x - b
		for (int row = 0; row < this.size; row++) {
			// A * x
			for (int column = 0; column < this.size; column++) {
				Ax[row] += this.A[row][column] * this.x[column];
			}
			// Ax - b
			this.residuum[row] = Ax[row] - this.b[row];
		}

	}

	public void initOptimization() {
		
		calculateResiduum();

		// start first step in the direction of the residuum
		for (int n = 0; n < this.size; n++) {
			this.p[n] = -this.residuum[n];
		}

	}

	public void optimize() {

		// calculate alpha

		double alpha = 0;
		double rTr = 0;
		double pTAp = 0;

		// rT * r
		for (int n = 0; n < this.size; n++) {
			rTr += this.residuum[n] * this.residuum[n];
		}

		// pT * A * p
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
			this.residuum[n] += alpha * Ap[n];
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
			p[n] = -this.residuum[n] + beta * this.p[n];
		}

	}

}
