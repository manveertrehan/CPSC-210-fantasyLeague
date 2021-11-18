package persistence;

import model.League;
import model.Team;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            League lg = new League("My league");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League lg = new League("My league");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLeague.json");
            writer.open();
            writer.writeLeague(lg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLeague.json");
            lg = reader.read();
            assertEquals(0, lg.getLeagueSize());
            assertEquals("My league", lg.getLeagueName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLeague() {
        try {
            League lg = new League("My league");
            Team tm1 = new Team("Raptors");
            Player plyr1 = new Player("Kawhi", 0);
            tm1.addPlayer(plyr1);
            Team tm2 = new Team("Lakers");
            lg.addTeam(tm1);
            lg.addTeam(tm2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLeague.json");
            writer.open();
            writer.writeLeague(lg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLeague.json");
            lg = reader.read();
            List<Team> league = lg.getTeams();
            assertEquals(2, league.size());
            assertEquals("My league", lg.getLeagueName());
            assertEquals("Raptors", league.get(0).getTeamName());
            assertEquals("Kawhi", league.get(0).getPlayerNameGivenIndex(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}