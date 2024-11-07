package ru.artemiyandstepan;

import jakarta.ejb.Remote;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import ru.artemiyandstepan.model.Movie;
import ru.artemiyandstepan.model.MovieGenre;
import ru.artemiyandstepan.model.MpaaRating;

import java.util.List;

@Remote
public interface MovieService {
    List<Movie> getAllMovies(@QueryParam("sortFields") String sortFields, @QueryParam("sortOrder") String sortOrder, @QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam("name") String nameFilter, @QueryParam("minOscarsCount") Long minOscarsCount, @QueryParam("maxOscarsCount") Long maxOscarsCount, @QueryParam("minBoxOffice") Integer minBoxOffice, @QueryParam("maxBoxOffice") Integer maxBoxOffice, @QueryParam("genre") MovieGenre genreFilter, @QueryParam("mpaaRating") MpaaRating mpaaRatingFilter);
    Movie getMovieById(int id);
    Movie createMovie(Movie movie);
    Movie updateMovie(int id, Movie updatedMovie);
    void deleteMovie(int id);
    long getSumUsaBoxOffice();
    long getScreenwritersTallerThan(double height);
    List<Movie> getMoviesByNameSubstring(String substring);
}

