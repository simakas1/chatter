package lt.vaskevicius.chatter.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {

    private String accessToken;

    public static AccessTokenResponse fromString(String accessToken) {
        return new AccessTokenResponse(accessToken);
    }
}
