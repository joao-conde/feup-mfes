package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Release {
  public String name = SeqUtil.toStr(SeqUtil.seq());
  public Date timestamp;

  public void cg_init_Release_1(final String n, final Date date) {

    name = n;
    timestamp = date;
    return;
  }

  public Release(final String n, final Date date) {

    cg_init_Release_1(n, date);
  }

  public Release() {}

  public String toString() {

    return "Release{"
        + "name := "
        + Utils.toString(name)
        + ", timestamp := "
        + Utils.toString(timestamp)
        + "}";
  }
}
