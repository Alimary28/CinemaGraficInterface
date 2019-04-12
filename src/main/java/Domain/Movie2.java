package Domain;

public class Movie2 extends Entity{
    private int year, bookings;
    private String title;
    private double price;
    private boolean inCinema;

    public Movie2(String id, int year, String title, double price, boolean inCinema) {
        super(id);
        this.year = year;
        this.title = title;
        this.price = price;
        this.inCinema = inCinema;
        this.bookings = 0;
    }
    @Override
    public String toString() {
        return "Movie{" +
                "id='" + getId() + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", inCinema=" + inCinema +
                ", bookings=" + bookings +
                '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInCinema() {
        return inCinema;
    }

    public void setInCinema(boolean inCinema) {
        this.inCinema = inCinema;
    }

    public int getBookings() {
        return bookings;
    }

    public void setBookings(int bookings) {
        this.bookings = bookings;
    }
}
