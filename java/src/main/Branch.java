package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Branch {
  public String name;
  public Boolean isProtected;
  private VDMSeq commits = SeqUtil.seq();

  public void cg_init_Branch_1(final String n, final Boolean prot) {

    name = n;
    isProtected = prot;
    return;
  }

  public Branch(final String n, final Boolean prot) {

    cg_init_Branch_1(n, prot);
  }

  public void commit(final Commit c) {

    commits = SeqUtil.conc(Utils.copy(commits), SeqUtil.seq(c));
  }

  public VDMSeq getCommits() {

    return Utils.copy(commits);
  }

  public Branch() {}

  public String toString() {

    return "Branch{"
        + "name := "
        + Utils.toString(name)
        + ", isProtected := "
        + Utils.toString(isProtected)
        + ", commits := "
        + Utils.toString(commits)
        + "}";
  }
}
