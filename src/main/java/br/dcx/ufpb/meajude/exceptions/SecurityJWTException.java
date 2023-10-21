package br.dcx.ufpb.meajude.exceptions;

public class SecurityJWTException extends AbstractException {
    public SecurityJWTException(String title, String details) {
        super(title, details);
    }
}
