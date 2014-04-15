package zeropoint.core.math;


import java.util.Random;


/**
 * Basic maths utilities
 * 
 * @author Zero Point
 */
public class MathUtil {
	/**
	 * The internal {@link Random} object used by the non-static RNG methods
	 */
	protected final Random rand;
	/**
	 * Force a number to be between an upper and lower bound
	 * 
	 * @param value
	 *            - the value to restrict
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return the <code>value</code> param, iff it is above <code>min</code> and below <code>max</code>. If it's less than <code>min</code>, returns <code>min</code>. If it's above <code>max</code>, returns <code>max</code>.
	 */
	public static final double bound(double value, double min, double max) {
		if (max > min) {
			double i = min;
			min = max;
			max = i;
		}
		else if (max == min) {
			return min;
		}
		else if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}
		return value;
	}
	/**
	 * Force a number to be between an upper and lower bound
	 * 
	 * @param value
	 *            - the value to restrict
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return the <code>value</code> param, iff it is above <code>min</code> and below <code>max</code>. If it's less than <code>min</code>, returns <code>min</code>. If it's above <code>max</code>, returns <code>max</code>.
	 */
	public static final float bound(float value, float min, float max) {
		if (max > min) {
			float i = min;
			min = max;
			max = i;
		}
		else if (max == min) {
			return min;
		}
		else if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}
		return value;
	}
	/**
	 * Force a number to be between an upper and lower bound
	 * 
	 * @param value
	 *            - the value to restrict
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return the <code>value</code> param, iff it is above <code>min</code> and below <code>max</code>. If it's less than <code>min</code>, returns <code>min</code>. If it's above <code>max</code>, returns <code>max</code>.
	 */
	public static final int bound(int value, int min, int max) {
		if (max > min) {
			int i = min;
			min = max;
			max = i;
		}
		else if (max == min) {
			return min;
		}
		else if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}
		return value;
	}
	/**
	 * Force a number to be between an upper and lower bound
	 * 
	 * @param value
	 *            - the value to restrict
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return the <code>value</code> param, iff it is above <code>min</code> and below <code>max</code>. If it's less than <code>min</code>, returns <code>min</code>. If it's above <code>max</code>, returns <code>max</code>.
	 */
	public static final long bound(long value, long min, long max) {
		if (max > min) {
			long i = min;
			min = max;
			max = i;
		}
		else if (max == min) {
			return min;
		}
		else if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}
		return value;
	}
	/**
	 * Force a number to be between an upper and lower bound
	 * 
	 * @param value
	 *            - the value to restrict
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return the <code>value</code> param, iff it is above <code>min</code> and below <code>max</code>. If it's less than <code>min</code>, returns <code>min</code>. If it's above <code>max</code>, returns <code>max</code>.
	 */
	public static final short bound(short value, short min, short max) {
		if (max > min) {
			short i = min;
			min = max;
			max = i;
		}
		else if (max == min) {
			return min;
		}
		else if (value < min) {
			return min;
		}
		else if (value > max) {
			return max;
		}
		return value;
	}
	/**
	 * This method should never be used. It's just a reference to
	 * <a href="http://www.xkcd.com/221">XKCD 221</a>.
	 * 
	 * @return 4
	 */
	public static final int getRandomNumber() {
		return 4;
	}
	/**
	 * Generate a random byte
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random byte between <code>min</code> and <code>max</code>
	 */
	public static final byte randByte(Random r, byte min, byte max) {
		return (byte) randInt(r, min, max);
	}
	/**
	 * Generate a random double
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random double between <code>min</code> and <code>max</code>
	 */
	public static final double randDouble(Random r, double min, double max) {
		if (max < min) {
			double t = min;
			min = max;
			max = t;
		}
		else if (max == min) {
			return min;
		}
		return (min + ((max - min) * r.nextDouble()));
	}
	/**
	 * Generate a random float
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random float between <code>min</code> and <code>max</code>
	 */
	public static final float randFloat(Random r, float min, float max) {
		if (max < min) {
			float t = min;
			min = max;
			max = t;
		}
		else if (max == min) {
			return min;
		}
		return (min + ((max - min) * r.nextFloat()));
	}
	/**
	 * Generate a random int
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random int between <code>min</code> and <code>max</code>
	 */
	public static final int randInt(Random r, int min, int max) {
		if (max < min) {
			int t = min;
			min = max;
			max = t;
		}
		else if (max == min) {
			return min;
		}
		return r.nextInt(max - min) + min;
	}
	/**
	 * Generate a random long
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random long between <code>min</code> and <code>max</code>
	 */
	public static final long randLong(Random r, long min, long max) {
		if (max < min) {
			long t = min;
			min = max;
			max = t;
		}
		else if (max == min) {
			return min;
		}
		return (min + (long) (r.nextDouble() * (max - min)));
	}
	/**
	 * Generate a random short
	 * 
	 * @param r
	 *            - the {@link Random} object to use
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random short between <code>min</code> and <code>max</code>
	 */
	public static final short randShort(Random r, short min, short max) {
		return (short) randInt(r, min, max);
	}
	/**
	 * Generate a new MathUtil object with a new {@link Random} object as the RNG
	 */
	public MathUtil() {
		this.rand = new Random();
	}
	/**
	 * Generate a new MathUtil object with a new {@link Random} object as the RNG, using the given seed
	 * 
	 * @param seed
	 *            - the seed for the RNG
	 */
	public MathUtil(long seed) {
		this.rand = new Random(seed);
	}
	/**
	 * Generate a new MathUtil object with the given RNG
	 * 
	 * @param init
	 *            - the RNG to use
	 */
	public MathUtil(Random init) {
		this.rand = init;
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random byte
	 */
	public final byte randByte(byte min, byte max) {
		return randByte(this.rand, min, max);
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random double
	 */
	public final double randDouble(double min, double max) {
		return randDouble(this.rand, min, max);
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random float
	 */
	public final float randFloat(float min, float max) {
		return randFloat(this.rand, min, max);
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random float
	 */
	public final int randInt(int min, int max) {
		return randInt(this.rand, min, max);
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random long
	 */
	public final long randLong(long min, long max) {
		return randLong(this.rand, min, max);
	}
	/**
	 * Generate a random number using the internal RNG
	 * 
	 * @param min
	 *            - the minimum value to return
	 * @param max
	 *            - the maximum value to return
	 * @return a random short
	 */
	public final short randShort(short min, short max) {
		return randShort(this.rand, min, max);
	}
}
