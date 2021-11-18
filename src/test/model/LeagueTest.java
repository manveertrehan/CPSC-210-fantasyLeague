package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeagueTest {

    private League league1;
    private Team team1;
    private Team team2;

    @BeforeEach
    public void setup() {
        league1 = new League("");
        team1 = new Team("");
        team2 = new Team("");

        league1.addTeam(team1);
        league1.addTeam(team2);
    }

    @Test
    public void testGetTeamNameGivenIndexOutOfRange() {
        assertEquals("empty team spot --- add more teams!", league1.getTeamNameGivenIndex(league1.getLeagueSize() + 2));
    }

    @Test
    public void testGetTeamNameGivenIndexInRange() {
        assertEquals(team1.getTeamName(), league1.getTeamNameGivenIndex(0));
    }

    @Test
    public void testGetTeamGivenIndexInRange() {
        assertEquals(team1, league1.getTeamGivenIndex(0));
    }

    @Test
    public void testGetTeamGivenIndex() {
        assertEquals(team1, league1.getTeamGivenIndex(0));
    }

    @Test
    public void testGetLeagueSize() {
        League league2 = new League("");
        league2.addTeam(team2);

        league1.removeTeam(1);
        assertEquals(league1.getLeagueSize(), league2.getLeagueSize());
    }
}
