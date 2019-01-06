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
    return "" + ((int)this.hour < 10 ? "0"+this.hour : this.hour) 
    		+ ":" + ((int)this.minute < 10 ? "0" + this.minute : this.minute) + " of " + 
    		((int)this.day < 10 ? "0" + this.day : this.day) + "/" 
    		+ ((int)this.month < 10 ? "0" + this.month : this.month) + "/" + this.year;
  }

  public static class x implements Record {
    public Date date;

    public x(final Date _date) {
 
      date = _date != null ? _date : null;
    }
    
    public boolean after(final x x) {
    	long year1 = (long) date.year, month1 = (long) date.month, day1 = (long) date.day;
    	long year2 = (long) x.date.year, month2 = (long) x.date.month, day2 = (long) x.date.day;
    	
    	if(year1 > year2) return true;
    	
    	if(year1 == year2 && month1 > month2) return true;
    	
    	if(year1 == year2 && month1 == month2 && day1 > day2) return true;
    	
    	return false;    	
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
