package ru.artemiyandstepan;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.artemiyandstepan.model.*;

import java.util.*;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    @EJB
    private MovieService movieService;
    @GET
    public Response getAllMovies(@QueryParam("sortFields") String sortFields, @QueryParam("sortOrder") String sortOrder, @QueryParam("page") @DefaultValue("0") int page, @QueryParam("size") @DefaultValue("10") int size, @QueryParam("name") String nameFilter, @QueryParam("minOscarsCount") Long minOscarsCount, @QueryParam("maxOscarsCount") Long maxOscarsCount, @QueryParam("minBoxOffice") Integer minBoxOffice, @QueryParam("maxBoxOffice") Integer maxBoxOffice, @QueryParam("genre") MovieGenre genreFilter, @QueryParam("mpaaRating") MpaaRating mpaaRatingFilter) {
        List<Movie> movies = movieService.getAllMovies(sortFields, sortOrder, page, size, nameFilter, minOscarsCount, maxOscarsCount, minBoxOffice, maxBoxOffice, genreFilter, mpaaRatingFilter);
        return Response.ok(movies).build();
    }

    @POST
    public Response createMovie(Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return Response.status(Response.Status.CREATED).entity(createdMovie).build();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movie).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") int id, Movie updatedMovie) {
        Movie movie = movieService.updateMovie(id, updatedMovie);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movie).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") int id) {
        movieService.deleteMovie(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/sum-usa-box-office")
    public Response getSumUsaBoxOffice() {
        long sum = movieService.getSumUsaBoxOffice();
        return Response.ok(sum).build();
    }

    @GET
    @Path("/movie-by-name")
    public Response getMoviesByNameSubstring(@QueryParam("substring") String substring) {
        List<Movie> movies = movieService.getMoviesByNameSubstring(substring);
        return Response.ok(movies).build();
    }

    @GET
    @Path("/screenwriters/taller-than")
    public Response getScreenwritersTallerThan(@QueryParam("height") double height) {
        long count = movieService.getScreenwritersTallerThan(height);
        return Response.ok(count).build();
    }
}
