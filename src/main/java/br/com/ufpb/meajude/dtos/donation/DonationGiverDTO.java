package br.com.ufpb.meajude.dtos.donation;

import br.com.ufpb.meajude.entities.Donation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DonationGiverDTO {

    private Long campaignId;
    private Long userId;
    private BigDecimal donationValue;

    private static DonationGiverDTO from(Donation donation) {
        DonationGiverDTO donationGiverDTO = new DonationGiverDTO();
        donationGiverDTO.setCampaignId(donation.getCampaign().getId());
        donationGiverDTO.setUserId(donation.getUser().getId());
        donationGiverDTO.setDonationValue(donation.getDonationValue());
        return donationGiverDTO;
    }
}
