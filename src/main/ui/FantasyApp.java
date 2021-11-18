package ui;

import model.League;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Multiple methods take code from TellerApp in AccountNotRobust
public class FantasyApp {
    private static Scanner input;
    private League league;
    private static final String JSON_STORE = "./data/league.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the fantasy app
    public FantasyApp() {
        runFantasy();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and kills program
    private void runFantasy() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayHome();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
                System.exit(0);
            } else {
                processCommand(command);
            }

        }
        System.out.println("\nBye");

    }

    // MODIFIES: this
    // EFFECTS: initializes new league
    private void init() {
        input = new Scanner(System.in);
        league = new League("");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays the home and initial options
    private void displayHome() {
        System.out.println("\nSelect one of:");
        System.out.println("\tv -> view teams (add/view stats");
        System.out.println("\te -> edit league (add/remove teams)");
        System.out.println("\tm -> manage rosters (add/remove players)");
        System.out.println("\ts -> save league to file");
        System.out.println("\tl -> load league from file");
        System.out.println("\tq -> end program");
        System.out.print("Tip: to get started, go to 'edit league' and add a couple teams, and then go to 'manage ");
        System.out.print("rosters' to add players to your teams.");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processCommand(String command) {
        if (command.equals("v")) {
            displayListOfTeams();
        } else if (command.equals("e")) {
            editLeague();
        } else if (command.equals("m")) {
            editTeams();
        } else if (command.equals("s")) {
            saveLeague();
        } else if (command.equals("l")) {
            loadLeague();
        } else {
            System.out.println("Invalid input ... please try again");
        }
    }

    // EFFECTS: displays a list of teams in the league
    //          and allows users to select a team
    private void displayListOfTeams() {
        System.out.println("\nEnter the number of the team that you wish to view/edit");
        printTeams();
        System.out.println("\n Or enter '0' to return home");

        int command = input.nextInt();
        if (command > 0) {
            displayInsideTeam(league.getTeamGivenIndex(command - 1));
        } else if (command != 0) {
            System.out.println("Invalid command");
        } else {
            returnHome();
        }
    }


    // REQUIRES: input is within team size
    // EFFECTS: displays a list of players on the selected team
    public void displayInsideTeam(Team team) {

        System.out.println("\nTEAM OVERVIEW:");
        System.out.println("\tTeam name:" + team.getTeamName());
        System.out.println("\tTeam Fantasy Points:" + team.getTotalFantasyPoints());
        System.out.println("---------------------------------------------------");
        System.out.println("\nThe players on this team are:");
        printPlayers(team);
        System.out.println("\n Please enter the number of the player you wish to select");
        System.out.println("\n Tip: if this list is empty, you need to add players to this team. Enter 0 to go home");

        int command = input.nextInt();
        if (command > 0) {
            displayInsidePlayer(team.getPlayerGivenIndex(command - 1));
        } else if (command != 0) {
            System.out.println("Invalid command");
        } else {
            returnHome();
        }
    }


    // MODIFIES: this
    // EFFECTS: adds a player to the team
    public void addPlayer(Team team) {
        System.out.println("\nWhat is the name of the player you want to add?");
        input.nextLine();
        Player player = new Player(input.nextLine(), 0);
        team.addPlayer(player);
        System.out.println("Player added");
    }

    // MODIFIES: this
    // EFFECTS: removes a player from the team
    public void removePlayer(Team team) {
        System.out.println("Enter the number of the player you want to remove");
        printPlayers(team);
        int command = input.nextInt();
        if (input.nextInt() > 0) {
            team.removePlayerAtIndex(command - 1);
            System.out.println("Player removed");
        } else {
            System.out.println("Invalid command");
        }
    }


    // MODIFIES: this
    // EFFECTS: displays the player's fantasy points and allows users to add stats
    public void displayInsidePlayer(Player player) {
        System.out.println("\nPLAYER OVERVIEW:");
        System.out.println("\tPlayer name: " + player.getPlayerName());
        System.out.println("\tPlayer Fantasy Points: " + player.getFantasyPoints());
        System.out.println("-------------------------------------------------");
        System.out.println("\nPlease select:");
        System.out.println("\t1 -> Add statline (points, assists, rebounds)");
        System.out.println("\t0 -> Return home");

        if (input.nextInt() == 1) {
            addStatLine(player);
        } else if ((input.nextInt() > 1) && (input.nextInt() < 0)) {
            System.out.println("Invalid command");
        } else {
            returnHome();
        }
    }

    // REQUIRES: non-negative values for points, assists, steals
    // MODIFIES: this
    // EFFECTS: adds
    public void addStatLine(Player player) {
        System.out.println("How many points did " + player.getPlayerName() + " get today?");
        player.addPoints(input.nextInt());

        System.out.println("How many assists did " + player.getPlayerName() + " dish out?");
        player.addAssists(input.nextInt());

        System.out.println("How many rebounds did " + player.getPlayerName() + " snag?");
        player.addRebounds(input.nextInt());

        System.out.println("Stat line added");
        displayListOfTeams();
    }

    // MODIFIES: this
    // EFFECTS: allows user to add/remove players on teams
    public void editTeams() {
        System.out.println("\nSelect one of:");
        System.out.println("\t1 -> Add a player to a team");
        System.out.println("\t2 -> Remove a player from a team");

        if (input.nextInt() == 1) {
            addPlayerToTeam();
        } else if (input.nextInt() == 2) {
            removePlayerFromTeam();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a player from the selected team
    public void removePlayerFromTeam() {
        System.out.println("\nEnter the number of the team you want to remove this player from");

        printTeams();
        System.out.println("Tip: if this list is empty, you need to add teams first. Enter '0' to return to home");
        int command = input.nextInt();
        if (command > 0) {
            removePlayer(league.getTeamGivenIndex(command - 1));
            System.out.println(" ");
        } else if (input.nextInt() != 0) {
            System.out.println("Invalid command");
        } else {
            returnHome();
        }

    }


    // MODIFIES: this
    // EFFECTS: adds a player to the selected team
    public void addPlayerToTeam() {
        System.out.println("\nEnter the number of the team you want to add this player to");

        printTeams();
        System.out.println("Tip: if this list is empty, you need to add teams first. Enter '0' to return to home");

        int command = input.nextInt();
        if (command > 0) {
            addPlayer(league.getTeamGivenIndex(command - 1));
            returnHome();
        } else if (input.nextInt() != 0) {
            System.out.println("Invalid command");
        } else {
            returnHome();
        }
    }


    // MODIFIES: this
    // EFFECTS: displays edit league menu and allows users to add or remove a team
    public void editLeague() {
        System.out.println("\nSelect one of:");
        System.out.println("\ta -> add new team");
        System.out.println("\tr -> remove a team");
        if (input.next().equals("a")) {
            System.out.println("What is the name of the team you want to add?");
            input.nextLine();
            Team team = new Team(input.nextLine());
            league.addTeam(team);
        } else if (input.next().equals("r")) {
            System.out.println("Enter the number of the team you want to remove");
            printTeams();
            int command = input.nextInt();
            if (command > 1) {
                league.removeTeam(command - 1);
                System.out.println("Team removed");
                returnHome();
            } else {
                System.out.println("Invalid command");
            }

        } else {
            System.out.println("Invalid command");
        }

    }

    // EFFECTS: prints out all players in team with index number
    public void printPlayers(Team team) {
        int i = 0;
        while (i < team.getTeamSize()) {
            System.out.print("\t" + (i + 1) + " -> " + team.getPlayerNameGivenIndex(i));
            System.out.println(" [ " + team.getPlayerGivenIndex(i).getFantasyPoints() + " Fantasy Points ]");
            i++;
        }
    }

    // EFFECTS: prints out all teams in league with index number
    public void printTeams() {
        int i = 0;
        while (i < league.getLeagueSize()) {
            System.out.print("\t" + (i + 1) + " -> " + league.getTeamNameGivenIndex(i));
            System.out.println(" [ " + league.getTeamGivenIndex(i).getTotalFantasyPoints() + " Total Fantasy Points ]");
            i++;
        }
    }

    // EFFECTS: returns to home
    public void returnHome() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayHome();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
                System.exit(0);
            } else {
                processCommand(command);
            }

        }
    }

    // method taken from JsonSerializationDemo
    // EFFECTS: saves the league to file
    private void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.writeLeague(league);
            jsonWriter.close();
            System.out.println("Saved " + league.getLeagueName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // method taken from JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads league from file
    private void loadLeague() {
        try {
            league = jsonReader.read();
            System.out.println("Loaded " + league.getLeagueName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
