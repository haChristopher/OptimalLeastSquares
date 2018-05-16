package hachristopher.ols.network.utils;

public class MathUtils {
	
	/**
	 * @param min
	 * @param max
	 * @return random number between min and max
	 */
	public static double randomBetween (double min, double max) {
		return (double) (Math.random() * (max - min)) + min;
	}

}
