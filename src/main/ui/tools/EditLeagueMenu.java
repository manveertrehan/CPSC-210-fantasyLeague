package ui.tools;

import model.League;
import model.Team;
import ui.FantasyLeague;

import javax.swing.*;
import java.awt.*;

// displays the edit league menu, allows users to add, remove, and enter a team
public class EditLeagueMenu extends FantasyLeague {

    private JButton addTeamButton;
    private JButton removeTeamButton;
    private JButton enterTeamButton;
    private JButton backButton;

    private JTextField addTeamName;
    private JTextField removeTeamNumber;
    private JTextField enterTeamNumber;

    private JPanel listTeamsPanel;
    private JPanel listNumbersPanel;
    private JPanel teamFPointsPanel;

    private JLabel addTeamLabel;
    private JLabel removeTeamLabel;
    private JLabel enterTeamLabel;

    private DefaultListModel<String> teamsInLeagueIndex = new DefaultListModel<>();
    private DefaultListModel<String> teamsInLeague = new DefaultListModel<>();
    private DefaultListModel<String> teamFPoints = new DefaultListModel<>();

    public League league;
    public EditTeamMenu editTeamMenu;

    // EFFECTS: constructs a league menu
    public EditLeagueMenu(League league) {
        this.league = league;
        hideButtons();
        initializeEditLeague();
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame components of leagueMenu
    private void initializeEditLeague() {
        initializeTextFields();
        initializeButtons();
        initializeTeamList();
        initializeTeamNumbers();
        initializeTeamFantasyPoints();
        initializeLeagueLabels();
        addFrameComponents();
        frame.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: adds components to frame
    private void addFrameComponents() {
        frame.add(addTeamButton);
        frame.add(removeTeamButton);
        frame.add(enterTeamButton);
        frame.add(addTeamName);
        frame.add(removeTeamNumber);
        frame.add(enterTeamNumber);
        frame.add(listTeamsPanel);
        frame.add(listNumbersPanel);
        frame.add(teamFPointsPanel);
        frame.add(backButton);
        frame.add(addTeamLabel);
        frame.add(removeTeamLabel);
        frame.add(enterTeamLabel);
    }

    // MODIFIES: this
    // EFFECTS: initializes JLabels for textfields
    private void initializeLeagueLabels() {
        addTeamLabel = new JLabel("Enter name of team you want to to add");
        addTeamLabel.setBounds(50, 100, 350, 50);
        addTeamLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

        removeTeamLabel = new JLabel("Enter number of team to remove");
        removeTeamLabel.setBounds(50, 600, 350, 50);
        removeTeamLabel.setFont(new Font("Monospaced", Font.BOLD, 14));


        enterTeamLabel = new JLabel("Enter number of team you wish to view/edit");
        enterTeamLabel.setBounds(50, 350, 350, 50);
        enterTeamLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

    }

    // MODIFIES: this
    // EFFECTS: displays a list of all teams in the league
    private void initializeTeamList() {
        teamsInLeague.clear();
        for (int i = 0; i < league.getLeagueSize(); i++) {
            if (!teamsInLeague.contains(league.getTeamNameGivenIndex(i))) {
                teamsInLeague.addElement(league.getTeamGivenIndex(i).getTeamName());
            }
        }
        JList<String> teamNames = new JList(teamsInLeague);
        teamNames.setVisibleRowCount(-1);
        teamNames.setVisible(true);
        listTeamsPanel = new JPanel();
        listTeamsPanel.setBounds(700, 50, 200, 525);
        listTeamsPanel.setLayout(new BorderLayout());
        listTeamsPanel.setVisible(true);
        listTeamsPanel.add(teamNames);
    }

    // MODIFIES: this
    // EFFECTS: initializes list of numbers to correspond to each team
    private void initializeTeamNumbers() {
        teamsInLeagueIndex.clear();
        for (int i = 1; i <= league.getLeagueSize(); i++) {
            teamsInLeagueIndex.addElement(Integer.toString(i) + " -> ");
        }
        JList<String> teamNumbers = new JList(teamsInLeagueIndex);
        teamNumbers.setVisibleRowCount(-1);
        teamNumbers.setVisible(true);
        listNumbersPanel = new JPanel();
        listNumbersPanel.setBounds(650, 50, 50, 525);
        listNumbersPanel.setLayout((new BorderLayout()));
        listNumbersPanel.setVisible(true);
        listNumbersPanel.add(teamNumbers);
    }

    // MODIFIES: this
    // EFFECTS: initializes list of fantasy point corresponding to each team
    private void initializeTeamFantasyPoints() {
        teamFPoints.clear();
        for (int i = 0; i < league.getLeagueSize(); i++) {
            teamFPoints.addElement(Integer.toString(league.getTeamGivenIndex(i).getTotalFantasyPoints()));
        }
        JList<String> teamPoints = new JList<>(teamFPoints);
        teamPoints.setVisibleRowCount(-1);
        teamPoints.setVisible(true);
        teamFPointsPanel = new JPanel();
        teamFPointsPanel.setBounds(900, 50, 50, 525);
        teamFPointsPanel.setLayout(new BorderLayout());
        teamFPointsPanel.setVisible(true);
        teamFPointsPanel.add(teamPoints);
    }

    // MODIFIES: this
    // EFFECTS: initializes text fields use to add or select teams
    private void initializeTextFields() {
        addTeamName = new JTextField();
        addTeamName.setBounds(50, 50, 200, 50);

        removeTeamNumber = new JTextField();
        removeTeamNumber.setBounds(50, 550, 200, 50);

        enterTeamNumber = new JTextField();
        enterTeamNumber.setBounds(50, 300, 200, 50);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons used to add, remove, or enter a team
    private void initializeButtons() {
        setButtonBounds();
        setButtonsDesigns();
        addActionListeners();
    }

    // EFFECTS: adds ActionListeners to each button
    private void addActionListeners() {
        addTeamButton.addActionListener(e -> addTeam());
        removeTeamButton.addActionListener(e -> removeTeam());
        enterTeamButton.addActionListener(e -> enterTeam());
        backButton.addActionListener(e -> goBack());
    }

    // EFFECTS: sets the design of each button
    private void setButtonsDesigns() {
        setButtonDesign(backButton);
        setButtonDesign(enterTeamButton);
        setButtonDesign(addTeamButton);
        setButtonDesign(removeTeamButton);
    }

    // EFFECTS: sets the bounds of each button
    private void setButtonBounds() {
        addTeamButton = new JButton("Add team");
        addTeamButton.setBounds(350, 50, 200, 50);
        removeTeamButton = new JButton("Remove team");
        removeTeamButton.setBounds(350, 550, 200, 50);
        enterTeamButton = new JButton("Enter team menu");
        enterTeamButton.setBounds(300, 300, 250, 50);
        backButton = new JButton("Home");
        backButton.setBounds(10, 2, 100, 40);
    }

    // EFFECTS: returns to FantasyLeague screen
    private void goBack() {
        frame.setVisible(false);
        FantasyLeague fantasyLeague = new FantasyLeague();
        fantasyLeague.setLeague(league);
    }

    // MODIFIES: this
    // EFFECTS: adds a team to the league
    private void addTeam() {
        if (addTeamName.getText().length() > 0 && isTeamNameTaken(addTeamName.getText())) {
            league.addTeam(new Team(addTeamName.getText()));
            teamsInLeague.addElement(addTeamName.getText());
            refreshPanels();
        }
    }

    // EFFECTS: refreshes panels to show updated lists
    private void refreshPanels() {
        initializeTeamList();
        initializeTeamNumbers();
        initializeTeamFantasyPoints();
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: checks if the team being added is already in the list
    private boolean isTeamNameTaken(String teamName) {
        for (Team team : league.getTeams()) {
            if (team.getTeamName().equals(teamName)) {
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: removes team from league
    private void removeTeam() {
        try {
            league.removeTeam(Integer.parseInt(removeTeamNumber.getText()) - 1);
            teamFPoints.removeElementAt(Integer.parseInt(removeTeamNumber.getText()) - 1);
            teamsInLeagueIndex.removeElementAt(Integer.parseInt(removeTeamNumber.getText()) - 1);
        } catch (Exception e) {
            // nothing
        }
        refreshPanels();
    }

    // EFFECTS: enters the EditTeamMenu with selected team
    private void enterTeam() {
        try {
            int teamSelectedNumber = Integer.parseInt(enterTeamNumber.getText()) - 1;
            Team teamSelected = league.getTeamGivenIndex(teamSelectedNumber);
            frame.setVisible(false);
            EditTeamMenu editTeamMenu = new EditTeamMenu(league, teamSelected);
        } catch (Exception e) {
            // nothing
        }
    }
}
