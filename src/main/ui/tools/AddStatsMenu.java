package ui.tools;

import model.Player;
import ui.FantasyLeague;

import javax.swing.*;
import java.awt.*;

// represents a popup menu that allows user to add stats to a player
public class AddStatsMenu {

    private JButton enterStats;
    private JTextField pointsField;
    private JTextField assistsField;
    private JTextField reboundsField;
    private JFrame addStatsFrame;
    private JLabel playerName;
    private JLabel playerPoints;
    private JLabel points;
    private JLabel assists;
    private JLabel rebounds;

    public Player player;

    // MODIFIES: this
    // EFFECTS: constructs a pop-up menu that allows users to add stats to given player
    public AddStatsMenu(Player player) {
        this.player = player;
        initializeAddStatsMenu();
    }

    // MODIFIES: this
    // EFFECTS: initializes new JFrame with add stats options
    private void initializeAddStatsMenu() {

        addStatsFrame = new JFrame();
        addStatsFrame.setSize(new Dimension(500, 575));

        initializeButton();
        initializeTextFields();
        initializeNameLabel();
        initializePointsLabel();
        initializeStatLabels();
        addComponents();
        addStatsFrame.setLayout(new BorderLayout());
        addStatsFrame.setVisible(true);
    }

    private void initializeStatLabels() {
        points = new JLabel("<- Enter points");
        points.setBounds(70, 270, 150, 50);

        rebounds = new JLabel("<- Enter rebounds");
        rebounds.setBounds(70, 350, 170, 50);

        assists = new JLabel("<- Enter assists");
        assists.setBounds(70, 430, 150, 50);

    }

    // MODIFIES: this
    // EFFECTS: creates label of player fantasyPoints
    private void initializePointsLabel() {
        playerPoints = new JLabel(Integer.toString(player.getFantasyPoints()) + " Fantasy Points");
        playerPoints.setBounds(15, 150, 250, 50);
        playerPoints.setFont(new Font("Monospaced", Font.BOLD, 24));
    }

    // MODIFIES: this
    // EFFECTS: adds components to frame
    private void addComponents() {
        addStatsFrame.add(pointsField);
        addStatsFrame.add(assistsField);
        addStatsFrame.add(reboundsField);
        addStatsFrame.add(enterStats);
        addStatsFrame.add(playerName);
        addStatsFrame.add(playerPoints);
        addStatsFrame.add(points);
        addStatsFrame.add(assists);
        addStatsFrame.add(rebounds);
    }

    // MODIFIES: this
    // EFFECTS: creates label of player name
    private void initializeNameLabel() {
        playerName = new JLabel(player.getPlayerName() + " - " + player.getTeam().getTeamName());
        playerName.setBounds(15, 25, 475, 200);
        playerName.setFont(new Font("Monospaced", Font.BOLD, 30));
    }

    // MODIFIES: this
    // EFFECTS: initializes text fields for adding stats
    private void initializeTextFields() {
        pointsField = new JTextField();
        pointsField.setBounds(15, 270, 50, 50);

        assistsField = new JTextField();
        assistsField.setBounds(15, 350, 50, 50);

        reboundsField = new JTextField();
        reboundsField.setBounds(15, 430, 50, 50);
    }

    // MODIFIES: this
    // EFFECTS: initializes button to submit stats to player
    private void initializeButton() {
        enterStats = new JButton("Submit stats");
        enterStats.setBounds(250, 270, 225, 200);

        enterStats.setBackground(Color.DARK_GRAY);
        enterStats.setFont(new Font("Arial", Font.BOLD, 20));
        enterStats.setForeground(Color.white);

        enterStats.addActionListener(e -> addStats());
    }

    // MODIFIES: this
    // EFFECTS: adds stats to the player
    private void addStats() {
        try {
            int points = Integer.parseInt(pointsField.getText());
            int assists = Integer.parseInt(assistsField.getText());
            int rebounds = Integer.parseInt(reboundsField.getText());

            player.addPoints(points);
            player.addRebounds(rebounds);
            player.addAssists(assists);

            addStatsFrame.dispose();
        } catch (Exception e) {
            // do nothing
        }
    }
}
