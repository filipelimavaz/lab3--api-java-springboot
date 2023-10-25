package br.dcx.ufpb.meajude.repositories;

import br.dcx.ufpb.meajude.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findAll();

    //RETORNA TODAS AS CAMPANHAS ATIVAS E ORDENA POR ORDEM ALFABÉTICA
    @Query("SELECT c FROM Campaign c WHERE c.status = 1 ORDER BY c.title ASC")
    List<Campaign> findActiveCampaigns();

    //RETORNA TODAS AS CAMPANHAS ATIVAS E ORDENA PELA DATA DE CRIAÇÃO
    @Query("SELECT c FROM Campaign c WHERE c.status = 1 ORDER BY c.creationDate ASC")
    List<Campaign> findActiveCampaignsOrderedByCreationDate();

    //RETORNA TODAS AS CAMPANHAS ENCERRADAS E ORDENA PELA DATA DE CRIAÇÃO
    @Query("SELECT c FROM Campaign c WHERE c.status = 2 ORDER BY c.creationDate ASC")
    List<Campaign> findClosedCampaignsOrderedByCreationDate();

    //RETORNA TODAS AS CAMPANHAS ATIVAS QUE ATINGIRAM A META
    @Query("SELECT c FROM Campaign c WHERE c.status = 1 AND c.donationAmount >= c.goal")
    List<Campaign> findActiveCampaignsReachedGoal();

    //RETORNA TODAS AS CAMPANHAS ENCERRADAS QUE ATINGIRAM A META
    @Query("SELECT c FROM Campaign c WHERE c.status = 2 AND c.donationAmount >= c.goal")
    List<Campaign> findClosedCampaignsReachedGoal();

    @Query("SELECT c FROM Campaign c WHERE c.status = 1 AND c.id = :id")
    Optional<Campaign> findActiveCampaignById(Long id);

    @Query("SELECT c FROM Campaign c WHERE c.status = 2")
    List<Campaign> findClosedCampaigns();

    @Query("SELECT c FROM Campaign c WHERE c.status = 0")
    List<Campaign> findUpcomingCampaigns();

    List<Campaign> findByUserId(Long userId);
}
