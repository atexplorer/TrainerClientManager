package org.atexplorer.TrainerClientManager.repository;

import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.atexplorer.TrainerClientManager.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<ClientProfile> findClientProfileByAppUserUsername(String username);

    Optional<TrainerProfile> findTrainerProfileByAppUserUsername(String username);
}
