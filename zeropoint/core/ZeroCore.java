package zeropoint.core;


import java.util.Random;

import zeropoint.core.math.MathUtil;


/**
 * Miscellaneous bits that didn't fit anywhere else, but also didn't warrant their own class
 * 
 * @author Zero Point
 */
public class ZeroCore {
	/**
	 * The possible error messages used when calling getRandomErrorMessage(...)
	 */
	public static final String[] randomErrorMessages = new String[] {
		"Abandon All Hope, Ye Who See This Message.",
		"Every deity that exists must hate you, for things to be this wrong.",
		"Expect to see smoke.",
		"Are the laws of physics still the same? Because I actually can't be sure.",
		"Not even joking. Absolutely everything has gone wrong.",
		"I'm sorry, Dave.",
		"I literally do not know how things could possibly have gone this wrong."
	};
	private static class EverythingWentWrongError extends Error {
		private static final long serialVersionUID = 3302732629028982034L;
		public EverythingWentWrongError() {
			super(getRandomErrorMessage());
		}
		public EverythingWentWrongError(String message) {
			super(message);
		}
		public EverythingWentWrongError(String message, Throwable cause) {
			super(message, cause);
		}
		public EverythingWentWrongError(Throwable cause) {
			super(getRandomErrorMessage(), cause);
		}
	}
	/**
	 * Not to be used
	 */
	public static final void explode() {
		throw new EverythingWentWrongError();
	}
	/**
	 * Don't use this
	 */
	// This is here so Eclipse doesn't whine about the undocumented params
	@SuppressWarnings("javadoc")
	public static final void explode(String message) {
		throw new EverythingWentWrongError(message);
	}
	/**
	 * Do not touch
	 */
	// This is here so Eclipse doesn't whine about the undocumented params
	@SuppressWarnings("javadoc")
	public static final void explode(String message, Throwable cause) {
		throw new EverythingWentWrongError(message, cause);
	}
	/**
	 * This is a bad idea
	 */
	// This is here so Eclipse doesn't whine about the undocumented params
	@SuppressWarnings("javadoc")
	public static final void explode(Throwable cause) {
		throw new EverythingWentWrongError(cause);
	}
	/**
	 * @return a random error message
	 */
	public static final String getRandomErrorMessage() {
		return getRandomErrorMessage(new Random());
	}
	/**
	 * @param r
	 *            - the {@link Random} object used to get a random entry from the <code>randomErrorMessages</code> array
	 * @return a random error message
	 */
	public static final String getRandomErrorMessage(Random r) {
		try {
			int id = MathUtil.randInt(r, 0, randomErrorMessages.length);
			return randomErrorMessages[id];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			throw new EverythingWentWrongError("Everything is so broken, I can't even get you a witty error message.");
		}
	}
	/**
	 * @param seed
	 *            - the seed for an RNG used to get a random message
	 * @return a random entry from the <code>randomErrorMessages</code> array
	 */
	public static final String getRandomErrorMessage(long seed) {
		return getRandomErrorMessage(new Random(seed));
	}
}
