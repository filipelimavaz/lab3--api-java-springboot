package br.com.ufpb.meajude.exceptions;

public class NotFoundException extends AbstractException {

    public NotFoundException(String title, String details) {
        super(title, details);
    }
}
