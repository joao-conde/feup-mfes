package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class VDMUtils {
  public static Number isSubstring(final String s, final String sub) {

    if (s.length() < sub.length()) {
      return -1L;
    }

    long toVar_5 = s.length() - sub.length() + 1L;

    for (Long i = 1L; i <= toVar_5; i++) {
      if (Utils.equals(SeqUtil.subSeq(s, i, i.longValue() + sub.length() - 1L), sub)) {
        return i;
      }
    }
    return -1L;
  }

  public VDMUtils() {}

  public static Number min(final VDMSet s) {

    throw new UnsupportedOperationException();
  }

  public static Number max(final VDMSet s) {

    throw new UnsupportedOperationException();
  }

  public static <T> Boolean isAscendingOrder(final VDMSeq s) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "VDMUtils{}";
  }
}
