package co.istad.ite.features.userProfile;

import co.istad.ite.features.userProfile.dto.UserProfileResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserProfileService {
    UserProfileResponse me();
}
