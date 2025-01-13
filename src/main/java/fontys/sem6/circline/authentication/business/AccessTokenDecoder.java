package fontys.sem6.circline.authentication.business;

import fontys.sem6.circline.authentication.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
