package xyz.revature.BankManager;

import java.util.ArrayList;
import java.util.Scanner;

import xyz.revature.BankManager.cli.Menu;
import xyz.revature.BankManager.models.User;

import java.util.Arrays;
import java.util.HashMap;

public class App {
	static void run() {
		Menu menu = new Menu();
		menu.echo();
		menu.parse();
		User session = menu.update();
		menu = new Menu(session);
		menu.echo();
		menu.parse();
		run();
	}
	public static void main(String[] args) {
		run();
	}
}