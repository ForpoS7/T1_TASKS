package t1.homeworks.tokenauth.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import t1.homeworks.tokenauth.entity.RefreshToken;

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, String> {
}
