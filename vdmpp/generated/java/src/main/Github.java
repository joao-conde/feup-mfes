package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Github {
  public VDMMap accounts = MapUtil.map();

  public void cg_init_Github_1() {

    return;
  }

  public Github() {

    cg_init_Github_1();
  }

  public void addAccount(final Account acc) {

    Utils.mapSeqUpdate(accounts, acc.username, acc);
  }

  public Number numAccounts() {

    return MapUtil.dom(Utils.copy(accounts)).size();
  }

  public VDMSet getRepositoriesByTags(final VDMSet tags) {

    VDMSet setCompResult_1 = SetUtil.set();
    VDMSet setCompResult_2 = SetUtil.set();
    VDMSet set_2 = MapUtil.rng(Utils.copy(accounts));
    for (Iterator iterator_2 = set_2.iterator(); iterator_2.hasNext(); ) {
      Account a = ((Account) iterator_2.next());
      setCompResult_2.add(MapUtil.rng(a.repositories));
    }
    VDMSet set_1 = SetUtil.dunion(Utils.copy(setCompResult_2));
    for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext(); ) {
      Repository r = ((Repository) iterator_1.next());
      if (repoMatchesTags(r, Utils.copy(tags))) {
        setCompResult_1.add(r);
      }
    }
    return Utils.copy(setCompResult_1);
  }

  private VDMSet getUsers() {

    VDMSet setCompResult_3 = SetUtil.set();
    VDMSet set_4 = MapUtil.dom(Utils.copy(accounts));
    for (Iterator iterator_4 = set_4.iterator(); iterator_4.hasNext(); ) {
      String un = ((String) iterator_4.next());
      if (((Account) Utils.get(accounts, un)) instanceof User) {
        setCompResult_3.add(un);
      }
    }
    return Utils.copy(setCompResult_3);
  }

  public VDMSet stargazers(final Repository repo) {

    VDMSet setCompResult_4 = SetUtil.set();
    VDMSet set_7 = getUsers();
    for (Iterator iterator_7 = set_7.iterator(); iterator_7.hasNext(); ) {
      String un = ((String) iterator_7.next());
      if (SetUtil.inSet(repo, ((User) ((Account) Utils.get(accounts, un))).getStars())) {
        setCompResult_4.add(un);
      }
    }
    return Utils.copy(setCompResult_4);
  }

  private VDMSeq getAllRepos() {

    VDMSet reposSet = SetUtil.set();
    VDMSeq repos = SeqUtil.seq();
    for (Iterator iterator_12 = MapUtil.rng(Utils.copy(accounts)).iterator();
        iterator_12.hasNext();
        ) {
      Account acc = (Account) iterator_12.next();
      VDMSet setCompResult_5 = SetUtil.set();
      VDMSet set_8 = SetUtil.diff(MapUtil.rng(acc.repositories), Utils.copy(reposSet));
      for (Iterator iterator_8 = set_8.iterator(); iterator_8.hasNext(); ) {
        Repository r = ((Repository) iterator_8.next());
        setCompResult_5.add(r);
      }
      reposSet = SetUtil.union(Utils.copy(reposSet), Utils.copy(setCompResult_5));
    }
    for (Iterator iterator_13 = reposSet.iterator(); iterator_13.hasNext(); ) {
      Repository r = (Repository) iterator_13.next();
      repos = SeqUtil.conc(Utils.copy(repos), SeqUtil.seq(r));
    }
    return Utils.copy(repos);
  }

  public VDMSeq getTopRepos() {

    VDMSeq l = getAllRepos();
    VDMSeq sorted_list = Utils.copy(l);
    long toVar_2 = 1L;
    long byVar_1 = -1L;
    for (Long i = l.size(); byVar_1 < 0 ? i >= toVar_2 : i <= toVar_2; i += byVar_1) {
      long toVar_1 = i.longValue() - 1L;

      for (Long j = 1L; j <= toVar_1; j++) {
        if (stargazers(((Repository) Utils.get(sorted_list, j))).size()
            > stargazers(((Repository) Utils.get(sorted_list, j.longValue() + 1L))).size()) {
          Repository temp = ((Repository) Utils.get(sorted_list, j));
          Utils.mapSeqUpdate(
              sorted_list, j, ((Repository) Utils.get(sorted_list, j.longValue() + 1L)));
          Utils.mapSeqUpdate(sorted_list, j.longValue() + 1L, temp);
        }
      }
    }
    return Utils.copy(sorted_list);
  }

  public static Boolean repoMatchesTags(final Repository r, final VDMSet tags) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "Github{" + "accounts := " + Utils.toString(accounts) + "}";
  }
}
