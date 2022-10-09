package photos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePhotoWithInfoCommand {

    @NotBlank(message ="Additional information mustn't be empty!")
    public String additionalInfo;
}
