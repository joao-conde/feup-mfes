package cli;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import main.*;
import org.overture.codegen.runtime.*;


public class CLI {

	private Boolean running = true;
	private Github gh = new Github();
	private CLIState state = CLIState.MAIN_MENU;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new CLI().run();
	}

	public void run() {
		
		//testing area
		/*User u = new User("jotac");
		gh.addAccount(u);
		Repository repos = new Repository("feup-mfes", u, true);*/
		
		
		//
		
		while (running) {
			switch (this.state) {
			case MAIN_MENU:
				mainMenu();
				break;
				
			case LOGGED:
				loggedInMenu();
				break;

			default:
				displayExitMsg();
				running = false;
			}
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

	private void mainMenu() {
		displayWelcomeBanner();
		displayMainMenuOpts();
		int opt = readUserInputOpt(1, 10); // TODO change to options
		if (opt != -1)
			processMainMenuOpt(opt);
	}

	private void displayMainMenuOpts() {
		System.out.println("1 - Login");
		System.out.println("2 - Register user");
		System.out.println("3 - Create organization");
		System.out.println("4 - View repositories ranked by rating");
		System.out.println("5 - View repositories filtered by a set of tags");
		System.out.println("6 - View existing users");
		System.out.println("7 - View stargazers of a repository");
	}

	private void processMainMenuOpt(int opt) {
		// TODO do smthing with opt -> change state
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
		default:
			System.out.println("Invalid option");
		}
	}

	private void loggedInMenu() {
		displayWelcomeBanner();
		displayMainMenuOpts();
		displayLoggedInMenuOpts();
		int opt = readUserInputOpt(1, 10); // TODO change to options
		if (opt != -1)
			processLoggedInMenuOpt(opt);
	}
	
	private void displayLoggedInMenuOpts() {
		
	}
	
	private void processLoggedInMenuOpt(int opt) {
		
	}
	
	
	private void login() {
		System.out.println("LOGIN"); // TODO change state
	}

	private void registerUser() {
		int prev = (int) gh.numAccounts();
		String username = readNonEmptyString("Username: ");
		gh.addAccount(new User(username));
		
		if(prev + 1 == (int)gh.numAccounts())
			System.out.println("User " + username + " successfully added");
		else
			System.out.println("User " + username + " already in use, specify a different one");
	}

	private void createOrganization() {
		String org = readNonEmptyString("Organization name: ");
		gh.addAccount(new Organization(org));
		System.out.println("Organization " + org + " successfully created");
	}

	private void viewTopRepos() {
		User u = new User("jotac");
		Repository rep = new Repository("feup-mfes", u, true);
		Repository rep2 = new Repository("feup", u, true);
		gh.addAccount(u);
		u.star(rep);
		VDMSeq repos = gh.getTopRepos();
		Iterator<Repository> ite = repos.iterator();
		while(ite.hasNext()) {
			Repository r = ite.next();
			System.out.println("repos " + r.name);
		}
		System.out.println(repos.size());
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
		
		System.out.println("Option (between " + lb + " and " + ub + "): ");
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

	private enum CLIState {
		MAIN_MENU, LOGGED, C;
	}

}
