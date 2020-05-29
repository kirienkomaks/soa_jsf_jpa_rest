package com.lab8;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab8.entity.Movie;
import com.lab8.entity.User;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Named
@ApplicationScoped
public class CatalogService {


    private ObjectMapper mapper = new ObjectMapper();

    private String name;
    private int age;
    //private String destination = "/img/";
    private UploadedFile imageUser;

    private String title;
    private String url;
    private String titleToSearch;


    private boolean add = false;

    private String changedUserName;

    private User changedUser;
    private Movie searchedMovie;
    private List<User> users = new ArrayList<>();
    //private List<Movie> userMovies = new ArrayList<>();
    private List<String> uriList = new ArrayList<>();

    private String chooseItemToUpdate;
    private String newValueToUpdate;

    private boolean showOsoby = false;
    private boolean showMoviesURI = false;
    private boolean searchMovie = false;



    public CatalogService() {

    }

    public String addUser() throws IOException {
        ResteasyClient client = new ResteasyClientBuilder().build();
        System.out.println("add in process");
        InputStream inputStream = imageUser.getInputstream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String fileName = imageUser.getFileName();
        copyFile(fileName,inputStream,bytes);
        //String imgBytes = new String(bytes);
        //System.out.println(imgBytes);
        ResteasyWebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController")
                .queryParam("name", name)
                .queryParam("age", age)
                .queryParam("image", fileName);
        Response response = target.request().post(null);
        response.close();
        return "index";
    }

    public void copyFile(String fileName, InputStream in, byte[] bytes) {
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(fileName));

            int read = 0;

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String addMovie(){
        ResteasyClient client = new ResteasyClientBuilder().build();
        System.out.println("add movie in process");
        ResteasyWebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController")
                .queryParam("title", title)
                .queryParam("url", url)
                .queryParam("userName", changedUserName);
        Response response = target.request().post(null);
        response.close();
        return "userPage";
    }

    public String getListUsers(){
        Client client=ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController/users");
        Response response = target.request().get();
        try {
            users = mapper.readValue(response.readEntity(String.class), new TypeReference<List<User>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(users);
        return "index";
    }

    public String getUser() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController/{name}")
                .resolveTemplate("name", changedUserName);
        Response response = target.request().get();
        try {
            String resp = response.readEntity(String.class);
            response.close();
            if (resp != null) {
                changedUser = mapper.readValue(resp, User.class);

                return "userPage";
            } else {
                return "index";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "index";
        }
    }

    public void deleteUser(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController/{name}")
                .resolveTemplate("name", changedUserName);
        Response response = target.request().delete();
        String resp = response.readEntity(String.class);
        response.close();
        if (resp != null) {
            System.out.println("user deleted");
        }
    }

    public List<Movie> getListUserMovies(){
        List<Movie> userMovies = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController/{name}/movies")
                .resolveTemplate("name", changedUserName);
        Response response = target.request().get();
        try {
            userMovies = mapper.readValue(response.readEntity(String.class), new TypeReference<List<Movie>>(){});
            response.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //System.out.println(userMovies.get(0).getUri());
        return userMovies;
    }

    public String updateMoviePosition(Movie movie){
        System.out.println("update started");
        Client client = ClientBuilder.newClient();
        if (chooseItemToUpdate.equals("title")){
            WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController/{idMovie}/title/{newVal}")
                    .resolveTemplate("idMovie", movie.getIdMovie())
                    .resolveTemplate("newVal", newValueToUpdate);
            Response response = target.request().put(null);
        }else if(chooseItemToUpdate.equals("uri")){
            WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController/{idMovie}/uri/{newVal}")
                    .resolveTemplate("idMovie", movie.getIdMovie())
                    .resolveTemplate("newVal", newValueToUpdate);
            Response response = target.request().put(null);
        }


        return "userPage";
    }

    public  String deleteMovie(Movie movie){
        System.out.println("delete movie catalog");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController/{idMovie}")
                .resolveTemplate("idMovie", movie.getIdMovie());
        Response response = target.request().delete();
        return "userPage";
    }

    public String findMovieByTitle(){
        setSearchMovie(true);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController/movies/{title}")
                .resolveTemplate("title", titleToSearch);
        Response response = target.request().get();
        try {
            String resp = response.readEntity(String.class);
            searchedMovie = mapper.readValue(resp, Movie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }


    public String conneg(){
        setShowMoviesURI(true);
        Client client=ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/movieController/movies");
        Response response = target.request().get();
        try {
            uriList = mapper.readValue(response.readEntity(String.class), new TypeReference<List<String>>(){});
            for (String s:uriList) {
                System.out.println(s);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "index";
    }

    public  String getOsoby(){
        setShowOsoby(true);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/lab8_war_exploded/service/userController/osoby");
        Response response = target.request().get();
        String resp = response.readEntity(String.class);
        int status = response.getStatus();
        if (status==200){
            WebTarget targetUser = client.target("http://localhost:8080/lab8_war_exploded/service/userController"+resp);
            Response responseUser = targetUser.request().get();
            try {
                users = mapper.readValue(responseUser.readEntity(String.class), new TypeReference<List<User>>(){});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return "index";
    }

    public void listener(AjaxBehaviorEvent event){
        System.out.println(event.getComponent().getClass().getName());
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public String getChangedUserName() {
        return changedUserName;
    }

    public void setChangedUserName(String changedUserName) {
        this.changedUserName = changedUserName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public User getChangedUser() {
        return changedUser;
    }

    public void setChangedUser(User changedUser) {
        this.changedUser = changedUser;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public UploadedFile getImageUser() {
        return imageUser;
    }

    public void setImageUser(UploadedFile imageUser) {
        this.imageUser = imageUser;
    }

    public String getChooseItemToUpdate() {
        return chooseItemToUpdate;
    }

    public void setChooseItemToUpdate(String chooseItemToUpdate) {
        this.chooseItemToUpdate = chooseItemToUpdate;
    }

    public String getNewValueToUpdate() {
        return newValueToUpdate;
    }

    public void setNewValueToUpdate(String newValueToUpdate) {
        this.newValueToUpdate = newValueToUpdate;
    }

    public boolean isShowOsoby() {
        return showOsoby;
    }

    public void setShowOsoby(boolean showOsoby) {
        this.showOsoby = showOsoby;
    }

    public boolean isShowMoviesURI() {
        return showMoviesURI;
    }

    public void setShowMoviesURI(boolean showMoviesURI) {
        this.showMoviesURI = showMoviesURI;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<String> getUriList() {
        return uriList;
    }

    public void setUriList(List<String> uriList) {
        this.uriList = uriList;
    }

    public String getTitleToSearch() {
        return titleToSearch;
    }

    public void setTitleToSearch(String titleToSearch) {
        this.titleToSearch = titleToSearch;
    }

    public Movie getSearchedMovie() {
        return searchedMovie;
    }

    public void setSearchedMovie(Movie searchedMovie) {
        this.searchedMovie = searchedMovie;
    }

    public boolean isSearchMovie() {
        return searchMovie;
    }

    public void setSearchMovie(boolean searchMovie) {
        this.searchMovie = searchMovie;
    }


}
