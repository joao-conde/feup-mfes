package main;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class User extends Account {
  private User selfUser;
  private VDMSet followers = SetUtil.set();
  private VDMSet following = SetUtil.set();
  private VDMSet stars = SetUtil.set();

  public void cg_init_User_1(final String un) {

    selfUser = this;
    cg_init_Account_1(un);
  }

  public User(final String un) {

    cg_init_User_1(un);
  }

  private void addFollower(final User follower) {

    followers = SetUtil.union(Utils.copy(followers), SetUtil.set(follower));
  }

  private void removeFollower(final User us) {

    followers = SetUtil.diff(Utils.copy(followers), SetUtil.set(us));
  }

  public void follow(final User us) {

    following = SetUtil.union(Utils.copy(following), SetUtil.set(us));
    us.addFollower(this);
  }

  public void unfollow(final User us) {

    following = SetUtil.diff(Utils.copy(following), SetUtil.set(us));
    us.removeFollower(this);
  }

  public void clearFollowing() {

    for (Iterator iterator_14 = following.iterator(); iterator_14.hasNext(); ) {
      User us = (User) iterator_14.next();
      unfollow(us);
    }
  }

  public void star(final Repository repo) {

    stars = SetUtil.union(Utils.copy(stars), SetUtil.set(repo));
  }

  public void unstar(final Repository repo) {

    stars = SetUtil.diff(Utils.copy(stars), SetUtil.set(repo));
  }

  public VDMSet getFollowing() {

    return Utils.copy(following);
  }

  public VDMSet getFollowers() {

    return Utils.copy(followers);
  }

  public VDMSet getStars() {

    return Utils.copy(stars);
  }

  public User() {}

  public String toString() {

    return "User{"
        + "selfUser := "
        + Utils.toString(selfUser)
        + ", followers := "
        + Utils.toString(followers)
        + ", following := "
        + Utils.toString(following)
        + ", stars := "
        + Utils.toString(stars)
        + "}";
  }
}
