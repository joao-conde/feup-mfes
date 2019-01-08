package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Tag {
  public String name;

  public void cg_init_Tag_1(final String tag) {

    name = tag;
    return;
  }

  public Tag(final String tag) {

    cg_init_Tag_1(tag);
  }

  public Tag() {}

  public String toString() {

    return "Tag{" + "name := " + Utils.toString(name) + "}";
  }
}
