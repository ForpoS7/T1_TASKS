package t1.homeworks.tokenauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@RedisHash("refresh_token")
public class RefreshToken {
    @Id
    private String username;
    private String token;
    @TimeToLive
    private Long timeToLiveSeconds;
}
