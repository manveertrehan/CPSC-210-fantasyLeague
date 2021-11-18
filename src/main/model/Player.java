package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.util.Objects;

// represents a player with a name and fantasy points
public class Player implements Writeable {

    private static int ASSIST_WORTH = 2;
    private static int REBOUND_WORTH = 1;

    private String name;
    private int fantasyPoints;
    private int points;
    private int assists;
    private int rebounds;

    private Team team;

    // REQUIRES: playerName has a non-zero length
    // EFFECTS: constructs a player with name and initial amount of fantasy points
    public Player(String playerName, int fanPoints) {
        fantasyPoints = fanPoints;
        this.name = playerName;
    }

    // EFFECTS: assigns a player to a team
    public void setTeam(Team t) {
        if (t != team && t != null) {
            if (team != null) {
                team.removePlayer(this);
            }
            team = t;
            team.addPlayer(this);
        } else if (t == null && team != null) {
            team.removePlayer(this);
            team = null;
        }
    }

    // EFFECTS: returns the team this player is on if it is on one
    public Team getTeam() {
        if (team != null) {
            return team;
        }
        return null;
    }

    // EFFECTS: returns name of player
    public String getPlayerName() {
        return name;
    }

    // EFFECTS: returns fantasypoints of player
    public int getFantasyPoints() {
        return fantasyPoints;
    }

    // REQUIRES: points is >= 0
    // MODIFIES: this
    // EFFECTS: adds points stat to the player's fantasy points
    public void addPoints(int points) {
        this.points += points;
        fantasyPoints =  fantasyPoints + points;
    }

    // REQUIRES: assists is >= 0
    // MODIFIES: this
    // EFFECTS: adds assists stat to the player's fantasy points
    //          with appropriate assist value
    public void addAssists(int assists) {
        this.assists += assists;
        fantasyPoints =  fantasyPoints + (assists * ASSIST_WORTH);
    }

    // REQUIRES: rebounds is >= 0
    // MODIFIES: this
    // EFFECTS: adds rebounds stat to the player's fantasy points
    //          with appropriate assist value
    public void addRebounds(int rebounds) {
        this.rebounds += rebounds;
        fantasyPoints = fantasyPoints + (rebounds * REBOUND_WORTH);
    }

    // REQUIRES: int points, int assists, int rebounds are >= 0
    // MODIFIES: this
    // EFFECTS: adds points, rebounds, and assists stats
    //          to the player's fantasy points with
    //          appropriate assist and rebound value
    public void addStats(int points, int assists, int rebounds) {
        fantasyPoints = fantasyPoints + points + (assists * ASSIST_WORTH) + (rebounds * REBOUND_WORTH);
    }

    // method taken from JsonSerializationDemo
    // EFFECTS: returns player as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("fantasyPoints", fantasyPoints);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }
}
