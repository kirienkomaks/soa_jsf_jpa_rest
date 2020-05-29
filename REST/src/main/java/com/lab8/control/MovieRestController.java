package com.lab8.control;

import com.lab8.entity.Movie;
import com.lab8.database.MovieDB;
import com.lab8.entity.User;
import com.lab8.database.UserDB;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/movieController")
@Api(tags={"todo"})
public class MovieRestController extends Application {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-Zajecia");
    private EntityManager em = factory.createEntityManager();

    private User user;

    private final MovieDB movieDB = new MovieDB();
    private final UserDB userDB = new UserDB();

    @GET
    @Path("/movies")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public List<Movie> getAllMovies(){
        return movieDB.readAllMovies();
    }

    @GET
    @Path("/movies")
    @Consumes("test/uri-list")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public List<String> getAllMoviesConNeg(){
        return movieDB.readAllMoviesConNeg();
    }

    @GET
    @Path("movies/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public Movie getMovie(@PathParam("title") String title){
        return movieDB.readMovie(title);
    }


    @POST
    @ApiOperation(value = "Create a new to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Created")
    })
    public void addMovie(@QueryParam("title") String title, @QueryParam("url") String url, @QueryParam("userName") String name){
        System.out.println("rest add movie");
        User user = userDB.readUser(name);
        Movie movie = new Movie(title,url,user);
        user.getMovies().add(movie);
        movieDB.addMovie(movie, user);
        User useradd = userDB.readUser(name);
    }

    @DELETE
    @Path("/{name}/{title}")
    @ApiOperation(value = "Remove existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Removed")
    })
    public void removeMovie(@PathParam("title") String title, @PathParam("name") String name){
        List<Movie> movies = movieDB.readAllMovies();
        for (Movie m:movies) {
            if (m.getTitle().equals(title) && m.getUser().getName().equals(name)){
                movieDB.deleteMovie(m);
            }
        }
    }

    @DELETE
    @Path("/{idMovie}")
    @ApiOperation(value = "Remove existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Removed")
    })
    public void removeMovie(@PathParam("idMovie") int idMovie){
        List<Movie> updatedMovies = new ArrayList<>();
        System.out.println("delete Movie rest");
        Movie movie = movieDB.readMovieByID(idMovie);
        user = userDB.readUser(movie.getUser().getName());
        for (Movie m:user.getMovies()) {
            if(m.getIdMovie()!=idMovie){
                updatedMovies.add(m);
            }
}
        user.setMovies(updatedMovies);
        userDB.updateUser(user);
        movieDB.deleteMovie(movie);
    }

    @PUT
    @Path("/{idMovie}/title/{newVal}")
    @ApiOperation(value = "Updates existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Updated"),
            @ApiResponse(code=404, message="Not found")
    })
    public void updateMovieTitle(@PathParam("idMovie")  int idMovie, @PathParam("newVal") String newVal) {
        List<Movie> updatedMovies = new ArrayList<>();
        Movie movie = movieDB.readMovieByID(idMovie);
        movie.setTitle(newVal);
        user = userDB.readUser(movie.getUser().getName());
        for (Movie m:user.getMovies()) {
            if(m.getIdMovie()==idMovie){
                m.setTitle(newVal);
            }
            updatedMovies.add(m);
        }
        user.setMovies(updatedMovies);
        userDB.updateUser(user);
        movieDB.updateMovie(movie);
    }

    @PUT
    @Path("/{idMovie}/uri/{newVal}")
    @ApiOperation(value = "Updates existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Updated"),
            @ApiResponse(code=404, message="Not found")
    })
    public void updateMovieURI(@PathParam("idMovie")  int idMovie, @PathParam("newVal") String newVal) {
        List<Movie> updatedMovies = new ArrayList<>();
        Movie movie = movieDB.readMovieByID(idMovie);
        movie.setUri(newVal);
        user = userDB.readUser(movie.getUser().getName());
        for (Movie m:user.getMovies()) {
            if(m.getIdMovie()==idMovie){
                m.setUri(newVal);
            }
            updatedMovies.add(m);
        }
        user.setMovies(updatedMovies);
        userDB.updateUser(user);
        movieDB.updateMovie(movie);


    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
