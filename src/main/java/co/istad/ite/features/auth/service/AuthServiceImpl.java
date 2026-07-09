package co.istad.ite.features.auth.service;


import co.istad.ite.features.auth.Role;
import co.istad.ite.features.auth.dto.request.RegisterRequest;
import co.istad.ite.features.auth.dto.resopnse.RegisterResponse;
import co.istad.ite.features.auth.mapper.AuthMapper;
import co.istad.ite.features.userProfile.UserProfile;
import co.istad.ite.features.userProfile.UserProfileRepository;
import co.istad.ite.security.KeycloakAdminClientProps;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final Keycloak keycloak;

    private final KeycloakAdminClientProps props;
    private final AuthMapper authMapper;
    private final UserProfileRepository userProfileRepository;
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Validate password matching
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords don't match!"
            );
        }

        // Create keycloak user
        UsersResource usersResource = keycloak
                .realm(props.getTargetRealm())
                .users();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(registerRequest.username());
        userRepresentation.setEmail(registerRequest.email());
        userRepresentation.setFirstName(registerRequest.firstName());
        userRepresentation.setLastName(registerRequest.lastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        // Set Keycloak custom attributes
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber", List.of(registerRequest.phoneNumber()));
        userRepresentation.setAttributes(attributes);

        // Set Credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        userRepresentation.setCredentials(List.of(credential));

        //try to create something on keycloak
        try(Response response = usersResource.create(userRepresentation)) {
            log.info("Response status code : {}" , response.getStatus());
            if(response.getStatus() == HttpStatus.CREATED.value()){
                //success situation
                UserRepresentation createdUser = keycloak.realm(props.getTargetRealm()).users()
                        .search(userRepresentation.getUsername())
                        .getFirst();


                UserResource userResourceSet = keycloak.realm(props.getTargetRealm())
                        .users().get(createdUser.getId());
                userResourceSet.sendVerifyEmail();

                RoleRepresentation roleUser = keycloak.realm(props.getTargetRealm())
                        .roles().get(Role.USER.name()).toRepresentation();
                RoleRepresentation roleCustomer = keycloak.realm(props.getTargetRealm())
                        .roles().get(Role.CUSTOMER.name()).toRepresentation();
                userResourceSet.roles().realmLevel().add(List.of(roleUser,roleCustomer));

                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(createdUser.getId());
                userProfileRepository.save(userProfile);
                return authMapper.mapUserRepresentaionToRegisterResponse(createdUser);
            }else if (response.getStatus() == HttpStatus.CONFLICT.value()){
                //conflict situation
                log.info("Check username or email already exist");
            }
        }

        return null;
    }
}
