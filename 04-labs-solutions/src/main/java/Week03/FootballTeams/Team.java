package Week03.FootballTeams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FootballTeams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;

    @Enumerated(EnumType.STRING)
    private Division division;
    private int points=0;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Player> players=new ArrayList<>();

   private int budget;

    public void addPlayer(Player playerToAdd){
        players.add(playerToAdd);
        playerToAdd.setTeam(this);
    }

    public Team() {
    }

    public Team(String name, String country, Division division, int budget) {
        this.name = name;
        this.country = country;
        this.division = division;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
