package br.dcx.ufpb.meajude.services;

import br.dcx.ufpb.meajude.dtos.campaign.CampaignDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignRegistrationDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignUpdateDTO;
import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import br.dcx.ufpb.meajude.exceptions.CustomValidationException;
import br.dcx.ufpb.meajude.repositories.CampaignRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Autowired
    private Validator validator;

    @Transactional
    public CampaignDTO createCampaign(CampaignRegistrationDTO campaignRegistrationDTO) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Campaign campaign = new Campaign();
        campaign.setTitle(campaignRegistrationDTO.getTitle());
        campaign.setDescription(campaignRegistrationDTO.getDescription());
        campaign.setDonationAmount(new BigDecimal(0));
        campaign.setGoal(campaignRegistrationDTO.getGoal());
        campaign.setStartDate(campaignRegistrationDTO.getStartDate());
        campaign.setEndDate(campaignRegistrationDTO.getEndDate());
        campaign.setCreationDate(currentDate);

        if(campaign.getStartDate().after(currentDate)) {
            campaign.setStatus(CampaignStatus.NOT_STARTED);
        } else {
            campaign.setStatus(CampaignStatus.ACTIVE);
        }

        if(campaign.getStartDate().after(campaign.getEndDate())) {
            return null; //RETORNA EXCEÇÃO SE A DATA DE INÍCIO FOR DEPOIS DA FINAL
        }

        Set<ConstraintViolation<Campaign>> violations = validator.validate(campaign);
        if (!violations.isEmpty()) {
            throw new CustomValidationException("One or more fields have validation errors", violations);
        }

        campaignRepository.save(campaign);
        return CampaignDTO.from(campaign);
    }

    //Retorna campanhas por ID
    public CampaignDTO returnCampaign(String id) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(Long.parseLong(id));

        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            return CampaignDTO.from(campaign);
        }
        return null; //EXCEÇÃO PARA CASO A CAMPANHA NÃO SEJA ENCONTRADA
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
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }

    //Retorna todas campanhas ATIVAS ordenadas pela DATA DE CRIAÇÃO
    public List<CampaignDTO> returnActiveCampaignsOrderedByCreationDate() {
        List<Campaign> campaignList = campaignRepository.findActiveCampaignsOrderedByCreationDate();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }

    //Retorna todas campanhas ENCERRADAS ordenadas pela DATA DE CRIAÇÃO
    public List<CampaignDTO> returnClosedCampaignsOrderedByCreationDate() {
        List<Campaign> campaignList = campaignRepository.findClosedCampaignsOrderedByCreationDate();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }

    //Retorna todas as campanhas ATIVAS que atingiram a META
    public List<CampaignDTO> returnActiveCampaignsReachedGoal() {
        List<Campaign> campaignList = campaignRepository.findActiveCampaignsReachedGoal();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }

    //Retorna todas as campanhas ENCERRADAS que atingiram a META
    public List<CampaignDTO> returnClosedCampaignsReachedGoal() {
        List<Campaign> campaignList = campaignRepository.findClosedCampaignsReachedGoal();
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
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
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
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
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }

    //Retorna todas campanhas do USUÁRIO
    public List<CampaignDTO> returnAllUserCampaignList(String id) {
        List<Campaign> campaignList = campaignRepository.findByUserId(Long.parseLong(id));
        List<CampaignDTO> campaignDTOList = new ArrayList<>();

        if(!campaignList.isEmpty()) {
            for(Campaign c : campaignList) {
                CampaignDTO campaignDTO = CampaignDTO.from(c);
                campaignDTOList.add(campaignDTO);
            }
            return campaignDTOList;
        }
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
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
        return null; //EXCEÇÃO PARA CASO NENHUMA CAMPANHA NÃO SEJA ENCONTRADA
    }



    public CampaignDTO updateCampaign(String id, CampaignUpdateDTO campaignUpdateDTO) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(Long.parseLong(id)); //TROCAR PARA FINDBYACTIVEID DEPOIS
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (optionalCampaign.isPresent()) {
            if(optionalCampaign.get().getEndDate().after(currentDate)) {
                Campaign campaign = optionalCampaign.get();
                campaignUpdateDTO.update(campaign);

                Set<ConstraintViolation<Campaign>> violations = localValidatorFactoryBean.validate(campaign);
                if (violations.isEmpty()) {
                    campaignRepository.save(campaign);
                    return CampaignDTO.from(campaign);
                } else {
                    return null; //NÃO FOI POSSÍVEL ATUALIZAR A CAMPANHA POIS UM DOS ATRIBUTOS FOI VIOLADO
                }
            }
        }
        return null; //NÃO FOI ENCONTRADA NENHUMA CAMPANHA COM ESSE ID
    }

    public CampaignDTO deleteCampaign(String id) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(Long.parseLong(id)); //TROCAR PARA FINDBYACTIVEID DEPOIS
        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            if(campaign.getDonations().isEmpty()) {
                campaignRepository.delete(campaign);
                return CampaignDTO.from(campaign);
            } else {
                return null; //A CAMPANHA NÃO PODE SER DELETADA PQ JÁ FOI FEITO DOAÇÕES
            }
        }
        return null; //NÃO FOI ENCONTRADA NENHUMA CAMPANHA COM ESSE ID
    }

    public CampaignDTO closeCampaign(String id) {
        Optional<Campaign> optionalCampaign = campaignRepository.findById(Long.parseLong(id)); //TROCAR PARA FINDBYACTIVEID DEPOIS
        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            campaign.setStatus(CampaignStatus.CLOSED);
            campaignRepository.save(campaign);
            return CampaignDTO.from(campaign);
        }
        return null; //NÃO FOI ENCONTRADA NENHUMA CAMPANHA COM ESSE ID
    }
}
