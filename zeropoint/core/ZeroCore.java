package zeropoint.core;


import java.util.Random;

import zeropoint.core.math.MathUtil;


public class ZeroCore {
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
	public static final void explode() {
		throw new EverythingWentWrongError();
	}
	public static final void explode(String message) {
		throw new EverythingWentWrongError(message);
	}
	public static final void explode(String message, Throwable cause) {
		throw new EverythingWentWrongError(message, cause);
	}
	public static final void explode(Throwable cause) {
		throw new EverythingWentWrongError(cause);
	}
	public static final String getRandomErrorMessage() {
		return getRandomErrorMessage(new Random());
	}
	public static final String getRandomErrorMessage(Random r) {
		try {
			int id = MathUtil.randInt(r, 0, randomErrorMessages.length);
			return randomErrorMessages[id];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			throw new EverythingWentWrongError("Everything is so broken, I can't even get you a witty error message.");
		}
	}
	public static final String getRandomErrorMessage(long seed) {
		return getRandomErrorMessage(new Random(seed));
	}
}
