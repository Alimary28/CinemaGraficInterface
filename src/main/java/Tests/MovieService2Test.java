package Tests;

import Domain.IValidator;
import Domain.Movie2;
import Domain.MovieValidatory2;
import Repository.IRepository;
import Repository.JSonRepository;
import Service.MovieService2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieService2Test {

    private IValidator<Movie2> movieValidator = new MovieValidatory2();
    private IRepository<Movie2> movieRepo = new JSonRepository<>(movieValidator);
    private MovieService2 movieService = new MovieService2(movieRepo);

    @Test
    void insert() {

        movieService.insert("1", 2005, "superman", 32, true);
        assertEquals(1,movieService.getAll().size());
    }

    @Test
    void update() {
        movieService.insert("1", 2005, "superman", 32, true);
        movieService.update("1", 2018, "superman", 32, true);
        assertEquals(2018,movieService.getAll().get(0).getYear());
    }

    @Test
    void fullTextSearch() {

        movieService.insert("1", 2005, "superman", 32, true);
        assertEquals(1,movieService.fullTextSearch("superman").size());
        assertEquals(0,movieService.fullTextSearch("adhgh").size());
    }
}