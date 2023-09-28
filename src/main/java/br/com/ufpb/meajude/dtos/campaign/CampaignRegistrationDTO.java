package br.com.ufpb.meajude.dtos.campaign;

import br.com.ufpb.meajude.entities.Campaign;
import br.com.ufpb.meajude.entities.enums.CampaignStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class CampaignRegistrationDTO {

    private String title;
    private String description;
    private BigDecimal goal;
    private Date startDate;
    private Date endDate;

    public static CampaignRegistrationDTO from(Campaign campaign) {
        CampaignRegistrationDTO campaignRegistrationDTO = new CampaignRegistrationDTO();
        campaignRegistrationDTO.setTitle(campaign.getTitle());
        campaignRegistrationDTO.setDescription(campaign.getDescription());
        campaignRegistrationDTO.setGoal(campaign.getGoal());
        campaignRegistrationDTO.setStartDate(campaign.getStartDate());
        campaignRegistrationDTO.setEndDate(campaign.getEndDate());
        return campaignRegistrationDTO;
    }
}
