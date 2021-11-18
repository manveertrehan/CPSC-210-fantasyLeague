package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    private Team team1;
    private Team team2;
    private Player plyr1;
    private Player plyr2;
    private Player plyr3;


    @BeforeEach
    public void setup() {
        team1 = new Team("");
        team2 = new Team("");

        plyr1 = new Player("lebron",0);
        plyr2 = new Player("kawhi",0);
        plyr3 = new Player("durant",0);

        plyr1.addStats(30, 5, 5);
        plyr2.addStats(20, 0, 0);
        plyr3.addStats(10, 0, 0);

        team1.addPlayer(plyr1);
        team1.addPlayer(plyr2);
    }

    @Test
    public void testGetTotalFantasyPoints() {
        assertEquals(plyr1.getFantasyPoints() + plyr2.getFantasyPoints(), team1.getTotalFantasyPoints());
    }

    @Test
    void testAddPlayer() {
        team1.addPlayer(plyr1);
        assertEquals(2, team1.getTeamSize());
        team2.addPlayer(plyr1);
        assertEquals(1, team1.getTeamSize());
        assertEquals(1, team2.getTeamSize());
    }

    @Test
    public void testGetPlayerGivenIndex() {
        assertEquals(plyr1, team1.getPlayerGivenIndex(0));
    }

    @Test
    public void testGetPlayerNameGivenIndexInRange() {
        assertEquals(plyr1.getPlayerName(), team1.getPlayerNameGivenIndex(0));
    }

    @Test
    public void testGetPlayerNameGivenIndexOutOfRange() {
        assertEquals("empty roster spot --- add more players!", team1.getPlayerNameGivenIndex(team1.getTeamSize() + 2));
    }

    @Test
    public void testRemovePlayerAtIndex() {
        Team team2 = new Team("");
        team2.addPlayer(plyr3);
        team1.removePlayerAtIndex(1);
        assertEquals(team2.getTeamSize(), team1.getPlayers().size());
    }
}
