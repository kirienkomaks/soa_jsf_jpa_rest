package com.lab8.control;


import com.lab8.entity.Movie;
import com.lab8.database.MovieDB;
import com.lab8.entity.User;
import com.lab8.database.UserDB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Path("/userController")
//@Api(tags={"todo"})
public class UserRestController extends Application {

    private final UserDB userDB = new UserDB();
    private final MovieDB movieDB = new MovieDB();

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public List<User> getAllUsers(){
        return userDB.readAllUsers();
    }

    @GET
    @Path("/osoby")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public String getOsoby(){
        String location = "/users";
        return location;
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public User getUser(@PathParam("name") String name){
        return userDB.readUser(name);
    }

    @GET
    @Path("/{name}/movies")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch all to dos")
    @ApiResponses({
            @ApiResponse(code=200, message="Success")
    })
    public List<Movie> getUserMovies(@PathParam("name") String name ){
        return userDB.readUserMovies(name);
    }

    @POST
    @ApiOperation(value = "Create a new to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Created")
    })
    public void addUser(@QueryParam("name") String name, @QueryParam("age") int age, @QueryParam("image") String image){
        System.out.println("rest add");
        File file = new File(image);
        byte[] bFile = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User(name, age, bFile);
        if(!userDB.userExist(user)) {
            userDB.createUser(user);
        }

    }

    @PUT
    @Path("/{name}")
    @ApiOperation(value = "Updates existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Updated"),
            @ApiResponse(code=404, message="Not found")
    })
    public void updateUser(@PathParam("name") String name, @QueryParam("age") int age) {
                User user = userDB.readUser(name);
                user.setAge(age);
                userDB.updateUser(user);
    }

    @DELETE
    @Path("/{name}")
    @ApiOperation(value = "Remove existing to do")
    @ApiResponses({
            @ApiResponse(code=200, message="Removed")
    })
    public void removeUser(@PathParam("name") String name){
        User user = userDB.readUser(name);
        List<Movie> movies = user.getMovies();
        if (!movies.equals(null)) {
            for (Movie m : movies) {
                movieDB.deleteMovie(m);
            }
        }
        userDB.deleteUser(user);
    }


}
