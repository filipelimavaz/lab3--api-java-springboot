package br.com.ufpb.meajude.entities.enums;

public enum CampaignStatus {
    NOT_STARTED("Not started"),
    ACTIVE("Active"),
    CLOSED("Closed");

    private String status;

    CampaignStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
