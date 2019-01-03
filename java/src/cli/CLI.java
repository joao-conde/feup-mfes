package cli;

import java.util.Iterator;
import java.util.Scanner;

import main.*;
import org.overture.codegen.runtime.*;

public class CLI {

	private Github gh = new Github();
	private User user = null; // logged in user

	private Boolean running = true;
	private CLIState state = CLIState.MAIN_MENU;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new CLI().run();
	}

	public void run() {

		// testing area TODO REMOVE THIS
		/*User u = new User("jotac");
		gh.addAccount(u);
		Repository repos = new Repository("feup-mfes", u, true);
		Tag tag1 = new Tag("mfes");
		repos.addTag(tag1);*/
		//
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
		int opt = readUserInputOpt(1, 6); // TODO change to options
		if (opt != -1)
			processMainMenuOpt(opt);
	}

	private void displayMainMenuOpts() {
		System.out.println("1 - Login");
		System.out.println("2 - Register user");
		System.out.println("3 - Create organization");
		System.out.println("4 - View repositories ranked by rating");
		System.out.println("5 - View repositories filtered by a set of tags");
		System.out.println("6 - View stargazers of a repository");
	}

	private void processMainMenuOpt(int opt) {
		switch (opt) {
		case 1:
			login();
			break;
		case 2:
			registerUser();
			break;
		case 3:
			createOrganization();
			break;
		case 4:
			viewTopRepos();
			break;
		case 5:
			viewReposByTags();
			break;
		case 6:
			viewReposStargazers();
			break;
		default:
			System.out.println("Invalid option");
		}
	}

	private void loggedInMenu() {
		displayLoggedBanner();
		displayLoggedInMenuOpts();
		int opt = readUserInputOpt(1, 14); // TODO change to options
		if (opt != -1)
			processLoggedInMenuOpt(opt);
	}

	private void displayLoggedInMenuOpts() {
		System.out.println("1 - Logout");
		System.out.println("2 - Register user");
		System.out.println("3 - Create organization");
		System.out.println("4 - View repositories ranked by rating");
		System.out.println("5 - View repositories filtered by a set of tags");
		System.out.println("6 - View stargazers of a repository");
		System.out.println("7 - Follow an user");
		System.out.println("8 - Unfollow an user");
		System.out.println("9 - Unfollow all users");
		System.out.println("10 - View who I follow");
		System.out.println("11 - View my followers");
		System.out.println("12 - View my stars");
		System.out.println("13 - Star a repository");
		System.out.println("14 - Unstar a repository");
	}

	private void processLoggedInMenuOpt(int opt) {
		if (opt <= 6 && opt != 1) {
			processMainMenuOpt(opt);
			return;
		}
		
		switch (opt) {
		case 1:
			logout();
			break;
		case 7:
			followUser();
			break;
		case 8:
			unfollowUser();
			break;
		case 9:
			unfollowAllUsers();
			break;
		case 10:
			viewFollowing();
			break;
		case 11:
			viewFollowers();
			break;
		case 12:
			viewMyStars();
			break;
		case 13:
			starRepos();
			break;
		case 14:
			unstarRepos();
			break;
		default:
			System.out.println("Invalid option");
		}
	}

	private void login() {
		String username = readNonEmptyString("Username: ");
		this.user = new User(username);
		this.state = CLIState.LOGGED;
	}

	private void logout() {
		this.user = null;
		this.state = CLIState.MAIN_MENU;
	}

	private void registerUser() {
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
		/*
		 * User u = new User("jotac"); Repository rep = new Repository("feup-mfes", u,
		 * true); Repository rep2 = new Repository("feup", u, true); gh.addAccount(u);
		 * u.star(rep);
		 */
		VDMSeq repos = gh.getTopRepos();
		System.out.println("Top Repositories");
		int i = 1;
		Iterator<Repository> ite = repos.iterator();
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().name);
			i++;
		}
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
		System.out.println("Repositories");
		System.out.println("[" + tags.toString() + "]");
		int i = 1;
		Iterator<Repository> ite = gh.getRepositoriesByTags(tags).iterator();
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().name);
			i++;
		}
	}

	private void viewReposStargazers() {
		String repoName = readNonEmptyString("Search for repository: ");
		// search for repos
		// this.gh.stargazers(reposFound);
		// display all stargazers
	}

	private void followUser() {
		String username = readNonEmptyString("Search for user: ");
		// search for user
		// this.user.follow(userFound);
	}

	private void unfollowUser() {
		String username = readNonEmptyString("Search for user: ");
		// search for user
		// this.user.unfollow(userFound);
	}

	private void unfollowAllUsers() {
		this.user.clearFollowing();
	}

	@SuppressWarnings("unchecked")
	private void viewFollowing() {
		System.out.println("Currently following:");
		Iterator<User> ite = this.user.getFollowing().iterator();
		int i = 1;
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().username);
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	private void viewFollowers() {
		System.out.println("My followers:");
		Iterator<User> ite = this.user.getFollowers().iterator();
		int i = 1;
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().username);
			i++;
		}
	}

	private void viewMyStars() {
		System.out.println("My stars:");
		Iterator<Repository> ite = this.user.getStars().iterator();
		int i = 1;
		while (ite.hasNext()) {
			System.out.println(i + ". " + ite.next().name);
			i++;
		}
	}

	private void starRepos() {
		String reposName = readNonEmptyString("Search for repository: ");
		Iterator<Repository> ite = this.gh.getTopRepos().iterator();
		while (ite.hasNext()) {
			Repository rep = ite.next();
			if (rep.name.equals(reposName)) {
				this.user.star(rep);
				break;
			}
		}
	}

	private void unstarRepos() {
		String reposName = readNonEmptyString("Search for repository: ");
		Iterator<Repository> ite = this.gh.getTopRepos().iterator();
		while (ite.hasNext()) {
			Repository rep = ite.next();
			if (rep.name.equals(reposName)) {
				this.user.unstar(rep);
				break;
			}
		}
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
		System.out.println("Press <Enter> to continue");
		scanner.nextLine();
	}

	private enum CLIState {
		MAIN_MENU, LOGGED, EXIT;
	}

}
