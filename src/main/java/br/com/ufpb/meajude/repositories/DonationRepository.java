package br.com.ufpb.meajude.repositories;

import br.com.ufpb.meajude.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {

    @Query("SELECT d FROM Donation d ORDER BY d.donationDate ASC")
    List<Donation> findAllDonationsOrderedByDate();
}
