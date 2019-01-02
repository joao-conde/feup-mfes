package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class cg_Utils {
  private VDMSeq bubbleSort(final VDMSeq l) {

    VDMSeq sorted_list = Utils.copy(l);
    long toVar_4 = 1L;
    long byVar_2 = -1L;
    for (Long i = (long) l.size(); byVar_2 < 0 ? i >= toVar_4 : i <= toVar_4; i += byVar_2) {
      long toVar_3 = i.longValue() - 1L;

      for (Long j = 1L; j <= toVar_3; j++) {
        if (((Number) Utils.get(sorted_list, j)).longValue()
            > ((Number) Utils.get(sorted_list, j.longValue() + 1L)).longValue()) {
          Number temp = ((Number) Utils.get(sorted_list, j));
          Utils.mapSeqUpdate(sorted_list, j, ((Number) Utils.get(sorted_list, j.longValue() + 1L)));
          Utils.mapSeqUpdate(sorted_list, j.longValue() + 1L, temp);
        }
      }
    }
    return Utils.copy(sorted_list);
  }

  public cg_Utils() {}

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

    return "cg_Utils{}";
  }
}
