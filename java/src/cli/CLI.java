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

		// TODO not hardcoded bounds, do smthing with opt -> change state
	}

	private void displayMainMenu() {
		System.out.print("SELECT UR OPTION\n list of opts\n");
	}

	private int readUserInput(int lb, int ub) {
		int opt = Integer.parseInt(scanner.nextLine());

		if (opt < lb || opt > ub) {
			System.out.println("Invalid option");
			opt = readUserInput(lb, ub);
		}

		return opt;
	}

	private enum CLIState {
		MAIN_MENU, B, C;
	}

}
