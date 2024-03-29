package br.dcx.ufpb.meajude.services;

import br.dcx.ufpb.meajude.dtos.donation.DonationDTO;
import br.dcx.ufpb.meajude.dtos.donation.DonationGiverDTO;
import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.Donation;
import br.dcx.ufpb.meajude.entities.User;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import br.dcx.ufpb.meajude.exceptions.CustomValidationException;
import br.dcx.ufpb.meajude.exceptions.InvalidRequestException;
import br.dcx.ufpb.meajude.exceptions.NotFoundException;
import br.dcx.ufpb.meajude.repositories.CampaignRepository;
import br.dcx.ufpb.meajude.repositories.DonationRepository;
import br.dcx.ufpb.meajude.repositories.UserRepository;
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
        throw new NotFoundException("Donation not found",
                "Please check if the ID is correct.");
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
        throw new NotFoundException("No donations founded",
                "There are no donations in the database.");
    }

    public List<DonationDTO> returnCampaignDonationList(String campaignId) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(Long.parseLong(campaignId));

        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            List<Donation> donationList = campaign.getDonations();

            List<DonationDTO> donationDTOList = new ArrayList<>();

            for (Donation donation : donationList) {
                DonationDTO donationDTO = DonationDTO.from(donation);
                donationDTOList.add(donationDTO);
            }

            return donationDTOList;
        } else {
            throw new NotFoundException("Campaign not found",
                    "Please check if the campaign ID is correct.");
        }
    }
}
