package Tests;

import Domain.IValidator;
import Domain.Movie2;
import Domain.MovieValidatory2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieValidatory2Test {

    private IValidator<Movie2> movieValidator = new MovieValidatory2();

    @Test
    void validate2() {

        Movie2 movie = new Movie2("1",2005,"Superman",20,true);

        try {
            movieValidator.validate2(movie);
        } catch (RuntimeException rex) {
            assertTrue(true);
        }
    }
}