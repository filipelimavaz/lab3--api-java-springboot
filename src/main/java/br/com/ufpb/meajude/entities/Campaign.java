package br.com.ufpb.meajude.entities;

import br.com.ufpb.meajude.entities.enums.CampaignStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CampaignStatus status;

    @NotBlank(message = "Title can't be blank")
    @Size(max = 100, message = "Title length must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Details can't be blank")
    @Size(max = 1000, message = "Description length must not exceed 1000 characters")
    private String description;

    private BigDecimal donationAmount;

    @NotNull(message = "Goal can't be null")
    @Positive(message = "The goal must be greater than zero")
    private BigDecimal goal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Start date can't be null")
    @Future(message = "The end date must be in the present or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "End date can't be null")
    @Future(message = "The end date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Donation> donations;

    public void setDonationAmount(BigDecimal value) {
        if (this.donationAmount == null) {
            this.donationAmount = value;
        } else {
            this.donationAmount = this.donationAmount.add(value);
        }
    }
}
