package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;

import java.util.Comparator;

public class SportEvent implements Comparable<SportEvent> {
    public static final Comparator<SportEvent> comparator = (SportEvent s1, SportEvent s2)->Double.compare(s2.rating(), s1.rating());

    private final String eventId;
    private double averageRating;
    private final int maxEnrollments;
    private QueueArrayImpl<Player> enrollments;
    private int numEnrollments;
    private LinkedList<Rating> ratings;
    private int numRatings;

    public SportEvent(String eventId, int maxEnrollments) {
        this.eventId = eventId;
        this.maxEnrollments = maxEnrollments;
        enrollments = new QueueArrayImpl<>();
        numEnrollments = 0;
        ratings = new LinkedList<>();
        numRatings = 0;
        setAverageRating();
    }

    public String getEventId() {
        return eventId;
    }

    public int getMaxEnrollments() {
        return maxEnrollments;
    }

    public double rating() {
        return averageRating;
    }

    private void setAverageRating() {
        double sum = 0.0;
        if (ratings.isEmpty()) {
            averageRating = 0.0;
        } else {
            Iterator<Rating> iterator = ratings.values();
            while (iterator.hasNext()) {
                sum += iterator.next().rating().getValue();
            }
            averageRating = sum/numRatings;
        }
    }

    public void addEnrollment(Player player) {
        enrollments.add(player);
        numEnrollments++;
    }

    public int getNumEnrollments() {
        return numEnrollments;
    }

    public void addRating(Rating rating) {
        ratings.insertEnd(rating);
        numRatings++;
        setAverageRating();
    }

    public Iterator<Rating> getRatings() {
        return ratings.values();
    }

    public int getNumRatings() {
        return numRatings;
    }

    @Override
    public int compareTo(SportEvent sportEvent) {
        return eventId.compareTo(sportEvent.eventId);
    }
}
