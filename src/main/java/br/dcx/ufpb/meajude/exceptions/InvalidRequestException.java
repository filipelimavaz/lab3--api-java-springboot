package br.dcx.ufpb.meajude.exceptions;

public class InvalidRequestException extends AbstractException {

    public InvalidRequestException(String title, String details) {
        super(title, details);
    }
}
