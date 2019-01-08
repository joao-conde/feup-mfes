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
    VDMSet set_5 = MapUtil.rng(Utils.copy(accounts));
    for (Iterator iterator_5 = set_5.iterator(); iterator_5.hasNext(); ) {
      Account a = ((Account) iterator_5.next());
      setCompResult_2.add(MapUtil.rng(a.repositories));
    }
    VDMSet set_4 = SetUtil.dunion(Utils.copy(setCompResult_2));
    for (Iterator iterator_4 = set_4.iterator(); iterator_4.hasNext(); ) {
      Repository r = ((Repository) iterator_4.next());
      if (repoMatchesTags(r, Utils.copy(tags))) {
        setCompResult_1.add(r);
      }
    }
    return Utils.copy(setCompResult_1);
  }

  private VDMSet getUsers() {

    VDMSet setCompResult_3 = SetUtil.set();
    VDMSet set_7 = MapUtil.dom(Utils.copy(accounts));
    for (Iterator iterator_7 = set_7.iterator(); iterator_7.hasNext(); ) {
      String un = ((String) iterator_7.next());
      if (((Account) Utils.get(accounts, un)) instanceof User) {
        setCompResult_3.add(un);
      }
    }
    return Utils.copy(setCompResult_3);
  }

  public VDMSet stargazers(final Repository repo) {

    VDMSet setCompResult_4 = SetUtil.set();
    VDMSet set_10 = getUsers();
    for (Iterator iterator_10 = set_10.iterator(); iterator_10.hasNext(); ) {
      String un = ((String) iterator_10.next());
      if (SetUtil.inSet(repo, ((User) ((Account) Utils.get(accounts, un))).getStars())) {
        setCompResult_4.add(un);
      }
    }
    return Utils.copy(setCompResult_4);
  }

  private VDMSeq getAllRepos() {

    VDMSet reposSet = SetUtil.set();
    VDMSeq repos = SeqUtil.seq();
    for (Iterator iterator_26 = MapUtil.rng(Utils.copy(accounts)).iterator();
        iterator_26.hasNext();
        ) {
      Account acc = (Account) iterator_26.next();
      VDMSet setCompResult_7 = SetUtil.set();
      VDMSet set_14 = SetUtil.diff(MapUtil.rng(acc.repositories), Utils.copy(reposSet));
      for (Iterator iterator_14 = set_14.iterator(); iterator_14.hasNext(); ) {
        Repository r = ((Repository) iterator_14.next());
        setCompResult_7.add(r);
      }
      reposSet = SetUtil.union(Utils.copy(reposSet), Utils.copy(setCompResult_7));
    }
    for (Iterator iterator_27 = reposSet.iterator(); iterator_27.hasNext(); ) {
      Repository r = (Repository) iterator_27.next();
      repos = SeqUtil.conc(Utils.copy(repos), SeqUtil.seq(r));
    }
    return Utils.copy(repos);
  }

  public VDMSeq getTopRepos() {

    VDMSeq seqCompResult_2 = SeqUtil.seq();
    VDMSeq set_16 = getAllRepos();
    for (Iterator iterator_16 = set_16.iterator(); iterator_16.hasNext(); ) {
      Repository r = ((Repository) iterator_16.next());
      if (!(r.isRepoPrivate())) {
        seqCompResult_2.add(r);
      }
    }
    VDMSeq l = Utils.copy(seqCompResult_2);
    VDMSeq sorted_list = Utils.copy(l);
    long toVar_4 = 1L;
    long byVar_2 = -1L;
    for (Long i = (long) l.size(); byVar_2 < 0 ? i >= toVar_4 : i <= toVar_4; i += byVar_2) {
      long toVar_3 = i.longValue() - 1L;

      for (Long j = 1L; j <= toVar_3; j++) {
        if (stargazers(((Repository) Utils.get(sorted_list, j))).size()
            < stargazers(((Repository) Utils.get(sorted_list, j.longValue() + 1L))).size()) {
          Repository temp = ((Repository) Utils.get(sorted_list, j));
          Utils.mapSeqUpdate(
              sorted_list, j, ((Repository) Utils.get(sorted_list, j.longValue() + 1L)));
          Utils.mapSeqUpdate(sorted_list, j.longValue() + 1L, temp);
        }
      }
    }
    return Utils.copy(sorted_list);
  }

  public VDMSet searchRepos(final String query) {

    VDMSet setCompResult_9 = SetUtil.set();
    VDMSet set_19 = SeqUtil.elems(getAllRepos());
    for (Iterator iterator_19 = set_19.iterator(); iterator_19.hasNext(); ) {
      Repository r = ((Repository) iterator_19.next());
      Boolean andResult_9 = false;

      if (!(r.isRepoPrivate())) {
        if (VDMUtils.isSubstring(r.name, query).longValue() >= 0L) {
          andResult_9 = true;
        }
      }

      if (andResult_9) {
        setCompResult_9.add(r);
      }
    }
    
    System.out.println("Best match is " + ((Repository)setCompResult_9.iterator().next()).name);
    return Utils.copy(setCompResult_9);
  }

  public VDMSet searchAccounts(final String query) {

    VDMSet setCompResult_10 = SetUtil.set();
    VDMSet set_21 = MapUtil.dom(Utils.copy(accounts));
    for (Iterator iterator_21 = set_21.iterator(); iterator_21.hasNext(); ) {
      String acc = ((String) iterator_21.next());
      if (VDMUtils.isSubstring(acc, query).longValue() >= 0L) {
        setCompResult_10.add(((Account) Utils.get(accounts, acc)));
      }
    }
    return Utils.copy(setCompResult_10);
  }

  public static Boolean repoMatchesTags(final Repository r, final VDMSet tags) {

    throw new UnsupportedOperationException();
  }

  public String toString() {

    return "Github{" + "accounts := " + Utils.toString(accounts) + "}";
  }
}
