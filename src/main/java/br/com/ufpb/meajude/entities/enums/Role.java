package br.com.ufpb.meajude.entities.enums;

public enum Role {
    ADMIN("Admin"),
    NORMAL("Normal");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
