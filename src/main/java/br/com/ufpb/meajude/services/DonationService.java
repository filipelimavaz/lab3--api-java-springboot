package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.donation.DonationDTO;
import br.com.ufpb.meajude.dtos.donation.DonationGiverDTO;
import br.com.ufpb.meajude.entities.Campaign;
import br.com.ufpb.meajude.entities.Donation;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.repositories.CampaignRepository;
import br.com.ufpb.meajude.repositories.DonationRepository;
import br.com.ufpb.meajude.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    public DonationDTO donationGiver(DonationGiverDTO donationGiverDTO) {
        Optional<Campaign> optionalCampaign = campaignRepository.findActiveCampaignById(String.valueOf(donationGiverDTO.getCampaignId()));
        Optional<User> optionalUser = userRepository.findById(String.valueOf(donationGiverDTO.getUserId()));

        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            Donation donation = new Donation();
            donation.setDonationValue(donationGiverDTO.getDonationValue());
            campaign.setDonationAmount(donationGiverDTO.getDonationValue());
            donation.setCampaign(campaign);
            donation.setUser(optionalUser.get());
            donation.setDonationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            campaignRepository.save(campaign);
            donationRepository.save(donation);
            return DonationDTO.from(donation);
        }
        return null;
    }

    public DonationDTO returnDonation(String id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);

        if(optionalDonation.isPresent()) {
            Donation donation = optionalDonation.get();
            return DonationDTO.from(donation);
        }
        return null;
    }

    public List<DonationDTO> returnAllDonationList() {
        List<Donation> donationList = donationRepository.findAll();
        List<DonationDTO> donationDTOList = new ArrayList<>();

        if(!donationList.isEmpty()) {
            for(Donation d : donationList) {
                DonationDTO donationDTO = DonationDTO.from(d);
                donationDTOList.add(donationDTO);
            }
            return donationDTOList;
        }
        return null;
    }
}
