package responseRequest;

import java.util.Collection;
import model.*;

public record GameListResponse(Collection<GameData> games, String message) {
}
