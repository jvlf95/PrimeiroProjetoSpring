package br.com.projetojoao.projetospring.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seasonNumber;
    private String title;
    private LocalDate released;
    private double rating;
    private int number;
    @ManyToOne
    private Serie serie;

    public Episode(){}

    public Episode(int seasonNumber, DataEpisode dataEpisode){
        this.seasonNumber = seasonNumber;
        this.title = dataEpisode.title();

        try{
            this.released = LocalDate.parse(dataEpisode.released());
        }
        catch(DateTimeParseException e){
            this.released = null;
        }

        try{
            this.rating = Double.valueOf(dataEpisode.rating());
        }
        catch(NumberFormatException e){
            this.rating = 0.0;
        }

        this.number = dataEpisode.epNumber();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return "\nTitle: " + getTitle() +
                "\nSeason: " + getSeasonNumber() +
                "\nNumber: " + getNumber() +
                "\nReleased: " + getReleased() +
                "\nRating: " + getRating();
    }
}
