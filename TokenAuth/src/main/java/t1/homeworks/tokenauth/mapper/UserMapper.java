package t1.homeworks.tokenauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import t1.homeworks.tokenauth.dto.RegistrationForm;
import t1.homeworks.tokenauth.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "GUEST")
    User mapToUser(RegistrationForm form, String hashPassword);
}
