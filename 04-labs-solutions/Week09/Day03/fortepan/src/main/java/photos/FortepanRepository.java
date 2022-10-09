package photos;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FortepanRepository {

    private List<Photo> photos = new ArrayList<>();

    private AtomicLong idGenerator = new AtomicLong();

    public List<Photo> getPhotos() {
        return new ArrayList<>(photos);
    }

    public Photo findById(long id) {
        return photos.stream()
                .filter(photo -> photo.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PhotoNotFoundException(id));
    }

    public long addPhoto(Photo photo) {
        long id = idGenerator.incrementAndGet();
        photos.add(new Photo(id, photo.getDescription(), photo.getYear()));
        return id;
    }

    public Photo updatePhotoWithPhotographerAndYear(long id, String nameOfPhotographer, int year) {
        Photo photo = findById(id);
        photo.setNameOfPhotographer(nameOfPhotographer);
        photo.setYear(year);
        return photo;
    }

    public Photo updatePhotoWithInfo(long id, String additionalInfo) {
        Photo photo = findById(id);
        photo.addMoreInfo(additionalInfo);
        return photo;
    }

    public void deletePhoto(long id) {
        Photo photo = findById(id);
        photos.remove(photo);
    }

    public void deleteAllPhotos() {
        photos = new ArrayList<>();
        idGenerator = new AtomicLong();
    }
}
