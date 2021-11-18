package ui;

import model.League;
import ui.tools.EditLeagueMenu;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import persistence.JsonReader;
import persistence.JsonWriter;

// represents the home page of a fantasy league
public class FantasyLeague extends JFrame {

    private League league;
    public JFrame frame;
    private JButton enterLeagueButton;
    private JButton saveLeagueButton;
    private JButton loadLeagueButton;
    private JLabel titleLabel;
    private JLabel ballGIF;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/league.json";


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    // MODIFIES: this
    // EFFECTS: constructs the Frame and initializes graphics
    public FantasyLeague() {
        super("Fantasy League");
        initializeFields();
        initializeGraphics();
    }

    // EFFECTS: returns league
    public League getLeague() {
        return league;
    }

    // MODIFIES: this
    // EFFECTS: changes the league to give league
    public void setLeague(League league) {
        this.league = league;
    }

    // MODIFIES: this
    // EFFECTS: initializes an empty league and json objects
    public void initializeFields() {
        league = new League("");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes frame and adds components
    public void initializeGraphics() {
        frame = new JFrame("Fantasy League");
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        initializeButtons();
        initializeLabels();
        frame.add(enterLeagueButton);
        frame.add(saveLeagueButton);
        frame.add(loadLeagueButton);
        frame.add(titleLabel);
        frame.add(ballGIF);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: constructs title JLabel
    private void initializeLabels() {
        titleLabel = new JLabel("MT Fantasy League");
        titleLabel.setBounds(200, 50, 600, 100);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 55));

        // gif from https://giphy.com/stickers/matchesfashion-frieze-art-fair-carlos-place-d7ToT8jj75iFQtNTQS
        ImageIcon ball = new ImageIcon("/home/manveertrehan01/IdeaProjects/project_k8f3b/data/bouncingball.gif");
        ballGIF = new JLabel(ball);
        ballGIF.setBounds(400, 150, 200, 200);
    }

    // MODIFIES: this
    // EFFECTS: sets bounds and designs of buttons
    private void initializeButtons() {
        enterLeagueButton = new JButton("Enter League");
        enterLeagueButton.setBounds(200, 400, 600, 50);
        setButtonDesign(enterLeagueButton);
        enterLeagueButton.addActionListener(e -> editLeagueMenu());

        saveLeagueButton = new JButton("Save League");
        saveLeagueButton.setBounds(200, 460, 600, 50);
        setButtonDesign(saveLeagueButton);
        saveLeagueButton.addActionListener(e -> saveLeague());

        loadLeagueButton = new JButton("Load League");
        loadLeagueButton.setBounds(200, 520, 600, 50);
        setButtonDesign(loadLeagueButton);
        loadLeagueButton.addActionListener(e -> loadLeague());
    }

    // MODIFIES: this
    // EFFECTS: sets design of buttons
    public void setButtonDesign(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.white);
    }

    // EFFECTS: enters the EditLeagueMenu
    private void editLeagueMenu() {
        frame.setVisible(false);
        new EditLeagueMenu(league);
    }

    // MODIFIES: this
    // EFFECTS: hides buttons from frame
    public void hideButtons() {
        enterLeagueButton.setVisible(false);
        saveLeagueButton.setVisible(false);
        loadLeagueButton.setVisible(false);
        titleLabel.setVisible(false);
        ballGIF.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: saves league to JSon
    public void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.writeLeague(league);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: loads league from JSon file
    public void loadLeague() {
        try {
            league = jsonReader.read();
        } catch (IOException e) {
            // nothing
        }
    }
}
