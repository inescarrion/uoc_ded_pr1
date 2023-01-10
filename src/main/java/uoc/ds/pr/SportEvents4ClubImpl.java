package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.DictionaryOrderedVector;
import uoc.ds.pr.util.OrderedVector;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class SportEvents4ClubImpl implements SportEvents4Club {

    private OrganizingEntity[] organizingEntities;
    private int numOrganizingEntities;

    private Player[] players;
    private Player mostActivePlayer;
    private int numPlayers;

    private DictionaryOrderedVector<String, SportEvent> sportEvents;

    private OrderedVector<SportEvent> bestSportEvents;

    private QueueArrayImpl<File> files;

    private int numFiles;
    private int numRejectedFiles;

    public SportEvents4ClubImpl() {
        organizingEntities = new OrganizingEntity[MAX_NUM_ORGANIZING_ENTITIES];
        numOrganizingEntities = 0;
        players = new Player[MAX_NUM_PLAYER];
        numPlayers = 0;
        mostActivePlayer = null;
        sportEvents = new DictionaryOrderedVector<String, SportEvent>(MAX_NUM_SPORT_EVENTS, Comparator.naturalOrder());
        bestSportEvents = new OrderedVector<>(MAX_NUM_SPORT_EVENTS, SportEvent.comparator);
        files = new QueueArrayImpl<>();
        numFiles = 0;
        numRejectedFiles = 0;
    }

    @Override
    public void addPlayer(String id, String name, String surname, LocalDate dateOfBirth) {
        Player player = getPlayer(id);
        if (player == null) {
            int pos = numPlayers();
            player = new Player(id, name, surname, dateOfBirth);
            players[pos] = player;
            numPlayers++;
        } else {
            player.setName(name);
            player.setSurname(surname);
            player.setDateOfBirth(dateOfBirth);
        }
    }

    @Override
    public void addOrganizingEntity(int id, String name, String description) {
        OrganizingEntity organizingEntity;
        if (organizingEntities[id] == null) {
            organizingEntity = new OrganizingEntity(id, name, description);
            organizingEntities[id] = organizingEntity;
            numOrganizingEntities++;
        } else {
            organizingEntity = organizingEntities[id];
            organizingEntity.setName(name);
            organizingEntity.setDescription(description);
        }
    }

    @Override
    public void addFile(String id, String eventId, int orgId, String description, Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) throws OrganizingEntityNotFoundException {
        if (getOrganizingEntity(orgId) == null) {
            throw new OrganizingEntityNotFoundException();
        } else {
            File newFile = new File(id, eventId, orgId, description, type, resources, max, startDate, endDate);
            files.add(newFile);
            numFiles++;
        }
    }

    @Override
    public File updateFile(Status status, LocalDate date, String description) throws NoFilesException {
        File file;
        if (files.isEmpty()) {
            throw new NoFilesException();
        } else {
            file = files.poll();
            file.setStatus(status);
            file.setProcessedDate(date);
            if (status == Status.ENABLED) {
                file.setDescription(description);
                // Add event to SportEvents ordered vector
                SportEvent sportEvent = new SportEvent(file.getEventId(), file.getMax());
                sportEvents.put(sportEvent.getEventId(), sportEvent);
                // Add event to its organizingEntity list of events
                OrganizingEntity organizingEntity = getOrganizingEntity(file.getOrgId());
                organizingEntity.addSportEvent(sportEvent);
            } else {
                numRejectedFiles++;
            }
        }
        return file;
    }

    @Override
    public void signUpEvent(String playerId, String eventId) throws PlayerNotFoundException, SportEventNotFoundException, LimitExceededException {
        Player player = getPlayer(playerId);
        SportEvent event = getSportEvent(eventId);
        if (player == null) {
            throw new PlayerNotFoundException();
        } else if (event == null) {
            throw new SportEventNotFoundException();
        } else {
            event.addEnrollment(player);
            if (event.getNumEnrollments() > event.getMaxEnrollments()) {
                throw new LimitExceededException();
            } else {
                player.addSportEvent(event);
            }
        }
        // Update mostActivePlayer if necessary
        if (mostActivePlayer == null) {
            mostActivePlayer = player;
        } else if (player.numEvents() > mostActivePlayer.numEvents()) {
            mostActivePlayer = player;
        }
    }

    @Override
    public double getRejectedFiles() {
        return (double)numRejectedFiles/numFiles;
    }

    @Override
    public Iterator<SportEvent> getSportEventsByOrganizingEntity(int organizationId) throws NoSportEventsException {
        OrganizingEntity organizingEntity = getOrganizingEntity(organizationId);
        if (organizingEntity == null || organizingEntity.getNumSportEvents() == 0) {
            throw new NoSportEventsException();
        } else {
            return organizingEntity.getSportEvents();
        }
    }

    @Override
    public Iterator<SportEvent> getAllEvents() throws NoSportEventsException {
        if(numSportEvents() == 0) {
            throw new NoSportEventsException();
        } else {
            return sportEvents.values();
        }
    }

    @Override
    public Iterator<SportEvent> getEventsByPlayer(String playerId) throws NoSportEventsException {
        Player player = getPlayer(playerId);
        if (player.numEvents() == 0) {
            throw new NoSportEventsException();
        } else {
            return player.getSportEvents();
        }
    }

    @Override
    public void addRating(String playerId, String eventId, Rating rating, String message) throws SportEventNotFoundException, PlayerNotFoundException, PlayerNotInSportEventException {
        Player player = getPlayer(playerId);
        SportEvent event = getSportEvent(eventId);
        if (player == null) {
            throw new PlayerNotFoundException();
        } else if (event == null) {
            throw new SportEventNotFoundException();
        } else {
            Iterator<SportEvent> playerEvents = player.getSportEvents();
            boolean found = false;
            while (!found && playerEvents.hasNext()) {
                if (Objects.equals(playerEvents.next().getEventId(), eventId)) {
                    found = true;
                }
            }
            if (!found) {
                throw new PlayerNotInSportEventException();
            } else {
                uoc.ds.pr.model.Rating eventRating = new uoc.ds.pr.model.Rating(player, rating, message);
                event.addRating(eventRating);
                if (!bestSportEvents.update(event)) {
                    bestSportEvents.put(event);
                }
            }
        }
    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws SportEventNotFoundException, NoRatingsException {
        SportEvent event = getSportEvent(eventId);
        if (event == null) {
            throw new SportEventNotFoundException();
        } else if (event.getNumRatings() == 0) {
            throw new NoRatingsException();
        } else {
            return event.getRatings();
        }
    }

    @Override
    public Player mostActivePlayer() throws PlayerNotFoundException {
        if (mostActivePlayer == null) {
            throw new PlayerNotFoundException();
        } else {
            return mostActivePlayer;
        }
    }

    @Override
    public SportEvent bestSportEvent() throws SportEventNotFoundException {
        if (bestSportEvents.isEmpty()) {
            throw new SportEventNotFoundException();
        } else {
            return bestSportEvents.getFirstElement();
        }
    }

    @Override
    public int numPlayers() {
        return numPlayers;
    }

    @Override
    public int numOrganizingEntities() {
        return numOrganizingEntities;
    }

    @Override
    public int numFiles() {
        return numFiles;
    }

    @Override
    public int numRejectedFiles() {
        return numRejectedFiles;
    }

    @Override
    public int numPendingFiles() {
        return files.size();
    }

    @Override
    public int numSportEvents() {
        return sportEvents.size();
    }

    @Override
    public int numSportEventsByPlayer(String playerId) {
        return getPlayer(playerId).numEvents();
    }

    @Override
    public int numPlayersBySportEvent(String sportEventId) {
        return getSportEvent(sportEventId).getNumEnrollments();
    }

    @Override
    public int numSportEventsByOrganizingEntity(int orgId) {
        return getOrganizingEntity(orgId).getNumSportEvents();
    }

    @Override
    public int numSubstitutesBySportEvent(String sportEventId) {
        int totalPlayers = numPlayersBySportEvent(sportEventId);
        int maxPlayers = getSportEvent(sportEventId).getMaxEnrollments();
        return totalPlayers > maxPlayers? totalPlayers - maxPlayers : 0;
    }

    @Override
    public Player getPlayer(String playerId) {
        boolean found = false;
        int i = 0;
        while (!found && i < numPlayers) {
            if (Objects.equals(players[i].getId(), playerId)) {
                found = true;
            } else {
                i++;
            }
        }
        return found? players[i] : null;
    }

    @Override
    public SportEvent getSportEvent(String eventId) {
        return sportEvents.get(eventId);
    }

    @Override
    public OrganizingEntity getOrganizingEntity(int id) {
        return id < MAX_NUM_ORGANIZING_ENTITIES? organizingEntities[id] : null;
    }

    @Override
    public File currentFile() {
        return files.peek();
    }
}
