package hachristopher.ols.network;

public enum Activation {
	SIGMOID, TANH, LEAKYRELU;

	public double activate(double x) {
		switch (this) {
		case SIGMOID:
			return 1 / (1 + Math.exp(-x));
		case TANH:
			return Math.tanh(x);
		case LEAKYRELU: {
			if (x > 0) {
				return x;
			} else {
				return x * 0.01;
			}
		}
		default:
			throw new AssertionError("Unknown activation function " + this);
		}
	}

	public double invert(double x) {

		switch (this) {
		case SIGMOID:
			return -Math.log((1 / x) - 1);
		case TANH:
			return Math.log(2.0 / (1.0 - x) - 1.0) / 2.0;
		case LEAKYRELU: {
			if (x > 0) {
				return x;
			} else {
				return x / 0.01;
			}
		}
		default:
			throw new AssertionError("Unknown activation function " + this);
		}
	}

	public double border(double x) {

		switch (this) {
		case SIGMOID: {
			if (x >= 1) {
				x = 0.99;
			} else if (x <= 0) {
				x = 0.01;
			}
			return x;
		}

		case TANH: {
			if (x >= 1) {
				x = 0.99;
			} else if (x <= -1) {
				x = -0.99;
			}
			return x;
		}

		case LEAKYRELU:
			return x;

		default:
			throw new AssertionError("Unknown activation function " + this);
		}
	}

}
