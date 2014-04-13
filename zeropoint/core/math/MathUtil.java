package zeropoint.core.math;


import java.util.Random;


public class MathUtil {
	public static final byte BYTE_MAX = Byte.MAX_VALUE;
	public static final byte BYTE_MIN = Byte.MIN_VALUE;
	public static final short SHORT_MAX = Short.MAX_VALUE;
	public static final short SHORT_MIN = Short.MIN_VALUE;
	public static final int INT_MAX = Integer.MAX_VALUE;
	public static final int INT_MIN = Integer.MIN_VALUE;
	public static final long LONG_MAX = Long.MAX_VALUE;
	public static final long LONG_MIN = Long.MIN_VALUE;
	public static final float FLOAT_MAX = Float.MAX_VALUE;
	public static final float FLOAT_MIN = Float.MIN_VALUE;
	public static final float FLOAT_MIN_NORM = Float.MIN_NORMAL;
	public static final double DOUBLE_MAX = Double.MAX_VALUE;
	public static final double DOUBLE_MIN = Double.MIN_VALUE;
	public static final double DOUBLE_MIN_NORM = Double.MIN_NORMAL;
	public static final double NAN_DOUBLE = Double.NaN;
	public static final float NAN_FLOAT = Float.NaN;
	public static final double NEG_INF_DOUBLE = Double.NEGATIVE_INFINITY;
	public static final float NEG_INF_FLOAT = Float.NEGATIVE_INFINITY;
	public static final double POS_INF_DOUBLE = Double.POSITIVE_INFINITY;
	public static final float POS_INF_FLOAT = Float.POSITIVE_INFINITY;
	public static final double NAN = NAN_DOUBLE;
	public static final double POS_INF = POS_INF_DOUBLE;
	public static final double NEG_INF = NEG_INF_DOUBLE;
	protected final Random rand;
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
	 * XKCD 221.
	 * 
	 * @return 4
	 */
	public static final int getRandomNumber() {
		return 4;
	}
	public static final byte randByte(Random r, byte min, byte max) {
		return (byte) randInt(r, min, max);
	}
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
	public static final short randShort(Random r, short min, short max) {
		return (short) randInt(r, min, max);
	}
	public MathUtil() {
		rand = new Random();
	}
	public MathUtil(long seed) {
		rand = new Random(seed);
	}
	public MathUtil(Random init) {
		rand = init;
	}
	public final byte randByte(byte min, byte max) {
		return randByte(rand, min, max);
	}
	public final double randDouble(double min, double max) {
		return randDouble(rand, min, max);
	}
	public final float randFloat(float min, float max) {
		return randFloat(rand, min, max);
	}
	public final int randInt(int min, int max) {
		return randInt(rand, min, max);
	}
	public final long randLong(long min, long max) {
		return randLong(rand, min, max);
	}
	public final short randShort(short min, short max) {
		return randShort(rand, min, max);
	}
}
