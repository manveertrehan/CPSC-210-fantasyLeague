package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.*;

// Represents a league with a name and empty list of teams
public class League implements Writeable {
    public List<Team> league;
    private String name;

    // EFFECTS: constructs an empty team
    public League(String name) {
        league = new ArrayList<>();
        this.name = name;
    }

    // EFFECTS: returns name
    public String getLeagueName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds a team to the league if it is not already
    // in the league
    public void addTeam(Team team) {
        league.add(team);
    }

    // MODIFIES: this
    // EFFECTS: removes a team from the league
    public void removeTeam(int i) {
        league.remove(i);
    }

    // EFFECTS: returns the team in position i
    public Team getTeamGivenIndex(int i) {
        return league.get(i);
    }

    // EFFECTS: returns the name of the team in position i
    public String getTeamNameGivenIndex(int i) {
        if (i < league.size()) {
            return league.get(i).getTeamName();
        }
        return "empty team spot --- add more teams!";
    }

    // EFFECTS: return size of league
    public int getLeagueSize() {
        return league.size();
    }

    // method taken from JsonSerializationDemo
    // EFFECTS: returns league as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("teams", leagueToJson());
        return json;
    }

    // method taken from JsonSerializationDemo
    // EFFECTS: returns teams in this league as a JSON array
    private JSONArray leagueToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : league) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // method taken from JsonSerializationDemo
    // EFFECTS: returns an unmodifiable list of teams in this league
    public List<Team> getTeams() {
        return Collections.unmodifiableList(league);
    }
}
