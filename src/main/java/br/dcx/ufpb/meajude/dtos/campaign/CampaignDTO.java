package br.dcx.ufpb.meajude.dtos.campaign;

import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class CampaignDTO {

    private CampaignStatus campaignStatus;
    private String title;
    private String description;
    private BigDecimal donationAmount;
    private BigDecimal goal;
    private Date startDate;
    private Date endDate;

    public static CampaignDTO from(Campaign campaign) {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setCampaignStatus(campaign.getStatus());
        campaignDTO.setTitle(campaign.getTitle());
        campaignDTO.setDescription(campaign.getDescription());
        campaignDTO.setDonationAmount(campaign.getDonationAmount());
        campaignDTO.setGoal(campaign.getGoal());
        campaignDTO.setStartDate(campaign.getStartDate());
        campaignDTO.setEndDate(campaign.getEndDate());
        return campaignDTO;
    }
}

