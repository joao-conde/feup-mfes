package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Repository {
  public String name;
  private Boolean isPrivate;
  private String description = SeqUtil.toStr(SeqUtil.seq());
  private Account owner;
  private Branch defaultBranch;
  public VDMSet tags = SetUtil.set();
  public VDMSet collaborators = SetUtil.set();
  public VDMSeq releases = SeqUtil.seq();
  public VDMMap branches = MapUtil.map();
  public VDMMap issues = MapUtil.map();

  public void cg_init_Repository_1(final String n, final Account acc, final Boolean priv) {

    name = n;
    isPrivate = priv;
    owner = acc;
    if (owner instanceof User) {
      collaborators = SetUtil.set(owner);
    }

    {
      final Branch master = new Branch("master", true);
      {
        defaultBranch = master;
        branches = MapUtil.map(new Maplet("master", master));
      }
    }

    return;
  }

  public Repository(final String n, final Account acc, final Boolean priv) {

    cg_init_Repository_1(n, acc, priv);
  }

  public void addRelease(final Account acc, final Release rel) {

    releases = SeqUtil.conc(Utils.copy(releases), SeqUtil.seq(rel));
  }

  public void addTag(final Account acc, final Tag tag) {

    tags = SetUtil.union(Utils.copy(tags), SetUtil.set(tag));
  }

  public Branch createBranch(final String n, final Boolean prot) {

    {
      final Branch b = new Branch(n, prot);
      {
        Utils.mapSeqUpdate(branches, n, b);
        return b;
      }
    }
  }

  public void delBranch(final User usr, final String n) {

    branches = MapUtil.domResBy(SetUtil.set(n), Utils.copy(branches));
  }

  public void commit(final User usr, final String branchName, final String hash, final Date date) {

    ((Branch) Utils.get(branches, branchName)).commit(new Commit(hash, usr, date));
  }

  public void addCollaborator(final Account acc, final User usr) {

    collaborators = SetUtil.union(Utils.copy(collaborators), SetUtil.set(usr));
  }

  public void mergeBranches(
      final User usr, final String dest, final String src, final Boolean delete) {

    ((Branch) Utils.get(branches, dest)).mergeBranch(((Branch) Utils.get(branches, src)));
    if (delete) {
      delBranch(usr, src);
    }
  }

  public void addIssue(final Account acc, final Issue issue) {

    Utils.mapSeqUpdate(issues, issue.title, issue);
  }

  public Branch getDefaultBranch() {

    return defaultBranch;
  }

  public String getDescription() {

    return description;
  }

  public String getOwner() {

    return owner.username;
  }

  public Number numReleases() {

    return releases.size();
  }

  public Boolean isRepoPrivate() {

    return isPrivate;
  }

  public void setDefaultBranch(final Account acc, final String bName) {

    defaultBranch = ((Branch) Utils.get(branches, bName));
  }

  public void setDescription(final Account acc, final String desc) {

    description = desc;
  }

  public void setPrivacy(final Account acc, final Boolean privacy) {

    isPrivate = privacy;
  }

  public Repository() {}

  public String toString() {

    return "Repository{"
        + "name := "
        + Utils.toString(name)
        + ", isPrivate := "
        + Utils.toString(isPrivate)
        + ", description := "
        + Utils.toString(description)
        + ", owner := "
        + Utils.toString(owner)
        + ", defaultBranch := "
        + Utils.toString(defaultBranch)
        + ", tags := "
        + Utils.toString(tags)
        + ", collaborators := "
        + Utils.toString(collaborators)
        + ", releases := "
        + Utils.toString(releases)
        + ", branches := "
        + Utils.toString(branches)
        + ", issues := "
        + Utils.toString(issues)
        + "}";
  }
}
