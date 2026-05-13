package br.com.projetojoao.projetospring.model;


import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String actors;
    private String plot;

    public Serie(DataSerie dataSerie){
        this.title = dataSerie.title();
        this.totalSeasons = dataSerie.totalSeasons();

        // try to obtain the double value
        try{
            this.rating = Double.valueOf(dataSerie.rating());
        }catch(NumberFormatException e){
            this.rating = 0.0;
        }

        // another way to make the conversion
        // Using OptionalDouble Class
        // this.rating = OptionalDobule.of(Double.valueOf(dataSerie.rating())).orElse(0)

        this.genre = Category.fromString(dataSerie.genre().split(",")[0].trim());
        this.actors = dataSerie.actors();
        this.plot = dataSerie.plot();
    }

    // getters and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString(){
        return "\nGenre: " + genre +
                "\nTitle: " + title +
                "\nTotal Seasons: " + totalSeasons +
                "\nRating: " + rating +
                "\nActors: " + actors +
                "\nPlot: " + plot;
    }
}
