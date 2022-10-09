package photos;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PhotoNotFoundException extends AbstractThrowableProblem {
    public PhotoNotFoundException(long id) {
        super(URI.create("photo/not-found"),
                "Photo not found",
                Status.NOT_FOUND,
                String.format("Photo with id: %d not found", id));
    }
}
