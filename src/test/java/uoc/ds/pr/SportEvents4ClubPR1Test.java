package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.Player;
import uoc.ds.pr.model.Rating;
import uoc.ds.pr.model.File;
import uoc.ds.pr.model.SportEvent;
import uoc.ds.pr.util.ResourceUtil;

import static uoc.ds.pr.SportEvents4Club.MAX_NUM_ORGANIZING_ENTITIES;
import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class SportEvents4ClubPR1Test {

    private SportEvents4Club sportEvents4Club;

    @Before
    public void setUp() throws Exception {
        this.sportEvents4Club = FactorySportEvents4Club.getSportEvents4Club();
    }

    @After
    public void tearDown() {
        this.sportEvents4Club = null;
    }


    public void initialState() {
        Assert.assertEquals(12, this.sportEvents4Club.numPlayers());
        Assert.assertEquals(5, this.sportEvents4Club.numOrganizingEntities());
        Assert.assertEquals(6, this.sportEvents4Club.numFiles());
        Assert.assertEquals(1, this.sportEvents4Club.numPendingFiles());

        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer1"));
        Assert.assertEquals(3, this.sportEvents4Club.numSportEventsByPlayer("idPlayer2"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer3"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer4"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer5"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer6"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer7"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer8"));
        Assert.assertEquals(1, this.sportEvents4Club.numSportEventsByPlayer("idPlayer9"));

        Assert.assertEquals(7, this.sportEvents4Club.numPlayersBySportEvent("EV-1101"));
        Assert.assertEquals(3, this.sportEvents4Club.numPlayersBySportEvent("EV-1102"));
    }

    @Test
    public void addPlayerTest() {

        // GIVEN:
        initialState();
        //

        this.sportEvents4Club.addPlayer("idPlayer1000", "Robert", "Lopez", createLocalDate("02-01-1942"));
        Assert.assertEquals("Robert", this.sportEvents4Club.getPlayer("idPlayer1000").getName());
        Assert.assertEquals(13, this.sportEvents4Club.numPlayers());

        this.sportEvents4Club.addPlayer("idPlayer9999", "XXXXX", "YYYYY", createLocalDate("12-11-1962"));
        Assert.assertEquals("XXXXX", this.sportEvents4Club.getPlayer("idPlayer9999").getName());
        Assert.assertEquals(14, this.sportEvents4Club.numPlayers());

        this.sportEvents4Club.addPlayer("idPlayer9999", "Lluis", "Casals", createLocalDate("22-07-1938"));
        Assert.assertEquals("Lluis", this.sportEvents4Club.getPlayer("idPlayer9999").getName());
        Assert.assertEquals("Casals", this.sportEvents4Club.getPlayer("idPlayer9999").getSurname());
        Assert.assertEquals(14, this.sportEvents4Club.numPlayers());
    }


    @Test
    public void addOrganizingEntityTest() {
        // GIVEN:
        initialState();
        //
        this.sportEvents4Club.addOrganizingEntity(15, "ORG_VDA", "description VDA" );
        Assert.assertEquals("ORG_VDA", this.sportEvents4Club.getOrganizingEntity(15).getName());
        Assert.assertEquals("description VDA", this.sportEvents4Club.getOrganizingEntity(15).getDescription());
        Assert.assertEquals(6, this.sportEvents4Club.numOrganizingEntities());

        this.sportEvents4Club.addOrganizingEntity(17, "ORG_XXX", "description XXX" );
        Assert.assertEquals("ORG_XXX", this.sportEvents4Club.getOrganizingEntity(17).getName());
        Assert.assertEquals("description XXX", this.sportEvents4Club.getOrganizingEntity(17).getDescription());
        Assert.assertEquals(7, this.sportEvents4Club.numOrganizingEntities());

        this.sportEvents4Club.addOrganizingEntity(17, "ORG_AWS", "description AW" );
        Assert.assertEquals("ORG_AWS", this.sportEvents4Club.getOrganizingEntity(17).getName());
        Assert.assertEquals("description AW", this.sportEvents4Club.getOrganizingEntity(17).getDescription());
        Assert.assertEquals(7, this.sportEvents4Club.numOrganizingEntities());
    }

    @Test
    public void addFileTest() throws DSException {
        // GIVEN:
        initialState();
        //
        File file=null;

        byte resources1 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,  SportEvents4Club.FLAG_VOLUNTEERS);
        this.sportEvents4Club.addFile("XXX-001", "EV-010", 1,"description EV-010",
                SportEvents4Club.Type.MEDIUM, resources1, 50,
                        createLocalDate("22-11-2022"), createLocalDate("28-11-2022"));

        Assert.assertEquals(2, this.sportEvents4Club.numPendingFiles() );
        Assert.assertEquals(7, this.sportEvents4Club.numFiles() );

        byte resources2 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,  SportEvents4Club.FLAG_VOLUNTEERS);
        this.sportEvents4Club.addFile("XXX-002", "EV-011", 1,"description EV-011",
                SportEvents4Club.Type.LARGE, resources2,
                100, createLocalDate("25-11-2022"),createLocalDate("25-12-2022"));

        Assert.assertEquals(3, this.sportEvents4Club.numPendingFiles() );
        Assert.assertEquals(8, this.sportEvents4Club.numFiles() );

        file = this.sportEvents4Club.currentFile();
        Assert.assertEquals("F-006", file.getFileId() );
        Assert.assertEquals(SportEvents4Club.Status.PENDING, file.getStatus() );

        byte resources3 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,  SportEvents4Club.FLAG_VOLUNTEERS);

        Assert.assertThrows(OrganizingEntityNotFoundException.class, ()->
                this.sportEvents4Club.addFile("XXX-002", "EV-011", 999, "description EV-011",
                        SportEvents4Club.Type.MICRO, resources3,
                        5, createLocalDate("25-11-2022"), createLocalDate("25-12-2022")));

        Assert.assertThrows(OrganizingEntityNotFoundException.class, ()->
                        this.sportEvents4Club.addFile("XXX-002", "EV-011", MAX_NUM_ORGANIZING_ENTITIES, "description EV-011",
                                SportEvents4Club.Type.MICRO, resources3,
                                5, createLocalDate("25-11-2022"), createLocalDate("25-12-2022")));

        Assert.assertThrows(OrganizingEntityNotFoundException.class, ()->
                this.sportEvents4Club.addFile("XXX-002", "EV-011", 15, "description EV-011",
                        SportEvents4Club.Type.MICRO, resources3,
                        5, createLocalDate("25-11-2022"), createLocalDate("25-12-2022")));

    }

    @Test
    public void updateFileTest() throws DSException {

        // GIVEN:
        initialState();
        //

        this.addFileTest();

        Assert.assertEquals(12, this.sportEvents4Club.numPlayers());
        Assert.assertEquals(5, this.sportEvents4Club.numOrganizingEntities());
        Assert.assertEquals(8, this.sportEvents4Club.numFiles() );
        Assert.assertEquals(3, this.sportEvents4Club.numPendingFiles() );
        Assert.assertEquals(1, this.sportEvents4Club.numRejectedFiles() );
        Assert.assertEquals(4, this.sportEvents4Club.numSportEvents());
        //
        File record=null;

        Assert.assertEquals(0.12, this.sportEvents4Club.getRejectedFiles(),0.03);

        this.sportEvents4Club.updateFile(SportEvents4Club.Status.DISABLED,
                createLocalDate("25-11-2022"), "KO X1");

        Assert.assertEquals(2, this.sportEvents4Club.numRejectedFiles() );
        Assert.assertEquals(8, this.sportEvents4Club.numFiles() );
        Assert.assertEquals(0.25, this.sportEvents4Club.getRejectedFiles(),0.03);
    }

    @Test
    public void signUpEventTest() throws DSException {
        // GIVEN:
        initialState();
        //
        Assert.assertEquals(1, this.sportEvents4Club.numPlayersBySportEvent("EV-1104"));

        sportEvents4Club.signUpEvent("idPlayer1", "EV-1104");
        sportEvents4Club.signUpEvent("idPlayer2", "EV-1104");
        sportEvents4Club.signUpEvent("idPlayer3", "EV-1104");
        sportEvents4Club.signUpEvent("idPlayer4", "EV-1104");

        Assert.assertEquals(5, this.sportEvents4Club.numPlayersBySportEvent("EV-1104"));

        Assert.assertThrows(LimitExceededException.class, ()->
                sportEvents4Club.signUpEvent("idPlayer6", "EV-1104"));

        Assert.assertEquals(6, this.sportEvents4Club.numPlayersBySportEvent("EV-1104"));

        Assert.assertThrows(LimitExceededException.class, ()->
                sportEvents4Club.signUpEvent("idPlayer7", "EV-1104"));

        Assert.assertEquals(2, this.sportEvents4Club.numSubstitutesBySportEvent("EV-1104"));
    }

    @Test
    public void getEventsByOrganizingEntityTest() throws DSException {
        // GIVEN:
        initialState();
        //

        Assert.assertEquals(2, this.sportEvents4Club.numSportEventsByOrganizingEntity(1));

        Iterator<SportEvent> it = this.sportEvents4Club.getSportEventsByOrganizingEntity(1);
        SportEvent sportEvent1 = it.next();
        Assert.assertEquals("EV-1101", sportEvent1.getEventId());

        SportEvent sportEvent2 = it.next();
        Assert.assertEquals("EV-1102", sportEvent2.getEventId());

        Assert.assertThrows(NoSportEventsException.class, ()->
                this.sportEvents4Club.getSportEventsByOrganizingEntity(2));
    }

    @Test
    public void getAllEventsTest() throws DSException {
        // GIVEN:
        initialState();
        //
        Iterator<SportEvent> it = this.sportEvents4Club.getAllEvents();

        SportEvent sportEvent1 = it.next();
        Assert.assertEquals("EV-1101", sportEvent1.getEventId());

        SportEvent sportEvent2 = it.next();
        Assert.assertEquals("EV-1102", sportEvent2.getEventId());

        SportEvent sportEvent3 = it.next();
        Assert.assertEquals("EV-1104", sportEvent3.getEventId());

        Assert.assertThrows(NoSportEventsException.class, ()->
                this.sportEvents4Club.getEventsByPlayer("idPlayer10"));;


    }

    @Test
    public void getSportEventsByPlayer() throws DSException {
        // GIVEN:
        initialState();
        //
        Iterator<SportEvent> it = this.sportEvents4Club.getEventsByPlayer("idPlayer1");

        SportEvent sportEvent = it.next();
        Assert.assertEquals("EV-1101", sportEvent.getEventId());
    }


    @Test
    public void mostActivePlayer() throws DSException{
        Player player = sportEvents4Club.mostActivePlayer();
        Assert.assertEquals("idPlayer2", player.getId());
        Assert.assertEquals(3, player.numEvents());

        sportEvents4Club.signUpEvent("idPlayer1", "EV-1102");
        sportEvents4Club.signUpEvent("idPlayer1", "EV-1105");
        sportEvents4Club.signUpEvent("idPlayer1", "EV-1104");

        player = sportEvents4Club.mostActivePlayer();
        Assert.assertEquals("idPlayer1", player.getId());
        Assert.assertEquals(4, player.numEvents());
    }

    @Test
    public void addRatingAndBestEventTest() throws DSException {
        // GIVEN:
        initialState();
        //

        Assert.assertThrows(NoRatingsException.class, ()->
                sportEvents4Club.getRatingsByEvent("EV-1101"));
        Assert.assertThrows(NoRatingsException.class, ()->
                sportEvents4Club.getRatingsByEvent("EV-1102"));

        SportEvent sportEvent1101 = this.sportEvents4Club.getSportEvent("EV-1101");
        SportEvent sportEvent1102 = this.sportEvents4Club.getSportEvent("EV-1102");

        Assert.assertEquals(0, sportEvent1101.rating(),0);

        this.sportEvents4Club.addRating("idPlayer1","EV-1101",
                SportEvents4Club.Rating.FIVE, "Very good");

        Assert.assertEquals(5, sportEvent1101.rating(),0);

        this.sportEvents4Club.addRating("idPlayer3", "EV-1101",
                SportEvents4Club.Rating.FOUR, "Good");

        Assert.assertEquals(4.5, sportEvent1101.rating(),0);

        this.sportEvents4Club.addRating("idPlayer2","EV-1102",
                SportEvents4Club.Rating.TWO, "CHIPI - CHAPI");
        Assert.assertEquals(2, sportEvent1102.rating(),0.09);

        SportEvent bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());
        Assert.assertEquals(4.5, bestSportEvent.rating(),0);
        Assert.assertSame(bestSportEvent, sportEvent1101);
        Assert.assertEquals(4.5, sportEvent1101.rating(),0);
        Assert.assertEquals(2, sportEvent1102.rating(),0);
        //
        //

        this.sportEvents4Club.addRating("idPlayer4", "EV-1101",
                SportEvents4Club.Rating.FOUR, "Good!!!");
        Assert.assertEquals(4.3, sportEvent1101.rating(),0.1);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());

        this.sportEvents4Club.addRating("idPlayer5", "EV-1101",
                SportEvents4Club.Rating.ONE, "Bad!!!");
        Assert.assertEquals(3.5, sportEvent1101.rating(),0.09);
        Assert.assertEquals(2, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1101);

        this.sportEvents4Club.addRating("idPlayer6", "EV-1101",
                SportEvents4Club.Rating.FOUR, "Good!!!");
        Assert.assertEquals(3.6, sportEvent1101.rating(),0.09);
        Assert.assertEquals(2, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1101);

        this.sportEvents4Club.addRating("idPlayer7", "EV-1101",
                SportEvents4Club.Rating.FIVE, "Very Good!!!");
        Assert.assertEquals(3.83, sportEvent1101.rating(),0.1);
        Assert.assertEquals(2, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1101);

        this.sportEvents4Club.addRating("idPlayer7", "EV-1101",
                SportEvents4Club.Rating.FIVE, "Very Good!!!");
        Assert.assertEquals(4, sportEvent1101.rating(),0);
        Assert.assertEquals(2, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1101", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1101);

        this.sportEvents4Club.addRating("idPlayer8", "EV-1102",
                SportEvents4Club.Rating.FIVE, "Very Good!!!");

        Assert.assertEquals(4, sportEvent1101.rating(),0);
        Assert.assertEquals(3.5, sportEvent1102.rating(),0.09);

        this.sportEvents4Club.addRating("idPlayer1", "EV-1101",
                SportEvents4Club.Rating.ONE, "So bored!!!");

        this.sportEvents4Club.addRating("idPlayer2", "EV-1102",
                SportEvents4Club.Rating.FIVE, "Very Good!!!");

        Assert.assertEquals(3.62, sportEvent1101.rating(),0.1);
        Assert.assertEquals(4, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1102", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1102);

        this.sportEvents4Club.addRating("idPlayer9", "EV-1102",
                SportEvents4Club.Rating.FIVE, "Very Good!!!");

        Assert.assertEquals(3.62, sportEvent1101.rating(),0.1);
        Assert.assertEquals(4.24, sportEvent1102.rating(),0.09);

        bestSportEvent = this.sportEvents4Club.bestSportEvent();
        Assert.assertEquals("EV-1102", bestSportEvent.getEventId());
        Assert.assertSame(bestSportEvent, sportEvent1102);

        Iterator<Rating> it = this.sportEvents4Club.getRatingsByEvent("EV-1101");
        Rating rating = it.next();
        Assert.assertEquals(SportEvents4Club.Rating.FIVE, rating.rating());
        Assert.assertEquals("idPlayer1", rating.getPlayer().getId());
    }
}
