package Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking2 extends Entity {

    private String movieId, cardId;
    LocalDate date;
    LocalTime hour;


    public Booking2(String id, String movieId, String cardId, LocalDate date, LocalTime hour) {
        super(id);
        this.movieId = movieId;
        this.cardId = cardId;
        this.date = date;
        this.hour = hour;
    }
    @Override
    public  String toString() {
        return "Booking{" +
                "id='" + getId() + '\'' +
                ", movieId='" + movieId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

}
