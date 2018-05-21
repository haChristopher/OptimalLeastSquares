package hachristopher.ols.dataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public enum Datasets {
	XOR, SMALLSPIRAL, BIGSPIRAL, MNIST;

	private double[][] x;
	private double[][] y;

	public void load() {
		switch (this) {
		case XOR:
			loadXor();
			break;
		case SMALLSPIRAL:
			loadCSV("smallSpiralData.txt");
			break;
		case BIGSPIRAL:
			loadCSV("bigSpiralData.txt");
			break;
		case MNIST:
			loadXor();
			break;
		default:
			throw new AssertionError("Unknown dataset " + this);
		}
	}

	private void loadXor() {
		double[][] data = { { 0.01, 0.01 }, { 0.99, 0.01 }, { 0.01, 0.99 }, { 1.0, 1.0 } };
		double outData[] = { -0.5, 0.5, 0.5, -0.5 };
		this.x = data;
		this.y = transform(outData);
	}

	private void loadCSV(String filename) {

		String path = "src/" + filename;
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			int count = 0;
			int numData = Integer.parseInt(br.readLine());
			double NewInData[][] = new double[numData][2];
			double NewOutData[] = new double[numData];

			while ((line = br.readLine()) != null && count < numData) {
				String[] data = line.split(cvsSplitBy);
				NewInData[count][0] = Double.parseDouble(data[0]);
				NewInData[count][1] = Double.parseDouble(data[1]);
				NewOutData[count] = Double.parseDouble(data[2]);
				count++;
			}

			this.x = NewInData;
			this.y = transform(NewOutData);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * transforms one dimensional data into two dimensional data
	 * 
	 * @param one
	 *            dimensional data
	 * @return two dimensional data
	 */
	private double[][] transform(double[] data) {
		double[][] transformed = new double[data.length][1];
		for (int i = 0; i < data.length; i++) {
			transformed[i][0] = data[i];
		}
		return transformed;
	}

	public double[][] getX() {
		return x;
	}

	public double[][] getY() {
		return y;
	}

}