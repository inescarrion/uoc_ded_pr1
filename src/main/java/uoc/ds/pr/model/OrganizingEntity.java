package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;

public class OrganizingEntity {
    private final int organizingEntityId;
    private String name;
    private String description;
    private LinkedList<SportEvent> sportEvents;
    private int numSportEvents;

    public OrganizingEntity(int organizingEntityId, String name, String description) {
        this.organizingEntityId = organizingEntityId;
        setName(name);
        setDescription(description);
        sportEvents = new LinkedList<>();
        numSportEvents = 0;
    }

    public int getOrganizingEntityId() {
        return organizingEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Iterator<SportEvent> getSportEvents() {
        return sportEvents.values();
    }

    public void addSportEvent(SportEvent sportEvent) {
        sportEvents.insertEnd(sportEvent);
        numSportEvents++;
    }

    public int getNumSportEvents() {
        return numSportEvents;
    }
}
