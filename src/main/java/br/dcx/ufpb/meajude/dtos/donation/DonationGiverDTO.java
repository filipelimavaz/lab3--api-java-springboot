package br.dcx.ufpb.meajude.dtos.donation;

import br.dcx.ufpb.meajude.entities.Donation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DonationGiverDTO {

    private Long campaignId;
    private BigDecimal donationValue;

    private static DonationGiverDTO from(Donation donation) {
        DonationGiverDTO donationGiverDTO = new DonationGiverDTO();
        donationGiverDTO.setCampaignId(donation.getCampaign().getId());
        donationGiverDTO.setDonationValue(donation.getDonationValue());
        return donationGiverDTO;
    }
}
