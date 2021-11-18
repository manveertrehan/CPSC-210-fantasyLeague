package persistence;

import model.League;
import model.Team;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            League league = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLeague() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLeague.json");
        try {
            League league = reader.read();
            assertEquals("My league", league.getLeagueName());
            assertEquals(0, league.getLeagueSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLeague() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLeague.json");
        try {
            League league = reader.read();
            assertEquals("My league", league.getLeagueName());
            List<Team> teams = league.getTeams();
            assertEquals(2, league.getLeagueSize());
            assertEquals("Raptors", league.getTeamNameGivenIndex(0));
            assertEquals("Kawhi", league.getTeamGivenIndex(0).getPlayerNameGivenIndex(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}