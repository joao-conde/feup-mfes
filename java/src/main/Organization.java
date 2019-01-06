package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Organization extends Account {
  public VDMSet members = SetUtil.set();

  public void cg_init_Organization_1(final String un) {

    cg_init_Account_1(un);
  }

  public Organization(final String un) {

    cg_init_Organization_1(un);
  }

  public void addMember(final Organization org, final User u) {

    members = SetUtil.union(Utils.copy(members), SetUtil.set(u));
  }

  public Number numMembers() {

    return members.size();
  }

  public Organization() {}

  public String toString() {

    return "Organization{" + "members := " + Utils.toString(members) + "}";
  }
}
