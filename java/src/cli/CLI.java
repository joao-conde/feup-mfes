package cli;

import java.util.Iterator;
import java.util.Scanner;

import main.*;
import org.overture.codegen.runtime.*;

public class CLI {

	private Github gh;
	private User user = null; // logged in user

	private Boolean running = true;
	private CLIState state = CLIState.MAIN_MENU;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		CLI cli = new CLI();
		cli.populateDB();
		cli.run();
	}

	public void populateDB() {
		this.gh = new Github();
		User jc = new User("jc"), andre = new User("andre"), ed = new User("ed");
		gh.addAccount(jc);
		gh.addAccount(andre);
		gh.addAccount(ed);

		jc.follow(andre);
		ed.follow(andre);
		andre.follow(ed);

		jc.newRepository("why-python-rocks", false);
		andre.newRepository("feup-mfes", false);
		ed.newRepository("frontend-shenanigans", true);

		andre.star((Repository) gh.searchRepos("feup-mfes").iterator().next());

		((Repository) gh.searchRepos("feup-mfes").iterator().next()).addTag(new Tag("tag1"));
		((Repository) gh.searchRepos("why-python-rocks").iterator().next()).addTag(new Tag("tag1"));
		((Repository) gh.searchRepos("why-python-rocks").iterator().next()).addTag(new Tag("tag2"));

	}

	public void run() {
		while (running) {
			switch (this.state) {
			case MAIN_MENU:
				mainMenu();
				break;

			case LOGGED:
				loggedInMenu();
				break;

			case EXIT:
				displayExitMsg();
				running = false;
			}
			requestEnter();
			cls();
		}
	}

	private void displayExitMsg() {
		System.out.println("\nThank you for using GitHub, see you soon");
	}

	private void displayWelcomeBanner() {
		System.out.println("==================================================");
		System.out.println("\t\tWelcome to GitHub!\t\t");
		System.out.println("==================================================");
		System.out.println("Select an option");
	}

	private void displayLoggedBanner() {
		System.out.println("==================================================");
		System.out.println("\t\tLogged in as " + this.user.username + "\t\t");
		System.out.println("==================================================");
		System.out.println("Select an option");
	}

	private void mainMenu() {
		displayWelcomeBanner();
		displayMainMenuOpts();
		int opt = readUserInputOpt(1, 5); // TODO change to options
		if (opt != -1)
			processMainMenuOpt(opt);
	}

	private void displayMainMenuOpts() {
		System.out.println("1 - SignIn");
		System.out.println("2 - SignUp");
		System.out.println("3 - View public repositories ranked by rating");
		System.out.println("4 - View public repositories filtered by a set of tags");
		System.out.println("5 - View stargazers of a repository");
	}

	private void processMainMenuOpt(int opt) {
		switch (opt) {
		case 1:
			signIn();
			break;
		case 2:
			signUp();
			break;
		case 3:
			viewTopRepos();
			break;
		case 4:
			viewReposByTags();
			break;
		case 5:
			viewReposStargazers();
			break;
		default:
			System.out.println("Invalid option");
		}
	}

	private void loggedInMenu() {
		displayLoggedBanner();
		displayLoggedInMenuOpts();
		int opt = readUserInputOpt(1, 100); // TODO change to options
		if (opt != -1)
			processLoggedInMenuOpt(opt);
	}

	private void displayLoggedInMenuOpts() {
		System.out.println("1 - SignOut");
		System.out.println("2 - View public repositories ranked by rating");
		System.out.println("3 - View public repositories filtered by a set of tags");
		System.out.println("4 - View stargazers of a repository");
		System.out.println("5 - Follow an user");
		System.out.println("6 - Unfollow an user");
		System.out.println("7 - Unfollow all users");
		System.out.println("8 - View who I follow");
		System.out.println("9 - View my followers");
		System.out.println("10 - View my stars");
		System.out.println("11 - Star a repository");
		System.out.println("12 - Unstar a repository");
		System.out.println("13 - Create Repository");
		System.out.println("14 - My Repositories");
		System.out.println("15 - View my bio");
		System.out.println("16 - Edit my bio");
		System.out.println("17 - Create Organization");
		System.out.println("18 - Search for users or organizations");
		System.out.println("19 - View my organizations");
	}

	private void processLoggedInMenuOpt(int opt) {
		switch (opt) {
		case 1:
			signOut();
			break;
		case 2:
			viewTopRepos();
			break;
		case 3:
			viewReposByTags();
			break;
		case 4:
			viewReposStargazers();
			break;
		case 5:
			followUser();
			break;
		case 6:
			unfollowUser();
			break;
		case 7:
			unfollowAllUsers();
			break;
		case 8:
			viewFollowing();
			break;
		case 9:
			viewFollowers();
			break;
		case 10:
			viewMyStars();
			break;
		case 11:
			starRepos();
			break;
		case 12:
			unstarRepos();
			break;
		case 13:
			createRepos();
			break;
		case 14:
			viewMyRepositories();
			break;
		case 15:
			viewMyBio();
			break;
		case 16:
			editMyBio();
			break;
		case 17:
			createOrganization();
			break;
		case 18:
			searchForAccounts();
			break;
		case 19:
			viewMyOrgs();
			break;
		case 20:
			addMemberToOrg();
			break;
		/*
		 * case 21: viewRepository(); break;
		 */
		default:
			System.out.println("Invalid option");
		}

	}

	@SuppressWarnings("unused")
	private void signIn() {
		String username = readNonEmptyString("\nUsername: ");
		VDMSet usersFound = gh.searchAccounts(username);
		if (!usersFound.isEmpty()) {
			this.user = (User) usersFound.iterator().next();
			this.state = CLIState.LOGGED;
			System.out.println("\nLogged in as " + username);
		} else
			System.out.println("\nUser " + username + " not found");
	}

	private void signOut() {
		this.user = null;
		this.state = CLIState.MAIN_MENU;
	}

	private void signUp() {
		int prev = (int) gh.numAccounts();
		String username = readNonEmptyString("Username: ");

		gh.addAccount(new User(username));

		if (prev + 1 == (int) gh.numAccounts())
			System.out.println("User " + username + " successfully added");
		else
			System.out.println("User " + username + " already in use, specify a different one");
	}

	private void createOrganization() {
		String org = readNonEmptyString("Organization name: ");
		gh.addAccount(new Organization(org));
		System.out.println("Organization " + org + " successfully created");
	}

	@SuppressWarnings("unchecked")
	private void viewTopRepos() {
		VDMSeq repos = gh.getTopRepos();
		if (!repos.isEmpty()) {
			System.out.println("Top Repositories");
			Iterator<Repository> ite = repos.iterator();
			while (ite.hasNext()) {
				printRepository(ite.next());
			}
		} else
			System.out.println("No repositories");
	}

	@SuppressWarnings("unchecked")
	private void viewReposByTags() {
		VDMSet tags = new VDMSet();
		System.out.println("Add a tag (blank to stop adding): ");
		while (true) {
			String t = scanner.nextLine();
			if (t.isEmpty())
				break;
			tags.add(new Tag(t));
		}

		System.out.println(tags.size());
		if (tags.isEmpty()) {
			System.out.println("No tags specified");
			return;
		}

		try {
			// TODO find out why this always throws UnsupportedOperationException
			VDMSet repos = gh.getRepositoriesByTags(tags);
			System.out.println("Repositories");
			Iterator<Repository> ite = repos.iterator();
			while (ite.hasNext()) {
				//print tags? TODO System.out.println("[" + tags.toString() + "]");
				printRepository(ite.next());
			}
		} catch (UnsupportedOperationException e) {
			System.out.println("No repositories match those tags");
		}
	}

	@SuppressWarnings("unchecked")
	private void printRepository(Repository r) {
		System.out.println("\n---Repository " + r.name + "---");
		System.out.println("About: " + r.getDescription());
		System.out.println("Privacy: " + (r.isRepoPrivate() ? "Private" : "Public"));
		System.out.println("Default branch: " + r.getDefaultBranch().name);
		
		VDMMap branches = r.branches;
		if(!branches.isEmpty()) {
			for(Object k: branches.keySet()) {
				printBranch((Branch)branches.get(k));
			}
		} else System.out.println("No branches");
		
		VDMSet tags = r.tags;
		if (!tags.isEmpty()) {
			System.out.println("Tags:");
			Iterator<Tag> ite = tags.iterator();
			int t = 1;
			while(ite.hasNext()) {
				System.out.println(t + ". " + ite.next().name);
				t++;
			}
		} else 
			System.out.println("\tNo tags");
		
		/*VDMSet collabs = r.collaborators;
		VDMSeq releases = r.releases;*/
	}
	
	private void printBranch(Branch branch) {
		System.out.println("\tBranch: "  + branch.name);
		System.out.println("Protected: " + (branch.isProtected ? "yes" : "no"));
		
		VDMSeq commits = branch.getCommits();
		if(!commits.isEmpty()) {
			Iterator<Commit> ite = commits.iterator();
			System.out.println("Commits");
			while(ite.hasNext()) {
				Commit c = ite.next();
				System.out.println("\tCommit " + c.hash + " by " + c.author + " at " + c.timestamp);
			}
		}
		else System.out.println("No commmits");
	}

	@SuppressWarnings("unchecked")
	private void viewReposStargazers() {
		String repoName = readNonEmptyString("Repository name: ");
		VDMSet reposFound = gh.searchRepos(repoName);
		if (reposFound.isEmpty()) {
			System.out.println("No repository found");
			return;
		}

		Repository r = (Repository) reposFound.iterator().next();
		VDMSet stargazers = this.gh.stargazers(r);
		if (stargazers.isEmpty()) {
			System.out.println("No stargazers for " + r.name);
			return;
		}

		int i = 1;
		Iterator<String> ite = stargazers.iterator();
		System.out.println("Stargazers for repository " + r.name);
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next());
			i++;
		}
	}

	private void followUser() {
		String username = readNonEmptyString("User: ");
		VDMSet usersFound = gh.searchAccounts(username);

		if (usersFound.isEmpty()) {
			System.out.println("No user found");
			return;
		}

		System.out.println("Started following " + username);
		this.user.follow((User) usersFound.iterator().next());
	}

	private void unfollowUser() {
		String username = readNonEmptyString("User: ");
		VDMSet usersFound = gh.searchAccounts(username);

		if (usersFound.isEmpty()) {
			System.out.println("No user found");
			return;
		}

		System.out.println("Stopped following " + username);
		this.user.unfollow((User) usersFound.iterator().next());
	}

	private void unfollowAllUsers() {
		this.user.clearFollowing();
	}

	@SuppressWarnings("unchecked")
	private void viewFollowing() {
		VDMSet following = this.user.getFollowing();

		if (following.isEmpty()) {
			System.out.println("Not following anyone");
			return;
		}

		int i = 1;
		Iterator<User> ite = following.iterator();
		System.out.println("I follow:");
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().username);
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	private void viewFollowers() {
		VDMSet followers = this.user.getFollowers();

		if (followers.isEmpty()) {
			System.out.println("No followers :(");
			return;
		}

		int i = 1;
		Iterator<User> ite = followers.iterator();
		System.out.println("My followers:");
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().username);
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	private void viewMyStars() {
		VDMSet myStars = this.user.getStars();

		if (myStars.isEmpty()) {
			System.out.println("No stars :(");
			return;
		}

		int i = 1;
		Iterator<Repository> ite = myStars.iterator();
		System.out.println("My stars:");
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().name);
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	private void starRepos() {
		String reposName = readNonEmptyString("Repository name: ");
		Iterator<Repository> ite = this.gh.getTopRepos().iterator();
		while (ite.hasNext()) {
			Repository rep = ite.next();
			if (rep.name.equals(reposName)) {
				this.user.star(rep);
				System.out.println("You starred " + rep.name);
				return;
			}
		}
		System.out.println("No repository '" + reposName + "' found");
	}

	@SuppressWarnings("unchecked")
	private void unstarRepos() {
		String reposName = readNonEmptyString("Search for repository: ");
		Iterator<Repository> ite = this.gh.getTopRepos().iterator();
		while (ite.hasNext()) {
			Repository rep = ite.next();
			if (rep.name.equals(reposName)) {
				this.user.unstar(rep);
				System.out.println("You unstarred " + rep.name);
				return;
			}
		}
		System.out.println("No repository '" + reposName + "' found");
	}

	private void createRepos() {
		String reposName = readNonEmptyString("Repository name: ");
		String isPrivate = readNonEmptyString("Private? (y/n): ").toLowerCase();

		if (!isPrivate.equals("y") && !isPrivate.equals("n"))
			System.out.println("Invalid private setting");
		else {
			System.out.println("Repository " + reposName + " successfully created as "
					+ (isPrivate.equals("y") ? "private" : "public"));
			this.user.newRepository(reposName, (isPrivate.equals("y")));
		}
	}

	private void viewMyRepositories() {
		VDMMap myRepos = this.user.repositories;
		if (myRepos.isEmpty()) {
			System.out.println("You don't have any repositories");
			return;
		}

		System.out.println("My repositories:");
		for (Object k : myRepos.keySet()) {
			printRepository((Repository)myRepos.get(k));
		}
	}

	private void viewMyBio() {
		System.out.println("About me\n" + this.user.getDescription());
	}

	private void editMyBio() {
		String newBio = readNonEmptyString("New bio: ");
		this.user.setDescription(newBio);
		System.out.println("Bio successfully updated");
	}

	@SuppressWarnings("unchecked")
	private void searchForAccounts() {
		String str = readNonEmptyString("acc: ");
		VDMSet set = gh.searchAccounts(str);
		Iterator<Account> ite = set.iterator();
		while (ite.hasNext()) {
			Account acc = ite.next();
			if (acc instanceof User)
				System.out.println("User: " + acc.username);
			else if (acc instanceof Organization)
				System.out.println("Org: " + acc.username);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean viewMyOrgs() {
		int orgs = 0;
		for (Object k : gh.accounts.keySet()) {
			if (gh.accounts.get(k) instanceof Organization) {
				if (orgs == 0)
					System.out.println("Your organizations:");
				orgs++;
				Organization o = ((Organization) gh.accounts.get(k));
				System.out.println(orgs + ". " + o.username);

				VDMSet members = o.members;
				if (!members.isEmpty()) {
					int u = 1;
					Iterator<User> ite = members.iterator();
					while (ite.hasNext()) {
						System.out.println("\t" + u + ". " + ite.next().username);
						u++;
					}
				} else {
					System.out.println("\tNo members yet");
				}

			}
		}
		if (orgs == 0)
			System.out.println("You do not own any organizations");

		return (orgs > 0);
	}

	private void addMemberToOrg() {
		if (!viewMyOrgs()) {
			System.out.println("No orgs, please create one first");
			return;
		}

		String memberName = readNonEmptyString("\nWho to add (username): ");
		String org = readNonEmptyString("\nWhere: ");

		try {
			((Organization) gh.searchAccounts(org).iterator().next())
					.addMember(((User) gh.searchAccounts(memberName).iterator().next()));
		} catch (Exception e) {
		}

		System.out.println("Member " + memberName + " successfully added to " + org);
	}

	private void exit() {
		this.state = CLIState.EXIT;
	}

	private String readNonEmptyString(String promptMsg) {
		System.out.print(promptMsg);
		String str;
		do {
			str = scanner.nextLine();
		} while (str.isEmpty());
		return str;
	}

	private int readUserInputOpt(int lb, int ub) {
		int opt;

		System.out.print("Option (between " + lb + " and " + ub + "): ");
		try {
			opt = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid option");
			return -1;
		}

		if (opt < lb || opt > ub) {
			System.out.println("Invalid option");
			return -1;
		}

		return opt;
	}

	private void cls() {
		for (int i = 0; i < 100; i++)
			System.out.println();
	}

	private void requestEnter() {
		System.out.println("\nPress <Enter> to continue");
		scanner.nextLine();
	}

	private enum CLIState {
		MAIN_MENU, LOGGED, EXIT;
	}

}
