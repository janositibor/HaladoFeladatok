package activitytracker;

import javax.persistence.*;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Area area;
    private String name;
    private int population;

    public City() {
    }

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public void addToArea(Area area){
        this.area=area;
        area.getCities().put(name,this);
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

//    @Override
//    public String toString() {
//        return "City{" +
//                "id=" + id +
//                ", areaID=" + area.getId() +
//                ", name='" + name + '\'' +
//                ", population=" + population +
//                '}';
//    }
}
