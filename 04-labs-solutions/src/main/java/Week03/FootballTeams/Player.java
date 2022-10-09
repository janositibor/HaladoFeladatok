package Week03.FootballTeams;

import javax.persistence.*;

@Entity
@Table(name = "Players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Player(Long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Player(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Player() {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}