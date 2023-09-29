package br.com.ufpb.meajude.dtos.donation;

import br.com.ufpb.meajude.entities.Donation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class DonationDTO {

    private Long userId;
    private Long campaignId;
    private BigDecimal donationValue;
    private Date donationDate;

    public static DonationDTO from(Donation donation) {
        DonationDTO donationDTO = new DonationDTO();
        donationDTO.setUserId(donation.getUser().getId());
        donationDTO.setCampaignId(donation.getCampaign().getId());
        donationDTO.setDonationValue(donation.getDonationValue());
        donationDTO.setDonationDate(donation.getDonationDate());
        return donationDTO;
    }
}
