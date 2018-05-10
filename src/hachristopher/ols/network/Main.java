package hachristopher.ols.network;

import hachristopher.ols.cg.ConjugateGradient;
import hachristopher.ols.network.utils.MathUtils;

public class Main {

	public static void main(String[] args) {
		
		int size = 100;
		double [] inVector;
		ConjugateGradient cg = new ConjugateGradient(size);
	
		// Initalize test matrix
		for (int i = 0; i < size; i++) {
			inVector = new double[size];
			for (int j = 0; j < size; j++) {
				inVector[j] = MathUtils.randomBetween(0,10);
			}
			cg.leastSquaresAdd(inVector, MathUtils.randomBetween(0,10));
		}
		
		cg.initOptimization();
		
		
		for (int i = 0; i < 200; i++) {
			cg.optimize();
		}
		
		double error = 0;
		cg.calculateResiduum();
		for (int i = 0; i < size; i++) {
			error += cg.residuum[i] * cg.residuum[i];
			System.out.println(cg.residuum[i]);
		}
		
		System.out.println("Error: " + error);

		System.out.println("Done!");
	}
	

}
