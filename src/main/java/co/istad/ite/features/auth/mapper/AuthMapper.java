package co.istad.ite.features.auth.mapper;


import co.istad.ite.features.auth.dto.request.RegisterRequest;
import co.istad.ite.features.auth.dto.resopnse.RegisterResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public class AuthMapper {
    public RegisterResponse mapUserRepresentaionToRegisterResponse( UserRepresentation userRepresentation) {
        return RegisterResponse.builder()
                .userId(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .phoneNumber(userRepresentation.firstAttribute("phoneNumber"))
                .build();
    }
}
