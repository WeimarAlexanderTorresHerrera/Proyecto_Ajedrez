package edu.upb.lp.progra.BugWorld;

import android.graphics.Color;
import edu.upb.lp.progra.adapterFiles.AndroidGameGUI;
import edu.upb.lp.progra.adapterFiles.UI;

public class BugWorldUI implements UI {
	private BugWorld world;
	private int selectedh = -1;
	private int selectedv = -1;
	private AndroidGameGUI gui;

	private ScoreManager scoreManager;

	public BugWorldUI(AndroidGameGUI gui) {
		int highScore = gui.retrieveInt("HighScore");
		String highScoreName = gui.retrieveString("HighScoreName");
		world = new BugWorld(this, highScore, highScoreName);
		this.gui = gui;
		scoreManager = new ScoreManager(this, highScore, highScoreName);
	}

	@Override
	public void onButtonPressed(String name) {
		// TODO replace this by a switch
		if (name.equals("Restart")) {
			scoreManager.checkHighScore(world.getScore());
		} else if (name.equals("Pass day")) {
			world.day();
		} else if (name.equals("Buy food")) {
			world.buyFood();
		} else if (name.equals("Clean cell")) {
			world.cleanCell(selectedv, selectedh);
		} else if (name.equals("Sell bug")) {
			world.sellBug(selectedv, selectedh);
		}
		updateInterface();
	}

	@Override
	public void onCellPressed(int vertical, int horizontal) {
		if (selectedh == horizontal && selectedv == vertical) {
			// Same position as before selected again, deselect it
			selectedh = -1;
			selectedv = -1;
		} else {
			if (selectedh > -1
					&& world.getCell(selectedv, selectedh).isBugAlive()
					&& Math.abs(selectedh - horizontal) <= 1
					&& Math.abs(selectedv - vertical) <= 1) {
				// Neighboring position, move bug
				world.moveBug(selectedv, selectedh, vertical, horizontal);
			}
			// Select the new position
			selectedh = horizontal;
			selectedv = vertical;
		}
		updateInterface();
	}

	@Override
	public void initialiseInterface() {
		// General configuration
		gui.configureGrid(world.getSizeVertical(), world.getSizeHorizontal(),
				1, 1, 10);
		gui.setBottomSectionProportion(0.25);

		// Text fields
		// Score
		gui.addTextField("Score", "Score: " + world.getScore(), 20, 25);

		// Money
		gui.addTextField("Money", "Money: " + world.getMoney(), 20, 25);

		// Food price
		gui.addTextField("Food price", "Food price: " + world.getFoodPrice(),
				20, 25);

		// A dummy text to make space
		gui.addTextField("Dummy", "", 0, 25);

		updateInterface();
	}

	private void updateInterface() {
		// Text fields
		// Score
		gui.updateTextField("Score", "Score: " + world.getScore());

		// Money
		gui.updateTextField("Money", "Money: " + world.getMoney());

		// Food price
		gui.updateTextField("Food price", "Food price: " + world.getFoodPrice());

		// Cells
		for (int vertical = 0; vertical < world.getSizeVertical(); vertical++) {
			for (int horizontal = 0; horizontal < world.getSizeHorizontal(); horizontal++) {
				gui.setTextColorOnCell(vertical, horizontal, Color.BLACK);
				gui.setTextSizeOnCell(vertical, horizontal, 20);
				boolean selected = horizontal == selectedh
						&& vertical == selectedv;
				getImage(vertical, horizontal, selected);
			}
		}

		// Bug description
		if (selectedh > -1 && world.getCell(selectedv, selectedh).isBugAlive()) {
			Bug bug = world.getCell(selectedv, selectedh).getBug();
			gui.addTextField("SelectedBug", "Selected bug:", 20, 25);
			gui.addTextField("Age", "  age: " + bug.getAge(), 20, 25);
			gui.addTextField("Hunger", "  hunger: " + bug.getHunger(), 20, 25);
			gui.addTextField("Fun", "  fun: " + bug.getFun(), 20, 25);
		} else {
			gui.removeTextField("SelectedBug");
			gui.removeTextField("Age");
			gui.removeTextField("Hunger");
			gui.removeTextField("Fun");
		}

		// Buttons
		gui.addButton("Restart", 20, 50);
		gui.addButton("Pass day", 20, 50);
		gui.addButton("Buy food", 20, 50);
		if (selectedh != -1 && !world.getCell(selectedv, selectedh).isEmpty()) {
			if (world.getCell(selectedv, selectedh).isBugAlive()) {
				gui.addButton("Sell bug", 20, 50);
				gui.removeButton("Clean cell");
			} else {
				gui.addButton("Clean cell", 20, 50);
				gui.removeButton("Sell bug");
			}
		} else {
			gui.removeButton("Sell bug");
			gui.removeButton("Clean cell");
		}
	}

	private void getImage(int vertical, int horizontal, boolean selected) {
		Cell cell = world.getCell(vertical, horizontal);
		if (cell.isEmpty() || cell.getFood() > 0) {
			if (selected) {
				gui.setImageOnCell(vertical, horizontal, "colors_blue");
			} else {
				gui.setImageOnCell(vertical, horizontal, "colors_grey");
			}
			gui.setTextOnCell(vertical, horizontal, "" + (cell.getFood() > 0 ? cell.getFood() : ""));
		} else {
			gui.setTextOnCell(vertical, horizontal, "");
			String imageName = "";
			Bug bug = cell.getBug();
			if (bug.isDead()) {
				imageName = "bugs_dead_bug";
			} else if (bug.getHunger() > 10) {
				imageName = "bugs_hungry_bug";
			} else if (bug.getFun() < 10) {
				imageName = "bugs_sad_bug";
			} else if (bug.getAge() > 15) {
				imageName = "bugs_old_bug";
			} else {
				imageName = "bugs_happy_bug";
			}
			if (selected) {
				imageName += "_selected";
			}
			gui.setImageOnCell(vertical, horizontal, imageName);
		}
	}

	public void showMessage(String message) {
		gui.reproduceSound("ping");
		gui.showTemporaryMessage(message, false);
	}

	public void showLongMessage(String message) {
		gui.reproduceSound("ping");
		gui.showTemporaryMessage(message, true);
	}

	public void askUserName(ScoreManager scoreManager) {
		gui.askUserText("Congratulations! New high score!\nPlease insert your name", scoreManager);
	}

	public void storeHighScore(int score) {
		showLongMessage("Congratulations! New High Score!");
		gui.storeInt("HighScore", score);
		askUserName(scoreManager);
	}

	public void storeHighScoreName(String name) {
		gui.storeString("HighScoreName", name);

	}

	public void endGame(int highScore, String highScoreName) {
		gui.reproduceSound("ping");
		gui.reproduceSound("ping");
		gui.showTemporaryMessage("Game ended\nHigh score: " + highScore + " ("
				+ highScoreName + ")", true);
		world = new BugWorld(this, highScore, highScoreName);
		updateInterface();
	}
}
