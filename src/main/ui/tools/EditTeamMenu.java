package ui.tools;

import model.League;
import model.Player;
import model.Team;
import ui.FantasyLeague;

import javax.swing.*;
import java.awt.*;

// displays the EditTeamMenu and allows users to add and remove player from the
// selected team and add stats to a player
public class EditTeamMenu extends FantasyLeague {

    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private JButton addStatsButton;
    private JButton refreshButton;
    private JButton backButton;

    private JTextField addPlayerName;
    private JTextField removePlayerNumber;
    private JTextField enterPlayerNumber;

    private JPanel listPlayersPanel;
    private JPanel listNumbersPanel;
    private JPanel listFPointsPanel;

    private JList<String> playerNames;
    private DefaultListModel<String> playersInLeagueIndex = new DefaultListModel<>();
    private DefaultListModel<String> playersInLeague = new DefaultListModel<>();
    private DefaultListModel<String> playersFPoints = new DefaultListModel<>();

    public Team team;
    public League league;

    // MODIFIES: this
    // EFFECTS: constructs an EditTeamMenu
    public EditTeamMenu(League league, Team team) {
        this.league = league;
        this.team = team;
        hideButtons();
        initializeEditTeam();
    }

    // MODIFIES: this
    // EFFECTS: initializes team menu
    private void initializeEditTeam() {
        initializeTextFields();
        initializeButtons();
        initializePlayerList();
        initializePlayerNumbers();
        initializeFantasyPoints();
        addFrameComponents();
        frame.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: adds all components to the frame
    private void addFrameComponents() {
        frame.add(addPlayerButton);
        frame.add(removePlayerButton);
        frame.add(addStatsButton);
        frame.add(addPlayerName);
        frame.add(removePlayerNumber);
        frame.add(enterPlayerNumber);
        frame.add(listPlayersPanel);
        frame.add(listNumbersPanel);
        frame.add(listFPointsPanel);
        frame.add(refreshButton);
        frame.add(backButton);
    }


    // MODIFIES: this
    // EFFECTS: initializes a list of all the players in the team
    private void initializePlayerList() {
        playersInLeague.clear();
        for (int i = 0; i < team.getTeamSize(); i++) {
            if (!playersInLeague.contains(team.getPlayerNameGivenIndex(i))) {
                playersInLeague.addElement(team.getPlayerGivenIndex(i).getPlayerName());
            }
        }
        playerNames = new JList(playersInLeague);
        playerNames.setVisibleRowCount(-1);
        playerNames.setVisible(true);
        listPlayersPanel = new JPanel();
        listPlayersPanel.setBounds(700, 50, 200, 525);
        listPlayersPanel.setLayout(new BorderLayout());
        listPlayersPanel.setVisible(true);
        listPlayersPanel.add(playerNames);
    }

    // MODIFIES: this
    // EFFECTS: initializes list of numbers to correspond with players in team
    private void initializePlayerNumbers() {
        playersInLeagueIndex.clear();
        for (int i = 1; i <= team.getTeamSize(); i++) {
            playersInLeagueIndex.addElement(Integer.toString(i) + " -> ");
        }
        JList<String> playerNumbers = new JList(playersInLeagueIndex);
        playerNumbers.setVisibleRowCount(-1);
        playerNumbers.setVisible(true);
        listNumbersPanel = new JPanel();
        listNumbersPanel.setBounds(650, 50, 50, 525);
        listNumbersPanel.setLayout(new BorderLayout());
        listNumbersPanel.setVisible(true);
        listNumbersPanel.add(playerNumbers);
    }

    // MODIFIES: this
    // EFFECTS: initializes list of fantasy points for each player
    private void initializeFantasyPoints() {
        playersFPoints.clear();
        for (int i = 0; i < team.getTeamSize(); i++) {
            playersFPoints.addElement(Integer.toString(team.getPlayerGivenIndex(i).getFantasyPoints()));
        }
        JList<String> playerPoints = new JList<>(playersFPoints);
        playerPoints.setVisibleRowCount(-1);
        playerPoints.setVisible(true);
        listFPointsPanel = new JPanel();
        listFPointsPanel.setBounds(900, 50, 50, 525);
        listFPointsPanel.setLayout(new BorderLayout());
        listFPointsPanel.setVisible(true);
        listFPointsPanel.add(playerPoints);
    }

    // MODIFIES: this
    // EFFECTS: initializes text fields used to add, remove, and enter a player
    private void initializeTextFields() {
        addPlayerName = new JTextField("ENTER NEW PLAYER NAME");
        addPlayerName.setBounds(50, 50, 200, 50);
        addPlayerName.setFont(new Font("Monospaced", Font.BOLD, 14));

        removePlayerNumber = new JTextField("ENTER PLAYER NUMBER");
        removePlayerNumber.setBounds(50, 550, 200, 50);
        removePlayerNumber.setFont(new Font("Monospaced", Font.BOLD, 14));


        enterPlayerNumber = new JTextField("ENTER PLAYER NUMBER");
        enterPlayerNumber.setBounds(50, 300, 200, 50);
        enterPlayerNumber.setFont(new Font("Monospaced", Font.BOLD, 14));

    }

    // MODIFIES: this
    // EFFECTS: initializes buttons to add, remove, and enter a player
    private void initializeButtons() {
        setButtonBounds();
        setButtonDesigns();
        addActionListeners();
    }

    // EFFECTS: sets bounds for each button
    private void setButtonBounds() {
        addPlayerButton = new JButton("Add player");
        addPlayerButton.setBounds(350, 50, 200, 50);
        removePlayerButton = new JButton("Remove player");
        removePlayerButton.setBounds(350, 550, 200, 50);
        addStatsButton = new JButton("Enter player menu");
        addStatsButton.setBounds(300, 300, 250, 50);
        refreshButton = new JButton("Refresh points");
        refreshButton.setBounds(700, 600, 200, 50);
        backButton = new JButton("Back");
        backButton.setBounds(10, 2, 100, 40);
    }

    // EFFECTS: set design for each button
    private void setButtonDesigns() {
        setButtonDesign(addStatsButton);
        setButtonDesign(addPlayerButton);
        setButtonDesign(removePlayerButton);
        setButtonDesign(refreshButton);
        setButtonDesign(backButton);
    }

    // EFFECTS: adds an ActionListener to each button
    private void addActionListeners() {
        addPlayerButton.addActionListener(e -> addPlayer());
        removePlayerButton.addActionListener(e -> removePlayer());
        addStatsButton.addActionListener(e -> enterPlayer());
        refreshButton.addActionListener(e -> refreshPanels());
        backButton.addActionListener(e -> goBack());
    }

    // EFFECTS: returns to EditLeagueMenu
    private void goBack() {
        frame.setVisible(false);
        new EditLeagueMenu(league);
    }

    // MODIFIES: this
    // EFFECTS: adds a player to the tem if they are not already on that team
    private void addPlayer() {
        if (addPlayerName.getText().length() > 0 && isPlayerNameTaken(addPlayerName.getText())) {
            team.addPlayer(new Player(addPlayerName.getText(), 0));
            playersInLeague.addElement(addPlayerName.getText());
            refreshPanels();
        }
    }

    // EFFECTS: refreshes panels to show updated players and stats
    private void refreshPanels() {
        initializePlayerList();
        initializePlayerNumbers();
        initializeFantasyPoints();
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: checks if the player name has already been added to this team
    private boolean isPlayerNameTaken(String playerName) {
        for (Player player : team.getPlayers()) {
            if (player.getPlayerName().equals(playerName)) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: removes a player from this team
    private void removePlayer() {
        try {
            team.removePlayerAtIndex(Integer.parseInt(removePlayerNumber.getText()) - 1);
            playersFPoints.removeElementAt(Integer.parseInt(removePlayerNumber.getText()) - 1);
        } catch (Exception e) {
            // nothing
        }
        refreshPanels();
    }

    // EFFECTS: enters the add stat menu for a player
    private void enterPlayer() {
        try {
            int playerSelectedNumber = Integer.parseInt(enterPlayerNumber.getText()) - 1;
            Player playerSelected = team.getPlayerGivenIndex(playerSelectedNumber);
            new AddStatsMenu(playerSelected);

            refreshPanels();
        } catch (Exception e) {
            // nothing
        }
    }
}
