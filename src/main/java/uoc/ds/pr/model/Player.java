package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;

import java.time.LocalDate;

public class Player {
    private final String id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private LinkedList<SportEvent> sportEvents;
    private int numEvents;

    public Player(String id, String name, String surname, LocalDate dateOfBirth) {
        this.id = id;
        setName(name);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        sportEvents = new LinkedList<>();
        numEvents = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Iterator<SportEvent> getSportEvents() {
        return sportEvents.values();
    }

    public void addSportEvent(SportEvent sportEvent) {
        sportEvents.insertEnd(sportEvent);
        numEvents++;
    }

    public int numEvents() {
        return numEvents;
    }
}
