package zeropoint.core.date;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Datetime wraps a Date object and makes it easy to get the time it represents in various formats.
 * 
 * @author Zero Point
 */
public class Datetime implements Cloneable {
	/**
	 * The {@link SimpleDateFormat} code for the four-digit year
	 */
	public static final String FMT_YEAR_LONG = "YYYY";
	/**
	 * The {@link SimpleDateFormat} code for the two-digit year
	 */
	public static final String FMT_YEAR_SHORT = "YY";
	/**
	 * The {@link SimpleDateFormat} code for the month number
	 */
	public static final String FMT_MONTH_NUM = "MM";
	/**
	 * The {@link SimpleDateFormat} code for the small name of the month
	 */
	public static final String FMT_MONTH_NAME_SHORT = "MMM";
	/**
	 * The {@link SimpleDateFormat} code for the full name of the month
	 */
	public static final String FMT_MONTH_NAME_LONG = "MMMM";
	/**
	 * The {@link SimpleDateFormat} code for the number of the day
	 */
	public static final String FMT_DAY_NUM = "dd";
	/**
	 * The {@link SimpleDateFormat} code for the short day name
	 */
	public static final String FMT_DAY_NAME_SHORT = "EEE";
	/**
	 * The {@link SimpleDateFormat} code for the long name of the day
	 */
	public static final String FMT_DAY_NAME_LONG = "EEEE";
	/**
	 * The {@link SimpleDateFormat} code for the am/pm indicator
	 */
	public static final String FMT_AM_PM = "a";
	/**
	 * The {@link SimpleDateFormat} code for the hour in 24 hour format
	 */
	public static final String FMT_HOUR_24 = "HH";
	/**
	 * The {@link SimpleDateFormat} code for the hour in 12 hour format
	 */
	public static final String FMT_HOUR_12 = "hh";
	/**
	 * The {@link SimpleDateFormat} code for the minute
	 */
	public static final String FMT_MINUTE = "mm";
	/**
	 * The {@link SimpleDateFormat} code for the second
	 */
	public static final String FMT_SECOND = "ss";
	/**
	 * The {@link SimpleDateFormat} code for the millisecond
	 */
	public static final String FMT_MILLISECOND = "SS";
	/**
	 * The {@link SimpleDateFormat} code for the long representation of the timezone
	 */
	public static final String FMT_TIMEZONE_LONG = "zzzz";
	/**
	 * The {@link SimpleDateFormat} code for the short timezone representation
	 */
	public static final String FMT_TIMEZONE_SHORT = "zzz";
	/**
	 * The {@link SimpleDateFormat} code for the timezone offset
	 */
	public static final String FMT_TIMEZONE_OFFSET = "ZZZZ";
	/**
	 * The {@link Date} object containing the time we represent
	 */
	protected Date date;
	/**
	 * Create a new Datetime object representing the time when the object was created
	 */
	public Datetime() {
		this.date = new Date();
	}
	/**
	 * Create a new Datetime object representing the given date/time
	 * 
	 * @param init
	 *            - the {@link Date} to use
	 */
	public Datetime(Date init) {
		this.date = new Date(init.getTime());
	}
	/**
	 * Copy a Datetime
	 * 
	 * @param init
	 *            - the Datetime to copy
	 */
	public Datetime(Datetime init) {
		this.date = new Date(init.longTime());
	}
	/**
	 * @return <code>true</code> if it's after noon, <code>false</code> otherwise
	 */
	public boolean pm() {
		return format(FMT_AM_PM).equalsIgnoreCase("pm");
	}
	/**
	 * @return <code>true</code> if it's before noon, <code>false</code> otherwise
	 */
	public boolean am() {
		return format(FMT_AM_PM).equalsIgnoreCase("am");
	}
	/**
	 * @return string indicating am/pm
	 */
	public String ampm() {
		return format(FMT_AM_PM);
	}
	@Override
	public Datetime clone() {
		return new Datetime(this);
	}
	/**
	 * @return a string representing the day
	 */
	public String dateStamp() {
		return format(FMT_YEAR_LONG + FMT_MONTH_NUM + FMT_DAY_NUM);
	}
	/**
	 * @return the day number
	 */
	public int day() {
		return Integer.valueOf(format(FMT_DAY_NUM));
	}
	/**
	 * @return the short day name
	 */
	public String dayName() {
		return format(FMT_DAY_NAME_SHORT);
	}
	/**
	 * @return the long day name
	 */
	public String dayNameLong() {
		return format(FMT_DAY_NAME_LONG);
	}
	/**
	 * @return the day number as a <code>String</code>
	 */
	public String dayString() {
		return format(FMT_DAY_NUM);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if ( !(obj instanceof Datetime)) {
			return false;
		}
		Datetime other = (Datetime) obj;
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		}
		else if ( !this.date.equals(other.date)) {
			return false;
		}
		return true;
	}
	/**
	 * Format the date/time according to the given string
	 * 
	 * @param fmt
	 *            - the format for the date/time
	 * @return the formatted date/time
	 */
	public String format(String fmt) {
		return new SimpleDateFormat(fmt).format(this.date);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
		return result;
	}
	/**
	 * @return the current hour, in twenty-four hour time
	 */
	public int hour() {
		return Integer.valueOf(format(FMT_HOUR_24));
	}
	/**
	 * @return the current hour, in twelve hour time
	 */
	public int hour12() {
		return Integer.valueOf(format(FMT_HOUR_12));
	}
	/**
	 * @return the date as formatted by ISO standard
	 */
	public String isoDate() {
		return format(FMT_YEAR_LONG + "-" + FMT_MONTH_NUM + "-" + FMT_DAY_NUM);
	}
	/**
	 * @return the date and time as formatted by ISO standard
	 */
	public String isoDatetime() {
		return isoDate() + "T" + isoTime();
	}
	/**
	 * @return the time as formatted by ISO standard
	 */
	public String isoTime() {
		return format(FMT_HOUR_24 + ":" + FMT_MINUTE + ":" + FMT_SECOND + FMT_TIMEZONE_OFFSET);
	}
	/**
	 * @return the timestamp as a <code>long</code> representing seconds since the epoch
	 */
	public long longTime() {
		return this.date.getTime();
	}
	/**
	 * @return the current milliseconds
	 */
	public int millisecond() {
		return Integer.valueOf(format(FMT_MILLISECOND));
	}
	/**
	 * @return the current minute
	 */
	public int minute() {
		return Integer.valueOf(format(FMT_MINUTE));
	}
	/**
	 * @return the current month number as an <code>int</code>
	 */
	public int month() {
		return Integer.valueOf(format(FMT_MONTH_NUM));
	}
	/**
	 * @return the short name of the current month
	 */
	public String monthName() {
		return format(FMT_MONTH_NAME_SHORT);
	}
	/**
	 * @return the long name of the current month
	 */
	public String monthNameLong() {
		return format(FMT_MONTH_NAME_LONG);
	}
	/**
	 * @return the current month number as a <code>String</code>
	 */
	public String monthString() {
		return format(FMT_MONTH_NUM);
	}
	/**
	 * @return the GMT offset for the current timezone
	 */
	public int offset() {
		return Integer.valueOf(format(FMT_TIMEZONE_OFFSET));
	}
	/**
	 * @return the current second
	 */
	public int second() {
		return Integer.valueOf(format(FMT_SECOND));
	}
	/**
	 * @return the short representation of the current year
	 */
	public int shortYear() {
		return Integer.valueOf(format(FMT_YEAR_SHORT));
	}
	/**
	 * Get the precise time, including milliseconds
	 * 
	 * @return the time, not including milliseconds
	 */
	public String time() {
		return time(true, false);
	}
	/**
	 * Get the current time, not including milliseconds
	 * 
	 * @param use24
	 *            - whether or not to use twenty-four hour time in the returned string
	 * @return the time, not including milliseconds
	 */
	public String time(boolean use24) {
		return time(use24, false);
	}
	/**
	 * Get the current time, in HH:MM:SS format
	 * 
	 * @param use24
	 *            - whether to use twenty-four hour time
	 * @param milli
	 *            - whether to append milliseconds
	 * @return the formatted time represented by this object
	 */
	protected String time(boolean use24, boolean milli) {
		return format((use24 ? FMT_HOUR_24 : FMT_HOUR_12) + ":" + FMT_MINUTE + ":" + FMT_SECOND + (milli ? "." + FMT_MILLISECOND : "") + (use24 ? "" : " " + FMT_AM_PM));
	}
	/**
	 * @return the current time, in twenty-four hour format, with milliseconds
	 */
	public String timePrecise() {
		return time(true, true);
	}
	/**
	 * Get the precise time, including milliseconds
	 * 
	 * @param use24
	 *            - whether or not to use twenty-four hour time in the returned string
	 * @return the time, including milliseconds
	 */
	public String timePrecise(boolean use24) {
		return time(use24, true);
	}
	/**
	 * @return the short version of the current timezone
	 */
	public String timezone() {
		return format(FMT_TIMEZONE_SHORT);
	}
	/**
	 * @return the long version of the current timezone
	 */
	public String timezoneLong() {
		return format(FMT_TIMEZONE_LONG);
	}
	@Override
	public String toString() {
		return this.date.toString();
	}
	/**
	 * Use the current time
	 */
	public void update() {
		this.date = new Date();
	}
	/**
	 * @param init
	 *            - the new Date to use
	 */
	public void update(Date init) {
		this.date = new Date(init.getTime());
	}
	/**
	 * @param time
	 *            - the new time to use
	 */
	public void update(long time) {
		this.date = new Date(time);
	}
	/**
	 * @return the year as an <code>int</code>
	 */
	public int year() {
		return Integer.valueOf(format(FMT_YEAR_LONG));
	}
	/**
	 * @return the year as a <code>String</code>
	 */
	public String yearString() {
		return format(FMT_YEAR_LONG);
	}
}
