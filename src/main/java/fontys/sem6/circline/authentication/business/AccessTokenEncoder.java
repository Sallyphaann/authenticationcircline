package fontys.sem6.circline.authentication.business;

import fontys.sem6.circline.authentication.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
