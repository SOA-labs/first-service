package ru.artemiyandstepan;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.artemiyandstepan.model.Movie;
import ru.artemiyandstepan.model.MovieGenre;
import ru.artemiyandstepan.model.MpaaRating;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Stateless
@Remote(MovieService.class)
public class MovieServiceEJB implements MovieService {

    private static final Map<Integer, Movie> movies = new HashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Logger logger = LogManager.getLogger(MovieServiceEJB.class);

    @Override
    public List<Movie> getAllMovies(String sortFields, String sortOrder, int page, int size, String nameFilter, Long minOscarsCount, Long maxOscarsCount, Integer minBoxOffice, Integer maxBoxOffice, MovieGenre genreFilter, MpaaRating mpaaRatingFilter) {
        logger.info("Fetching all movies with filters");

        List<Movie> filteredMovies = new ArrayList<>(movies.values());

        // Фильтры
        if (nameFilter != null && !nameFilter.isEmpty()) {
            filteredMovies.removeIf(movie -> !movie.getName().toLowerCase().contains(nameFilter.toLowerCase()));
            logger.info("Applied filter name: {}", nameFilter);
        }
        if (minOscarsCount != null) {
            filteredMovies.removeIf(movie -> movie.getOscarsCount() < minOscarsCount);
            logger.info("Applied filter minOscarsCount: {}", minOscarsCount);
        }
        if (maxOscarsCount != null) {
            filteredMovies.removeIf(movie -> movie.getOscarsCount() > maxOscarsCount);
            logger.info("Applied filter maxOscarsCount: {}", maxOscarsCount);
        }
        if (minBoxOffice != null) {
            filteredMovies.removeIf(movie -> movie.getUsaBoxOffice() < minBoxOffice);
            logger.info("Applied filter minBoxOffice: {}", minBoxOffice);
        }
        if (maxBoxOffice != null) {
            filteredMovies.removeIf(movie -> movie.getUsaBoxOffice() > maxBoxOffice);
            logger.info("Applied filter maxBoxOffice: {}", maxBoxOffice);
        }
        if (genreFilter != null) {
            filteredMovies.removeIf(movie -> !movie.getGenre().equals(genreFilter));
            logger.info("Applied filter genreFilter: {}", genreFilter);
        }
        if (mpaaRatingFilter != null) {
            filteredMovies.removeIf(movie -> !movie.getMpaaRating().equals(mpaaRatingFilter));
            logger.info("Applied filter mpaaRatingFilter: {}", mpaaRatingFilter);
        }

        // Сортировка по нескольким полям
        if (sortFields != null && !sortFields.isEmpty()) {
            String[] fields = sortFields.split(",");
            filteredMovies.sort((movie1, movie2) -> {
                for (String field : fields) {
                    int comparison = switch (field) {
                        case "name" -> movie1.getName().compareTo(movie2.getName());
                        case "oscarsCount" -> Long.compare(movie1.getOscarsCount(), movie2.getOscarsCount());
                        case "usaBoxOffice" -> Integer.compare(movie1.getUsaBoxOffice(), movie2.getUsaBoxOffice());
                        case "creationDate" -> movie1.getCreationDate().compareTo(movie2.getCreationDate());
                        default -> throw new IllegalArgumentException("Invalid sort field: " + field);
                    };
                    logger.info("Sorted by: {}", field);
                    if (comparison != 0) {
                        return "desc".equalsIgnoreCase(sortOrder) ? -comparison : comparison;
                    }
                }
                return 0; // Если поля равны
            });
        }
        // Постраничный вывод
        int totalMovies = filteredMovies.size();
        int start = Math.min(page * size, totalMovies);
        int end = Math.min((page + 1) * size, totalMovies);


        return new ArrayList<>(filteredMovies.subList(start, end));
    }

    // Функция для сравнения по указанному полю



    @Override
    public Movie getMovieById(int id) {
        return movies.get(id);
    }

    @Override
    public Movie createMovie(Movie movie) {
        movie.setId(idCounter.incrementAndGet());
        movie.setCreationDate(new Date());
        movies.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie updateMovie(int id, Movie updatedMovie) {
        Movie existingMovie = movies.get(id);
        if (existingMovie != null) {
            updatedMovie.setId(id);
            updatedMovie.setCreationDate(existingMovie.getCreationDate());
            movies.put(id, updatedMovie);
        }
        return updatedMovie;
    }

    @Override
    public void deleteMovie(int id) {
        movies.remove(id);
    }

    @Override
    public long getSumUsaBoxOffice() {
        return movies.values().stream().mapToLong(Movie::getUsaBoxOffice).sum();
    }

    @Override
    public long getScreenwritersTallerThan(double height) {
        return movies.values().stream()
                .filter(movie -> movie.getScreenwriter() != null && movie.getScreenwriter().getHeight() != null && movie.getScreenwriter().getHeight() > height)
                .count();
    }

    @Override
    public List<Movie> getMoviesByNameSubstring(String substring) {
        if (substring == null || substring.isEmpty()) {
            return null;
        }
        logger.info("Fetching movies with name containing substring: {}", substring);
        // Фильтрация
        return movies.values().stream().filter(movie -> movie.getName().toLowerCase().contains(substring.toLowerCase())).toList();
    }
}
