package photos;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FortepanService {

    private FortepanRepository repository;

    private ModelMapper modelMapper;

    public List<PhotoDto> listAllPhotos(Optional<String> photographer, Optional<Integer> year) {
        List<Photo> photos = repository.getPhotos().stream()
                .filter(photo -> photographer.isEmpty() || photo.getNameOfPhotographer() != null && photo.getNameOfPhotographer().equals(photographer.get()))
                .filter(photo -> year.isEmpty() || photo.getYear() == (year.get()))
                .collect(Collectors.toList());
        Type targetListType = new TypeToken<List<PhotoDto>>() {}.getType();
        return modelMapper.map(photos, targetListType);
    }

    public PhotoDto findById(long id) {
        Photo photo = repository.findById(id);
        return modelMapper.map(photo, PhotoDto.class);
    }

    public PhotoDto createPhotoWithDescription(CreatePhotoWithDescriptionCommand command) {
        Photo photo = new Photo(command.getDescription());
        long id = repository.addPhoto(photo);
        photo.setId(id);
        return modelMapper.map(photo, PhotoDto.class);
    }

    public PhotoDto createPhotoWithDescriptionAndYear(CreatePhotoWithDescriptionAndYearCommand command) {
        Photo photo = new Photo(command.getDescription(), command.getYear());
        long id = repository.addPhoto(photo);
        photo.setId(id);
        return modelMapper.map(photo, PhotoDto.class);
    }

    public PhotoDto updatePhotoWithPhotographerAndYear(long id, UpdatePhotoWithPhotographerAndYearCommand command) {
        Photo photo = repository.updatePhotoWithPhotographerAndYear(id, command.getNameOfPhotographer(), command.getYear());
        return modelMapper.map(photo, PhotoDto.class);
    }

    public PhotoDto updatePhotoWithInfo(long id, UpdatePhotoWithInfoCommand command) {
        Photo photo = repository.updatePhotoWithInfo(id, command.getAdditionalInfo());
        return modelMapper.map(photo, PhotoDto.class);
    }

    public void deletePhoto(long id) {
        repository.deletePhoto(id);
    }

    public void deleteAllPhotos() {
        repository.deleteAllPhotos();
    }
}
