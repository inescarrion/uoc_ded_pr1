package uoc.ds.pr.model;

import uoc.ds.pr.SportEvents4Club;

public class Rating {
    private Player player;
    private SportEvents4Club.Rating rating;
    private String message;

    public Rating(Player player, SportEvents4Club.Rating rating, String message) {
        setPlayer(player);
        setRating(rating);
        setMessage(message);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SportEvents4Club.Rating rating() {
        return rating;
    }

    public void setRating(SportEvents4Club.Rating rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
