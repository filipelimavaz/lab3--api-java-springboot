package br.dcx.ufpb.meajude.dtos.campaign;

import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
public class CampaignUpdateDTO {

    private String updateField;
    private String updateInformation;

    public static CampaignDTO from(Campaign campaign) {
        CampaignDTO campaignDTO = CampaignDTO.from(campaign);
        return campaignDTO;
    }

    public Campaign update(Campaign campaign) {
        String field = this.updateField.toLowerCase();

        switch (field) {
            case "title":
                campaign.setTitle(this.updateInformation);
                return campaign;
            case "description":
                campaign.setDescription(this.updateInformation);
                return campaign;
            case "goal" :
                campaign.setGoal(new BigDecimal(this.updateInformation));
                return campaign;
            case "startDate":
                campaign.setStartDate(Date.valueOf(this.updateInformation));
                return campaign;
            case "endDate":
                campaign.setEndDate(Date.valueOf(this.updateInformation));
                return campaign;
            case "status":
                campaign.setStatus(CampaignStatus.valueOf(this.updateInformation));
                return campaign;
            default:
                return null;
        }
    }
}
