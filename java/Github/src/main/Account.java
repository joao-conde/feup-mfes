package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Account {
  public String username;
  public VDMMap repositories = MapUtil.map();
  private String description = SeqUtil.toStr(SeqUtil.seq());

  public void cg_init_Account_1(final String un) {

    username = un;
  }

  public Account(final String un) {

    cg_init_Account_1(un);
  }

  public String getDescription() {

    return description;
  }

  public void setDescription(final String desc) {

    description = desc;
  }

  public Repository newRepository(final String name, final Boolean isPriv) {

    {
      final Repository r = new Repository(name, this, isPriv);
      {
        Utils.mapSeqUpdate(repositories, name, r);
        return r;
      }
    }
  }

  public Account() {}

  public String toString() {

    return "Account{"
        + "username := "
        + Utils.toString(username)
        + ", repositories := "
        + Utils.toString(repositories)
        + ", description := "
        + Utils.toString(description)
        + "}";
  }
}
