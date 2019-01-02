package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Commit {
  public String hash;
  public Date timestamp;
  public User author;

  public void cg_init_Commit_1(final String str, final User u, final Date d) {

    hash = str;
    author = u;
    timestamp = d;
    return;
  }

  public Commit(final String str, final User u, final Date d) {

    cg_init_Commit_1(str, u, d);
  }

  public Commit() {}

  public String toString() {

    return "Commit{"
        + "hash := "
        + Utils.toString(hash)
        + ", timestamp := "
        + Utils.toString(timestamp)
        + ", author := "
        + Utils.toString(author)
        + "}";
  }
}
