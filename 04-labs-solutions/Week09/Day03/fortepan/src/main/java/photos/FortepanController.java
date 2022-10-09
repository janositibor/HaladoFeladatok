package photos;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.violations.Violation;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/photos")
public class FortepanController {

    private FortepanService service;

    @GetMapping
    public List<PhotoDto> listAllPhotos(@RequestParam Optional<String> photographer, @RequestParam Optional<Integer> year) {
        return service.listAllPhotos(photographer, year);
    }

    @GetMapping("/{id}")
    public PhotoDto findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @PostMapping("/create-description")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto createPhotoWithDescription(@Valid @RequestBody CreatePhotoWithDescriptionCommand command) {
        return service.createPhotoWithDescription(command);
    }

    @PostMapping("/create-description-and-year")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto createPhotoWithDescriptionAndYear(@Valid @RequestBody CreatePhotoWithDescriptionAndYearCommand command) {
        return service.createPhotoWithDescriptionAndYear(command);
    }

    @PutMapping("/photographer-and-year/{id}")
    public PhotoDto updatePhotoWithPhotographerAndYear(@PathVariable("id") long id, @Valid @RequestBody UpdatePhotoWithPhotographerAndYearCommand command) {
        return service.updatePhotoWithPhotographerAndYear(id, command);
    }

    @PutMapping("/info/{id}")
    public PhotoDto updatePhotoWithInfo(@PathVariable("id") long id, @Valid @RequestBody UpdatePhotoWithInfoCommand command) {
        return service.updatePhotoWithInfo(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable("id") long id) {
        service.deletePhoto(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException manve){
        List<Violation> violations=manve.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        Problem problem=Problem.builder()
                .withType(URI.create("/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(manve.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
