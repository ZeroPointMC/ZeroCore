package zeropoint.core.date;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Datetime wraps a Date object and makes it easy to get the time it represents in various formats.
 * 
 * @author Zero Point
 */
public class Datetime implements Cloneable {
	public static final String FMT_YEAR_LONG = "YYYY";
	public static final String FMT_YEAR_SHORT = "YY";
	public static final String FMT_MONTH_NUM = "MM";
	public static final String FMT_MONTH_NAME_SHORT = "MMM";
	public static final String FMT_MONTH_NAME_LONG = "MMMM";
	public static final String FMT_DAY_NUM = "dd";
	public static final String FMT_DAY_NAME_SHORT = "EEE";
	public static final String FMT_DAY_NAME_LONG = "EEEE";
	public static final String FMT_AM_PM = "a";
	public static final String FMT_HOUR_24 = "HH";
	public static final String FMT_HOUR_12 = "hh";
	public static final String FMT_MINUTE = "mm";
	public static final String FMT_SECOND = "ss";
	public static final String FMT_MILLISECOND = "SS";
	public static final String FMT_TIMEZONE_LONG = "zzzz";
	public static final String FMT_TIMEZONE_SHORT = "zzz";
	public static final String FMT_TIMEZONE_OFFSET = "ZZZZ";
	protected Date date;
	public Datetime() {
		date = new Date();
	}
	public Datetime(Date init) {
		date = new Date(init.getTime());
	}
	public Datetime(Datetime init) {
		date = new Date(init.longTime());
	}
	public boolean am() {
		return format(FMT_AM_PM).equalsIgnoreCase("am");
	}
	public String ampm() {
		return format(FMT_AM_PM);
	}
	@Override
	public Datetime clone() {
		return new Datetime(this);
	}
	public String dateStamp() {
		return format(FMT_YEAR_LONG + FMT_MONTH_NUM + FMT_DAY_NUM);
	}
	public int day() {
		return Integer.valueOf(format(FMT_DAY_NUM));
	}
	public String dayName() {
		return format(FMT_DAY_NAME_SHORT);
	}
	public String dayNameLong() {
		return format(FMT_DAY_NAME_LONG);
	}
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
	public String format(String fmt) {
		return new SimpleDateFormat(fmt).format(date);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
		return result;
	}
	public int hour() {
		return Integer.valueOf(format(FMT_HOUR_24));
	}
	public int hour12() {
		return Integer.valueOf(format(FMT_HOUR_12));
	}
	public String isoDate() {
		return format(FMT_YEAR_LONG + "-" + FMT_MONTH_NUM + "-" + FMT_DAY_NUM);
	}
	public String isoDatetime() {
		return isoDate() + "T" + isoTime();
	}
	public String isoTime() {
		return format(FMT_HOUR_24 + ":" + FMT_MINUTE + ":" + FMT_SECOND + FMT_TIMEZONE_OFFSET);
	}
	public long longTime() {
		return date.getTime();
	}
	public void longTime(long newTime) {
		date.setTime(newTime);
	}
	public int millisecond() {
		return Integer.valueOf(format(FMT_MILLISECOND));
	}
	public int minute() {
		return Integer.valueOf(format(FMT_MINUTE));
	}
	public int month() {
		return Integer.valueOf(format(FMT_MONTH_NUM));
	}
	public String monthName() {
		return format(FMT_MONTH_NAME_SHORT);
	}
	public String monthNameLong() {
		return format(FMT_MONTH_NAME_LONG);
	}
	public String monthString() {
		return format(FMT_MONTH_NUM);
	}
	public int offset() {
		return Integer.valueOf(format(FMT_TIMEZONE_OFFSET));
	}
	public boolean pm() {
		return format(FMT_AM_PM).equalsIgnoreCase("pm");
	}
	public int second() {
		return Integer.valueOf(format(FMT_SECOND));
	}
	public int shortYear() {
		return Integer.valueOf(format(FMT_YEAR_SHORT));
	}
	public String time() {
		return time(true, false);
	}
	public String time(boolean use24) {
		return time(use24, false);
	}
	protected String time(boolean use24, boolean milli) {
		return format((use24 ? FMT_HOUR_24 : FMT_HOUR_12) + ":" + FMT_MINUTE + ":" + FMT_SECOND + (milli ? "." + FMT_MILLISECOND : "") + (use24 ? "" : " " + FMT_AM_PM));
	}
	public String timePrecise() {
		return time(true, true);
	}
	public String timePrecise(boolean use24) {
		return time(use24, true);
	}
	public String timezone() {
		return format(FMT_TIMEZONE_SHORT);
	}
	public String timezoneLong() {
		return format(FMT_TIMEZONE_LONG);
	}
	@Override
	public String toString() {
		return date.toString();
	}
	public void update() {
		date = new Date();
	}
	public void update(Date init) {
		date = new Date(init.getTime());
	}
	public void update(long init) {
		date = new Date(init);
	}
	public int year() {
		return Integer.valueOf(format(FMT_YEAR_LONG));
	}
	public String yearString() {
		return format(FMT_YEAR_LONG);
	}
}
