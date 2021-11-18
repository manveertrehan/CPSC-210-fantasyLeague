package persistence;

import model.League;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// class and method structure taken from JsonSerializationDemo

// represents a reader that reads Json data
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads league from file and returns it;
    // throws IOException if an error occurs reading data from file
    public League read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses League from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        League lg = new League(name);
        addTeams(lg, jsonObject);
        return lg;
    }

    // MODIFIES: lg
    // EFFECTS: parses teams from JSON object and adds them to league
    private void addTeams(League lg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(lg, nextTeam);
        }
    }

    // MODIFIES: lg
    // EFFECTS: parses team from JSON object and adds it to league
    private void addTeam(League lg, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        lg.addTeam(parseTeam(team, jsonObject));
    }

    // EFFECTS: parses team from JSON object and returns it
    private Team parseTeam(Team team, JSONObject jsonObject) {
        addPlayers(team, jsonObject);
        return team;
    }

    // MODIFIES: tm
    // EFFECTS: parses players from JSON object and adds them to team
    private void addPlayers(Team tm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(tm, nextPlayer);
        }
    }

    // MODIFIES: tm
    // EFFECTS: parses player from JSON object and adds it to team
    private void addPlayer(Team tm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int fantasyPoints = jsonObject.getInt("fantasyPoints");
        Player plyr = new Player(name, fantasyPoints);
        tm.addPlayer(plyr);
    }
}
