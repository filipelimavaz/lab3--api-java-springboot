package br.dcx.ufpb.meajude.exceptions;

public class InvalidFieldException extends AbstractException {

    public InvalidFieldException(String title, String details) {
        super(title, details);
    }
}
