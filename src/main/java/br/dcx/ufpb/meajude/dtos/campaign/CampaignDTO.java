package br.dcx.ufpb.meajude.dtos.campaign;

import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class CampaignDTO {

    private CampaignStatus campaignStatus;
    private String id;
    private String title;
    private String description;
    private BigDecimal donationAmount;
    private BigDecimal goal;
    private LocalDate startDate;
    private LocalDate endDate;
    private String username;

    public static CampaignDTO from(Campaign campaign) {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(String.valueOf(campaign.getId()));
        campaignDTO.setCampaignStatus(campaign.getStatus());
        campaignDTO.setTitle(campaign.getTitle());
        campaignDTO.setDescription(campaign.getDescription());
        campaignDTO.setDonationAmount(campaign.getDonationAmount());
        campaignDTO.setGoal(campaign.getGoal());
        campaignDTO.setStartDate(campaign.getStartDate());
        campaignDTO.setEndDate(campaign.getEndDate());
        campaignDTO.setUsername(campaign.getUser().getUsername());

        return campaignDTO;
    }
}

