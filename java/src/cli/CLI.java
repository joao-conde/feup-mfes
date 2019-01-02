package cli;

import java.io.IOException;
import java.util.Scanner;

import main.*;

public class CLI {

	private Boolean running = true;
	private Github gh = new Github();
	private CLIState state = CLIState.MAIN_MENU;
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new CLI().run();
	}

	public void run() {
		while (running) {
			switch (this.state) {
			case MAIN_MENU:
				mainMenu();
				System.out.println(gh.numAccounts());
				break;

			default:
				System.out.println("EXITING");
				running = false;
			}
		}
	}

	private void mainMenu() {
		displayMainMenuOpts();
		int opt = readUserInputOpt(1, 10); // TODO change to options
		if (opt != -1)
			processOption(opt);
	}

	private void displayMainMenuOpts() {
		System.out.println("==================================================");
		System.out.println("\t\tWelcome to GitHub!\t\t");
		System.out.println("==================================================");
		System.out.println("Select an option");
		System.out.println("1 - Login");
		System.out.println("2 - Register user");
		System.out.println("3 - Create organization");
		System.out.println("2 - View how many GitHub accounts exist");
		System.out.println("3 - View repositories filtered by a set of tags");
		System.out.println("4 - View existing users");
		System.out.println("5 - View stargazers of a repository");
		System.out.println("6 - View all repositories");
		System.out.println("7 - View the repositories rating ranks");
	}

	private void processOption(int opt) {
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
		}
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

	private String readNonEmptyString(String promptMsg) {
		System.out.print(promptMsg);
		String inp;
		do {
			inp = scanner.nextLine();
		} while (inp.isEmpty());
		return inp;
	}

	private int readUserInputOpt(int lb, int ub) {
		int opt;

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
		MAIN_MENU, B, C;
	}

}
