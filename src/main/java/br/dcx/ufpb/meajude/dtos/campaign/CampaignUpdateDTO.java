package br.dcx.ufpb.meajude.dtos.campaign;

import br.dcx.ufpb.meajude.entities.Campaign;
import br.dcx.ufpb.meajude.entities.enums.CampaignStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
                campaign.setStartDate(LocalDate.parse(updateInformation, formatter));
                return campaign;
            case "endDate":
                campaign.setEndDate(LocalDate.parse(updateInformation, formatter));
                return campaign;
            case "status":
                campaign.setStatus(CampaignStatus.valueOf(this.updateInformation));
                return campaign;
            default:
                return null;
        }
    }
}
