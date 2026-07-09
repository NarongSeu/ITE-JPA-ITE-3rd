package co.istad.ite.features.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserProfileRepository
        extends JpaRepository<UserProfile, String> {
}