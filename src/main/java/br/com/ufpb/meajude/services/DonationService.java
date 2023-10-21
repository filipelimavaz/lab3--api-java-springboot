package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.donation.DonationDTO;
import br.com.ufpb.meajude.dtos.donation.DonationGiverDTO;
import br.com.ufpb.meajude.entities.Campaign;
import br.com.ufpb.meajude.entities.Donation;
import br.com.ufpb.meajude.entities.User;
import br.com.ufpb.meajude.entities.enums.CampaignStatus;
import br.com.ufpb.meajude.exceptions.CustomValidationException;
import br.com.ufpb.meajude.exceptions.InvalidFieldException;
import br.com.ufpb.meajude.exceptions.InvalidRequestException;
import br.com.ufpb.meajude.exceptions.NotFoundException;
import br.com.ufpb.meajude.repositories.CampaignRepository;
import br.com.ufpb.meajude.repositories.DonationRepository;
import br.com.ufpb.meajude.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private Validator validator;

    public DonationDTO donationGiver(DonationGiverDTO donationGiverDTO) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to create a campaign.");
        }

        Optional<Campaign> optionalCampaign = campaignRepository.findActiveCampaignById(donationGiverDTO.getCampaignId());
        User user = authorizationService.getLoggedUser();

        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();

            if(!campaign.getStatus().equals(CampaignStatus.ACTIVE)) {
                throw new InvalidRequestException("Donation not performed",
                        "Only donations with 'Active' status can receive donations.");
            }

            Donation donation = new Donation();
            Set<ConstraintViolation<Object>> violations = validator.validate(donation);
            
            if (!violations.isEmpty()) {
                throw new CustomValidationException("One or more fields have validation errors", violations);
            }

            donation.setDonationValue(donationGiverDTO.getDonationValue());
            campaign.setDonationAmount(donationGiverDTO.getDonationValue());
            donation.setCampaign(campaign);
            donation.setUser(user);
            donation.setDonationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            campaign.getDonations().add(donation);
            campaign.setDonationAmount(campaign.getDonationAmount().add(donationGiverDTO.getDonationValue()));
            campaignRepository.save(campaign);
            donationRepository.save(donation);
            return DonationDTO.from(donation);
        }
        throw new NotFoundException("Campaign not found",
                "Please check if the ID is correct or if the campaign is registered.");
    }

    public DonationDTO returnDonation(String id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);

        if(optionalDonation.isPresent()) {
            Donation donation = optionalDonation.get();
            return DonationDTO.from(donation);
        }
        return null;
    }

    //Retorna TODAS as doações e ordena pela data de criação
    public List<DonationDTO> returnAllDonationList() {
        List<Donation> donationList = donationRepository.findAllDonationsOrderedByDate();
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
