package br.com.ufpb.meajude.services;

import br.com.ufpb.meajude.dtos.campaign.CampaignDTO;
import br.com.ufpb.meajude.dtos.campaign.CampaignRegistrationDTO;
import br.com.ufpb.meajude.dtos.campaign.CampaignUpdateDTO;
import br.com.ufpb.meajude.entities.Campaign;
import br.com.ufpb.meajude.entities.enums.CampaignStatus;
import br.com.ufpb.meajude.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public CampaignDTO createCampaign(CampaignRegistrationDTO campaignRegistrationDTO) {

            Campaign campaign = new Campaign();
            campaign.setTitle(campaignRegistrationDTO.getTitle());
            campaign.setDescription(campaignRegistrationDTO.getDescription());
            campaign.setDonationAmount(new BigDecimal(0));
            campaign.setGoal(campaignRegistrationDTO.getGoal());
            campaign.setStartDate(campaignRegistrationDTO.getStartDate());
            campaign.setEndDate(campaignRegistrationDTO.getEndDate());

            Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(campaign.getStartDate().after(currentDate)) {
                campaign.setStatus(CampaignStatus.NOT_STARTED);
            } else {
                campaign.setStatus(CampaignStatus.ACTIVE);
            }
            campaignRepository.save(campaign);
            return CampaignDTO.from(campaign);
    }

    //Retorna campanhas por ID
    public CampaignDTO returnCampaign(String id) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(id);

        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            return CampaignDTO.from(campaign);
        }
        return null;
    }

    //Retorna todas campanhas ATIVAS
    public List<CampaignDTO> returnActiveCampaignList() {
        List<Campaign> campaignList = campaignRepository.findActiveCampaigns();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null;
    }

    //Retorna todas campanhas EM BREVE
    public List<CampaignDTO> returnUpcomingCampaignList() {
        List<Campaign> campaignList = campaignRepository.findUpcomingCampaigns();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null;
    }

    //Retorna todas campanhas ENCERRADAS
    public List<CampaignDTO> returnClosedCampaignList() {
        List<Campaign> campaignList = campaignRepository.findClosedCampaigns();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null;
    }

    //Retorna todas campanhas do USUÁRIO
    public List<CampaignDTO> returnAllUserCampaignList(String id) {
        List<Campaign> campaignList = campaignRepository.findByUserId(id);
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null;
    }

    //Retorna TODAS campanhas
    public List<CampaignDTO> returnAllCampaignList() {
        List<Campaign> campaignList = campaignRepository.findAll();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null;
    }

    public CampaignDTO updateCampaign(String id, CampaignUpdateDTO campaignUpdateDTO) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(id); //TROCAR PARA FINDBYACTIVEID DEPOIS

        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            campaignUpdateDTO.update(campaign);
            campaignRepository.save(campaign);
            return CampaignDTO.from(campaign);
        }
        return null;
    }

    public CampaignDTO closeCampaign(String id) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(id); //TROCAR PARA FINDBYACTIVEID DEPOIS
        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            campaign.setStatus(CampaignStatus.CLOSED);
            campaignRepository.save(campaign);
            return CampaignDTO.from(campaign);
        }
        return null;
    }
}
