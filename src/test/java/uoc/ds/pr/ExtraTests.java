package uoc.ds.pr;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.util.ResourceUtil;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class ExtraTests {
    private SportEvents4Club sportEvents4Club;

    @Before
    public void setUp() {
        this.sportEvents4Club = new SportEvents4ClubImpl();
    }

    @After
    public void tearDown() {
        this.sportEvents4Club = null;
    }


    @Test
    public void updateFileEmptyQueue() {
        // Check if an exception is thrown when trying to update a file and the files queue is empty
        Assert.assertThrows(NoFilesException.class, ()->
                this.sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("12-11-2021"), "X"));
    }


    @Test
    public void signUpEventPlayerException() {
        // Check if an exception is thrown when nonexistent player tries to sign up in a sport event
        Assert.assertThrows(PlayerNotFoundException.class, ()->
                this.sportEvents4Club.signUpEvent("X", "X"));
    }


    @Test
    public void signUpEventSportEventException() {
        // Add player
        this.sportEvents4Club.addPlayer("P01", "Antón", "Loureiro", createLocalDate("21-12-1998"));

        // Check if an exception is thrown when the player tries to sign up in nonexistent sport event
        Assert.assertThrows(SportEventNotFoundException.class, ()->
                this.sportEvents4Club.signUpEvent("P01", "X"));
    }


    @Test
    public void addRatingPlayerException() {
        // Check if an exception is thrown when a nonexistent player tries to add rating
        Assert.assertThrows(PlayerNotFoundException.class, ()->
                this.sportEvents4Club.addRating("X", "X", SportEvents4Club.Rating.ONE, "X"));
    }


    @Test
    public void addRatingSportEventException() {
        // Add player
        this.sportEvents4Club.addPlayer("P01", "Antón", "Loureiro", createLocalDate("21-12-1998"));

        // Check if an exception is thrown when the player tries to add rating to nonexistent sport event
        Assert.assertThrows(SportEventNotFoundException.class, ()->
                this.sportEvents4Club.addRating("P01", "X", SportEvents4Club.Rating.ONE, "X"));
    }


    @Test
    public void addRatingPlayerNotInEventException() throws DSException {
        // Add player and organizing entity
        this.sportEvents4Club.addPlayer("P01", "Antón", "Loureiro", createLocalDate("21-12-1998"));
        this.sportEvents4Club.addOrganizingEntity(1, "Eirís S.D.", "Football club");

        // Add 1 file and update it to create a sport event
        byte res = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        sportEvents4Club.addFile("F01", "SE01", 1,"description",
                SportEvents4Club.Type.MICRO, res,
                20, createLocalDate("22-01-2021"), createLocalDate("12-04-2022"));
        sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("12-04-2021"), "X");

        // Check if an exception is thrown when the player tries to add rating to the event without being registered in it
        Assert.assertThrows(PlayerNotInSportEventException.class, ()->
                this.sportEvents4Club.addRating("P01", "SE01", SportEvents4Club.Rating.ONE, "X"));
    }


    @Test
    public void getRatingsByEventException() {
        // Check if an exception is thrown when trying to add rating to nonexistent sport event
        Assert.assertThrows(SportEventNotFoundException.class, ()->
                this.sportEvents4Club.getRatingsByEvent("X"));
    }


    @Test
    public void bestSportEventException() {
        // Check if an exception is thrown when best rated sport event is looked up because bestSportEvent vector is empty
        Assert.assertThrows(SportEventNotFoundException.class, ()->
                this.sportEvents4Club.bestSportEvent());
    }

    @Test
    public void sameRatingTest() throws DSException{
        // Add player and organizing entity
        this.sportEvents4Club.addPlayer("P01", "Antón", "Loureiro", createLocalDate("21-12-1998"));
        this.sportEvents4Club.addOrganizingEntity(1, "Eirís S.D.", "Football club");

        // Add 2 files and update them to create a sport event
        byte res = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        sportEvents4Club.addFile("F01", "SE01", 1,"description",
                SportEvents4Club.Type.MICRO, res,
                20, createLocalDate("22-01-2021"), createLocalDate("12-04-2022"));
        sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("12-04-2021"), "X");
        sportEvents4Club.addFile("F01", "SE02", 1,"description",
                SportEvents4Club.Type.MICRO, res,
                20, createLocalDate("22-01-2021"), createLocalDate("12-04-2022"));
        sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("12-04-2021"), "X");

        // Sign up player in both sport events
        sportEvents4Club.signUpEvent("P01", "SE01");
        sportEvents4Club.signUpEvent("P01", "SE02");

        // The player adds the same rating to both sport events
        sportEvents4Club.addRating("P01", "SE01", SportEvents4Club.Rating.FIVE, "X");
        sportEvents4Club.addRating("P01", "SE02", SportEvents4Club.Rating.FIVE, "X");

        // Check if best rated sport event is the first rated (SE01) despite the tie
        Assert.assertEquals("SE01", this.sportEvents4Club.bestSportEvent().getEventId());
    }


    @Test
    public void substituteSportEventsList() throws DSException {
        // Add 2 players and 1 organizing entity
        this.sportEvents4Club.addPlayer("P01", "Antón", "Loureiro", createLocalDate("21-12-1998"));
        this.sportEvents4Club.addPlayer("P02", "Ángel", "Loureiro", createLocalDate("12-06-1994"));
        this.sportEvents4Club.addOrganizingEntity(1, "Eirís S.D.", "Football club");

        // Add 1 file and update it to create a sport event
        byte res = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        sportEvents4Club.addFile("F01", "SE01", 1,"description",
                SportEvents4Club.Type.MICRO, res,
                1, createLocalDate("22-01-2021"), createLocalDate("12-04-2022"));
        sportEvents4Club.updateFile(SportEvents4Club.Status.ENABLED, createLocalDate("12-04-2021"), "X");

        // Sign up 2 players (LimitExceededException thrown because the second is substitute)
        sportEvents4Club.signUpEvent("P01", "SE01");
        Assert.assertThrows(LimitExceededException.class, ()->
                sportEvents4Club.signUpEvent("P02", "SE01"));

        // Check if there are 2 players in that sport event and 1 of them is a substitute
        Assert.assertEquals(2, this.sportEvents4Club.numPlayersBySportEvent("SE01"));
        Assert.assertEquals(1, this.sportEvents4Club.numSubstitutesBySportEvent("SE01"));

        // Check if the first player has 1 sport event in the list
        Assert.assertEquals(1, this.sportEvents4Club.getPlayer("P01").numEvents());
        Assert.assertTrue(this.sportEvents4Club.getPlayer("P01").getSportEvents().hasNext());

        // Check if the second player has no sport events in the list as it was signed up as a substitute
        Assert.assertEquals(0, this.sportEvents4Club.getPlayer("P02").numEvents());
        Assert.assertFalse(this.sportEvents4Club.getPlayer("P02").getSportEvents().hasNext());

    }

}
