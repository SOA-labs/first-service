package ru.artemiyandstepan.model;

import java.io.Serializable;
import java.util.Date;


public class Movie implements Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private long oscarsCount;
    private int usaBoxOffice;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person screenwriter;
    public Movie(){}
    public Movie(int id, String name, Coordinates coordinates, Date creationDate, long oscarsCount, int usaBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public int getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public void setUsaBoxOffice(int usaBoxOffice) {
        this.usaBoxOffice = usaBoxOffice;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }
}

