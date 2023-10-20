package br.dcx.ufpb.meajude.entities.enums;

public enum UserType {
    ONG("Ong"),
    PERSON("Person"),
    CHURCH("Church"),
    SOCIETY("Society"),
    ASSOCIATION("Association"),
    STATE_GOVERNMENT("StateGovernment"),
    FEDERAL_GOVERNMENT("FederalGovernment"),
    MUNICIPAL_GOVERNMENT("MunicipalGovernment");

    private String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
