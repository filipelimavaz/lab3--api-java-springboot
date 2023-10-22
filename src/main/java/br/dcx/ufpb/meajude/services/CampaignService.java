package br.dcx.ufpb.meajude.services;

import br.dcx.ufpb.meajude.dtos.campaign.CampaignDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignRegistrationDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignUpdateDTO;
import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import br.dcx.ufpb.meajude.repositories.CampaignRepository;
import br.dcx.ufpb.meajude.repositories.UserRepository;
import br.dcx.ufpb.meajude.exceptions.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    @Autowired
    private Validator validator;

    @Transactional
    public CampaignDTO createCampaign(CampaignRegistrationDTO campaignRegistrationDTO) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to create a campaign.");
        }

        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Campaign campaign = new Campaign();
        campaign.setTitle(campaignRegistrationDTO.getTitle());
        campaign.setDescription(campaignRegistrationDTO.getDescription());
        campaign.setDonationAmount(new BigDecimal(0));
        campaign.setGoal(campaignRegistrationDTO.getGoal());
        campaign.setStartDate(campaignRegistrationDTO.getStartDate());
        campaign.setEndDate(campaignRegistrationDTO.getEndDate());
        campaign.setCreationDate(currentDate);
        campaign.setUser(authorizationService.getLoggedUser());

        if(campaign.getStartDate().after(currentDate)) {
            campaign.setStatus(CampaignStatus.NOT_STARTED);
        } else {
            campaign.setStatus(CampaignStatus.ACTIVE);
        }

        if(campaign.getStartDate().after(campaign.getEndDate())) {
            throw new InvalidFieldException("Campaign can't be created: Invalid date range",
                    "Please verify the start date. The start date cannot be later than the end date.");
        }

        Set<ConstraintViolation<Object>> violations = validator.validate(campaign);
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
        throw new NotFoundException("Campaign not found",
                "Please verify if the campaign id is correct or if it's campaign is registered on the platform.");
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
        throw new NotFoundException("No active campaigns found",
                "There are no active campaigns registered.");
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
        throw new NotFoundException("No active campaigns found",
                "There are no active campaigns registered.");
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
        throw new NotFoundException("No closed campaigns found",
                "There are no closed campaigns registered.");
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
        throw new NotFoundException("No active campaigns found",
                "There are no active campaigns that reached goal registered.");
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
        throw new NotFoundException("No closed campaigns found",
                "There are no closed campaigns that reached goal registered.");
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
        throw new NotFoundException("No upcoming campaigns found",
                "There are no upcoming campaigns at the moment.");
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
        throw new NotFoundException("No closed campaigns found",
                "There are no closed campaigns registered.");
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
        throw new NotFoundException("No campaigns found",
                "This user was no campaigns.");
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
        throw new NotFoundException("No campaigns found",
                "There are no campaigns in the database.");
    }

    public CampaignDTO updateCampaign(String id, CampaignUpdateDTO campaignUpdateDTO) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to create a campaign.");
        }
        Optional<Campaign> optionalCampaign = campaignRepository.findActiveCampaignById(Long.parseLong(id));
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
                if(campaign.getEndDate().after(currentDate) || campaign.getUser().equals(authorizationService.getLoggedUser())) {
                    campaignUpdateDTO.update(campaign);

                    Set<ConstraintViolation<Campaign>> violations = localValidatorFactoryBean.validate(campaign);
                    if (violations.isEmpty()) {
                        campaignRepository.save(campaign);
                        return CampaignDTO.from(campaign);
                    } else {
                        return null; //NÃO FOI POSSÍVEL ATUALIZAR A CAMPANHA POIS UM DOS ATRIBUTOS FOI VIOLADO
                    }
                } else {
                    throw new InvalidRequestException("Invalid Resquest",
                            "The required operation cannot be performed because the final campaign deadline has not been reached" +
                            "or the logged in user is not the owner of the campaign.");
                }
            }
        throw new NotFoundException("Campaign not found",
                "Please verify if the campaign id is correct or if it's campaign is registered on the platform.");
    }

    public CampaignDTO closeCampaign(String id) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to create a campaign.");
        }

        Optional<Campaign> optionalCampaign = campaignRepository.findActiveCampaignById(Long.parseLong(id));
        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            if(campaign.getUser().equals(authorizationService.getLoggedUser())) {
                campaign.setStatus(CampaignStatus.CLOSED);
                return CampaignDTO.from(campaign);
            } else {
                throw new InvalidRequestException("Invalid Resquest",
                        "The campaign only be finished by his owner.");
            }
        }
        throw new NotFoundException("Campaign not found",
                "Please verify if the campaign id is correct or if it's campaign is registered on the platform.");
    }
    public CampaignDTO deleteCampaign(String id) {
        if(!authorizationService.isUserLoggedIn()) {
            throw new InvalidRequestException("Invalid Resquest",
                    "You must be logged in to create a campaign.");
        }

        Optional<Campaign> optionalCampaign = campaignRepository.findActiveCampaignById(Long.parseLong(id));
        UserDetails userDetails = userRepository.findByEmail(authorizationService.getLoggedUser().getEmail());

        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            if(campaign.getUser().equals(authorizationService.getLoggedUser()) || userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                if(campaign.getDonations().isEmpty()) {
                    campaignRepository.delete(campaign);
                } else {
                    campaign.setStatus(CampaignStatus.CLOSED);
                    campaignRepository.save(campaign);
                }
                return CampaignDTO.from(campaign);
            }
            throw new UnauthorizedException("User does not have permission",
                    "The required operation cannot be performed by this user");
        }
        throw new NotFoundException("Campaign not found",
                "Please verify if the campaign id is correct or if it's campaign is registered on the platform.");
    }
}
