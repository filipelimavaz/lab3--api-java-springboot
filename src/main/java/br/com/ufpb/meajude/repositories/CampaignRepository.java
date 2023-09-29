package br.com.ufpb.meajude.repositories;

import br.com.ufpb.meajude.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, String> {

    List<Campaign> findAll();

    @Query("SELECT c FROM Campaign c WHERE c.status = 1")
    List<Campaign> findActiveCampaigns();

    @Query("SELECT c FROM Campaign c WHERE c.status = 1 AND c.id = :id")
    Optional<Campaign> findActiveCampaignById(String id);

    @Query("SELECT c FROM Campaign c WHERE c.status = 3")
    List<Campaign> findClosedCampaigns();

    @Query("SELECT c FROM Campaign c WHERE c.status = 0")
    List<Campaign> findUpcomingCampaigns();

    List<Campaign> findByUserId(String userId);
}
