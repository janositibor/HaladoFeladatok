package activitytracker;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "Activity_ID_Generator")
    @TableGenerator(name = "Activity_ID_Generator", table = "act_id_gen", pkColumnName = "id_gen", valueColumnName = "id_val")
    private Long id;

    @Column(name = "start_Time", nullable = false)
    private LocalDateTime startTime;

    @Column(length=200, nullable = false)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private ActivityType type;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


//    Statikusan mindíg beolvassa általában nem javasolt
//    @ElementCollection(fetch =FetchType.EAGER)
    @ElementCollection
    @CollectionTable(name="LABELS", joinColumns=@JoinColumn(name="activity_id"))
    @Column(name="LABEL")
    private List<String> labels;

    @OneToMany(mappedBy = "activity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OrderBy("time")
    private List<TrackPoint> trackPoints=new ArrayList<>();

    @ManyToMany(mappedBy="activities")
    private Set<Area> areas=new HashSet<>();

    public Activity() {
    }


    public Activity(LocalDateTime startTime, String description, ActivityType type) {
        this.startTime = startTime;
        this.description = description;
        this.type = type;
    }

    @PrePersist
    public void setCreated(){
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdated(){
        setUpdatedAt(LocalDateTime.now());
    }

    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    public void addTrackPoint(TrackPoint trackPointToAdd){
        trackPoints.add(trackPointToAdd);
        trackPointToAdd.setActivity(this);
    }

    public void setTrackPoints(List<TrackPoint> trackPoints) {
        this.trackPoints = trackPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", trackPoints=" + trackPoints +
                '}';
    }
}
