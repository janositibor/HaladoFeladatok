package photos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {

  private Long id;

  private String description;

  private int year;

  private String nameOfPhotographer;

  private List<String> additionalInfo = new ArrayList<>();

  public Photo(String description) {
    this.description = description;
  }

  public Photo(String description, int year) {
    this.description = description;
    this.year = year;
  }

  public Photo(Long id, String description, int year) {
    this.id = id;
    this.description = description;
    this.year = year;
  }

  public void addMoreInfo(String info) {
    additionalInfo.add(info);
  }
}
