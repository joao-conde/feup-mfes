package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Date {
  public static final Number startYear = 2000L;
  private Number year;
  private Number month;
  private Number day;
  private Number hour;
  private Number minute;

  public void cg_init_Date_1(
      final Number y, final Number mo, final Number d, final Number h, final Number mi) {

    year = y;
    month = mo;
    day = d;
    hour = h;
    minute = mi;
    return;
  }

  public Date(final Number y, final Number mo, final Number d, final Number h, final Number mi) {

    cg_init_Date_1(y, mo, d, h, mi);
  }

  public Date() {}

  public static Boolean isLeapYear(final Number y) {

    throw new UnsupportedOperationException();
  }

  public static Number daysOfMonth(final Number y, final Number m) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "Date{"
        + "startYear = "
        + Utils.toString(startYear)
        + ", year := "
        + Utils.toString(year)
        + ", month := "
        + Utils.toString(month)
        + ", day := "
        + Utils.toString(day)
        + ", hour := "
        + Utils.toString(hour)
        + ", minute := "
        + Utils.toString(minute)
        + "}";
  }

  public static class x implements Record {
    public Date date;

    public x(final Date _date) {

      date = _date != null ? _date : null;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof x)) {
        return false;
      }

      x other = ((x) obj);

      return Utils.equals(date, other.date);
    }

    public int hashCode() {

      return Utils.hashCode(date);
    }

    public x copy() {

      return new x(date);
    }

    public String toString() {

      return "mk_Date`x" + Utils.formatFields(date);
    }
  }
}
