package ResponseRequest;

import java.util.Collection;
import model.*;

public record GameListResponse(Collection<GameData> games, String message) {
}
