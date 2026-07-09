package co.istad.ite.features.auth.dto.resopnse;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String userId,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {
}