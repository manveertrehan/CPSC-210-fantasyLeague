package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player plyr1;
    private Team team1;
    private Team team2;

    @BeforeEach
    public void setup() {
        plyr1 = new Player("Kawhi",0);
        team1 = new Team("Raptors");
        team2 = new Team("Clippers");
    }

    @Test
    void testConstructor() {
        assertNotEquals(plyr1, null);
        assertEquals(plyr1, new Player("Kawhi", 10));
        assertNotEquals(plyr1, team1);
    }

    @Test
    public void testAddPoints() {
        plyr1.addPoints(28);
        assertEquals(28, plyr1.getFantasyPoints());
    }

    @Test
    public void testAddAssists() {
        plyr1.addAssists(8);
        assertEquals(8 * 2, plyr1.getFantasyPoints());
    }

    @Test
    public void testAddRebounds() {
        plyr1.addRebounds(7);
        assertEquals(7 * 1, plyr1.getFantasyPoints());
    }

    @Test
    public void testAddStats() {
        plyr1.addStats(28, 8, 7);
        assertEquals(28 + (8 * 2) + (7 * 1), plyr1.getFantasyPoints());
    }

    @Test
    public void testGetName() {
        assertEquals("Kawhi", plyr1.getPlayerName());
    }

    @Test
    void testSetTeam() {
        plyr1.setTeam(null);
        assertNull(plyr1.getTeam());

        plyr1.setTeam(team1);
        assertEquals(team1, plyr1.getTeam());

        plyr1.setTeam(team2);
        assertEquals(team2, plyr1.getTeam());

        plyr1.setTeam(null);
        assertNull(plyr1.getTeam());
    }
}