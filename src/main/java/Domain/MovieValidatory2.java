package Domain;

public class MovieValidatory2 implements IValidator<Movie2>{

    public void validate2(Movie2 movie2){
        if(movie2.getPrice() <= 0.0){
            throw new MovieException("Price must be a positive number");
        }
        if(movie2.getYear() < 1000 || movie2.getYear() > 9999){
            throw new MovieException("Invalid year");
        }
    }
}
