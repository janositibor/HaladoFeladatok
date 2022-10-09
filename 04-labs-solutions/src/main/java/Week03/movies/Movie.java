package Week03.movies;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name="date_of_release")
    private LocalDate releaseDate;
    private int length;

    @ElementCollection
    @CollectionTable(name="ratings", joinColumns = @JoinColumn(name="movie_id"))
    @AttributeOverride(name="username", column=@Column(name="user_name"))
    private List<Rating> ratings = new ArrayList<>();


    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate, int length) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public Movie(Long id, String title, LocalDate releaseDate, int length) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public void addRating(Rating rating){
        ratings.add(rating);
    }


    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }


    public int getLength() {
        return length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
