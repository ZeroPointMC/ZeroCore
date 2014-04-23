package zeropoint.core.logger;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import zeropoint.core.date.Datetime;
import zeropoint.core.io.file.FileBase;


/**
 * A (fairly) easy to use formatter for Java logging
 * 
 * @author Zero Point
 */
public class LoggingFormatter extends Formatter {
	// Base flags for individual components
	/**
	 * Indicates that no time/date should be shown
	 */
	public static final int FLAG_NONE = 0;
	/**
	 * Indicates that the year should be shown in logging output
	 */
	public static final int FLAG_YEAR = 1;
	/**
	 * Indicates that the month should be shown in logging output
	 */
	public static final int FLAG_MONTH = 2;
	/**
	 * Indicates that the day should be shown in logging output
	 */
	public static final int FLAG_DAY = 4;
	/**
	 * Indicates that the hour should be shown in logging output
	 */
	public static final int FLAG_HOUR = 8;
	/**
	 * Indicates that the minute should be shown in logging output
	 */
	public static final int FLAG_MINUTE = 16;
	/**
	 * Indicates that the second should be shown in logging output
	 */
	public static final int FLAG_SECOND = 32;
	/**
	 * Indicates that the millisecond should be shown in logging output
	 */
	public static final int FLAG_MILLISECOND = 64;
	// Compound flags for convenience
	/**
	 * Indicates that the entire date should be shown in logging output
	 */
	public static final int FLAG_DATE_ALL = FLAG_YEAR | FLAG_MONTH | FLAG_DAY;
	/**
	 * Indicates that the time (without hours) should be shown in logging output
	 */
	public static final int FLAG_TIME_NO_HOUR = FLAG_MINUTE | FLAG_SECOND;
	/**
	 * Indicates that the time (without seconds) should be shown in logging output
	 */
	public static final int FLAG_TIME_NO_SECOND = FLAG_HOUR | FLAG_MINUTE;
	/**
	 * Indicates that the time should be shown as HH:MM:SS should be shown in logging output
	 */
	public static final int FLAG_TIME_STANDARD = FLAG_HOUR | FLAG_MINUTE | FLAG_SECOND;
	/**
	 * Indicates that the time should be shown as HH:MM:SS:II should be shown in logging output, where II is milliseconds
	 */
	public static final int FLAG_TIME_FULL = FLAG_TIME_STANDARD | FLAG_MILLISECOND;
	/**
	 * Indicates that the entire date and standard time (HH:MM:SS) should be shown in logging output
	 */
	public static final int FLAG_DATETIME_STANDARD = FLAG_DATE_ALL | FLAG_TIME_STANDARD;
	/**
	 * Indicates that the entire date and full time (with milliseconds) should be shown in logging output
	 */
	public static final int FLAG_DATETIME_ALL = FLAG_DATE_ALL | FLAG_TIME_FULL;
	/**
	 * The flags applied to this LoggingFormatter object at construction
	 */
	public final int flags;
	/**
	 * If <code>true</code>, no stack trace will be printed when logging exceptions
	 */
	public final boolean shortMode;
	/**
	 * Construct a new LoggingFormatter with the flags set to FLAG_TIME_STANDARD (HH:MM:SS)
	 */
	public LoggingFormatter() {
		this.flags = FLAG_NONE;
		this.shortMode = false;
	}
	/**
	 * Construct a new LoggingFormatter with the given flags
	 * 
	 * @param flags
	 *            - the flags to use
	 */
	public LoggingFormatter(int flags) {
		this.flags = flags;
		this.shortMode = false;
	}
	/**
	 * Construct a new LoggingFormatter with the given flags
	 * 
	 * @param flags
	 *            - the flags to use
	 * @param useShort
	 *            - <code>true</code> to leave out the stack trace when logging exceptions, <code>false</code> to display
	 */
	public LoggingFormatter(int flags, boolean useShort) {
		this.flags = flags;
		this.shortMode = useShort;
	}
	/**
	 * Construct a new LoggingFormatter with the given flags
	 * 
	 * @param useShort
	 *            - <code>true</code> to leave out the stack trace when logging exceptions, <code>false</code> to display
	 */
	public LoggingFormatter(boolean useShort) {
		this.flags = FLAG_NONE;
		this.shortMode = useShort;
	}
	// Inspired by Minecraft Forge's logging format
	// Credit for the ideas and small snippets of code goes to cpw
	@Override
	public String format(LogRecord record) {
		StringBuffer msg = new StringBuffer();
		// My work - inspired by Forge, written by me
		if (this.showDateAny() || this.showTimeAny()) {
			Datetime now = new Datetime();
			StringBuffer spec = new StringBuffer();
			if (showYear()) {
				spec.append((spec.toString().length() > 0 ? "-" : "") + Datetime.FMT_YEAR_LONG);
			}
			if (showMonth()) {
				spec.append((spec.toString().length() > 0 ? "-" : "") + Datetime.FMT_MONTH_NUM);
			}
			if (showDay()) {
				spec.append((spec.toString().length() > 0 ? "-" : "") + Datetime.FMT_DAY_NUM);
			}
			if (showHour()) {
				spec.append((spec.toString().length() > 0 ? " " : "") + Datetime.FMT_HOUR_24);
			}
			if (showMinute()) {
				spec.append((spec.toString().length() > 0 ? ":" : "") + Datetime.FMT_MINUTE);
			}
			if (showSecond()) {
				spec.append((spec.toString().length() > 0 ? ":" : "") + Datetime.FMT_SECOND);
			}
			if (showMilli()) {
				spec.append((spec.toString().length() > 0 ? ":" : "") + Datetime.FMT_MILLISECOND);
			}
			msg.append("[" + now.format(spec.toString()) + "] ");
		}
		// Borrowed from Forge
		// All glory to cpw!
		Level level = record.getLevel();
		String levelName = level.getLocalizedName();
		if (levelName == null) {
			levelName = level.getName();
		}
		if (record.getLoggerName() != null) {
			msg.append("[" + record.getLoggerName() + "] ");
		}
		else {
			msg.append("[] ");
		}
		if ((levelName != null) && (levelName.length() > 0)) {
			msg.append("[" + levelName + "] ");
		}
		else {
			msg.append("[] ");
		}
		msg.append(formatMessage(record));
		Throwable thr = record.getThrown();
		// Small change here, to allow for short logging mode
		if (thr != null) {
			if (this.shortMode) {
				msg.append(" [" + thr.toString() + "]");
				msg.append(FileBase.LINE_SEP);
			}
			else {
				msg.append(FileBase.LINE_SEP);
				StringWriter thrDump = new StringWriter();
				thr.printStackTrace(new PrintWriter(thrDump));
				msg.append(thrDump.toString());
			}
		}
		else {
			msg.append(FileBase.LINE_SEP);
		}
		return msg.toString();
	}
	/**
	 * @return <code>true</code> if the year will be shown, <code>false</code> otherwise
	 */
	public final boolean showYear() {
		return (this.flags != 0) && ((this.flags & FLAG_YEAR) != 0);
	}
	/**
	 * @return <code>true</code> if the month will be shown, <code>false</code> otherwise
	 */
	public final boolean showMonth() {
		return (this.flags != 0) && ((this.flags & FLAG_MONTH) != 0);
	}
	/**
	 * @return <code>true</code> if the day will be shown, <code>false</code> otherwise
	 */
	public final boolean showDay() {
		return (this.flags != 0) && ((this.flags & FLAG_DAY) != 0);
	}
	/**
	 * @return <code>true</code> if any part of the date will be shown, <code>false</code> otherwise
	 */
	public final boolean showDateAny() {
		return showYear() || showMonth() || showDay();
	}
	/**
	 * @return <code>true</code> if the entire year (YYYY-MM-DD) will be shown, <code>false</code> otherwise
	 */
	public final boolean showFullDate() {
		return (this.flags != 0) && ((this.flags & FLAG_DATE_ALL) != 0);
	}
	/**
	 * @return <code>true</code> if the hour will be shown, <code>false</code> otherwise
	 */
	public final boolean showHour() {
		return (this.flags != 0) && ((this.flags & FLAG_HOUR) != 0);
	}
	/**
	 * @return <code>true</code> if the minute will be shown, <code>false</code> otherwise
	 */
	public final boolean showMinute() {
		return (this.flags != 0) && ((this.flags & FLAG_MINUTE) != 0);
	}
	/**
	 * @return <code>true</code> if the second will be shown, <code>false</code> otherwise
	 */
	public final boolean showSecond() {
		return (this.flags != 0) && ((this.flags & FLAG_SECOND) != 0);
	}
	/**
	 * @return <code>true</code> if the millisecond will be shown, <code>false</code> otherwise
	 */
	public final boolean showMilli() {
		return (this.flags != 0) && ((this.flags & FLAG_MILLISECOND) != 0);
	}
	/**
	 * @return <code>true</code> if any part of the time will be shown, <code>false</code> otherwise
	 */
	public final boolean showTimeAny() {
		return showHour() || showMinute() || showSecond() || showMilli();
	}
	/**
	 * @return <code>true</code> if the standard time (HH:MM:SS) will be shown, <code>false</code> otherwise
	 */
	public final boolean showStandardTime() {
		return (this.flags != 0) && ((this.flags & FLAG_TIME_STANDARD) != 0);
	}
	/**
	 * @return <code>true</code> if the full time (with milliseconds) will be shown, <code>false</code> otherwise
	 */
	public final boolean showFullTime() {
		return (this.flags != 0) && ((this.flags & FLAG_TIME_FULL) != 0);
	}
	/**
	 * @return <code>true</code> if the full date and standard time will be shown will be shown, <code>false</code> otherwise
	 */
	public final boolean showFullStamp() {
		return (this.flags != 0) && ((this.flags & FLAG_DATETIME_STANDARD) != 0);
	}
}
