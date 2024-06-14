package ResponseRequest;
import model.*;

import java.util.Objects;

public record ResponseAuth(String username, String authToken, String message) {
}