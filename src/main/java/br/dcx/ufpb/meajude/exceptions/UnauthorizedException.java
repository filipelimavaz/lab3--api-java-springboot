package br.dcx.ufpb.meajude.exceptions;

public class UnauthorizedException extends AbstractException{

    public UnauthorizedException(String title, String details) {
        super(title, details);
    }
}
