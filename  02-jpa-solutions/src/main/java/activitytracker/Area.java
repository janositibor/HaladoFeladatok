package activitytracker;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(name="Activity_Area",
            joinColumns=@JoinColumn(name="Area_ID"),
            inverseJoinColumns=@JoinColumn(name="Activity_ID"))
    private Set<Activity> activities=new HashSet<>();

    @OneToMany(mappedBy="area", cascade = CascadeType.ALL)
    @MapKey(name = "name")
    private Map<String, City> cities=new HashMap<>();

    public Area() {
    }

    public Area(String name) {
        this.name = name;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
        activity.getAreas().add(this);
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

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Map<String, City> getCities() {
        return cities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
    }

//    @Override
//    public String toString() {
//        return "Area{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", activities=" + activities +
//                ", cities=" + cities +
//                '}';
//    }
}
