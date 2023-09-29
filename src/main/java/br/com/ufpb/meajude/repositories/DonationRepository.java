package br.com.ufpb.meajude.repositories;

import br.com.ufpb.meajude.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {

}
