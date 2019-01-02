package cli;

import java.util.Scanner;

import main.Github;

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
				break;

			default:
				System.out.println("EXITING");
				running = false;
			}
		}
	}

	private void mainMenu() {
		displayMainMenu();
		
		int opt = readUserInput(1, 5);
		if(opt == -1) return;

		// TODO not hardcoded bounds, do smthing with opt -> change state
	}

	private void displayMainMenu() {
		System.out.println("==========================================");
		System.out.println("Welcome to GitHub!");
		System.out.println("==========================================");
		System.out.println("Select an option");
		System.out.println("1 - Add a GitHub account");
		System.out.println("2 - View how many GitHub accounts exist");
		System.out.println("3 - View repositories filtered by a set of tags");
		System.out.println("4 - View existing users");
		System.out.println("5 - View stargazers of a repository");
		System.out.println("6 - View all repositories");
		System.out.println("7 - View the repositories rating ranks");
	}

	private int readUserInput(int lb, int ub) {
		int opt;
		
		try{
			opt = Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e) {
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
