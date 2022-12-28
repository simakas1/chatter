package lt.vaskevicius.chatter.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatterError {

    private String code;
    private String message;

    @Override
    public String toString() {
        String errorFormat = "ChatterError{code='%s', message='%s'}";
        return String.format(errorFormat, code, message);
    }
}
