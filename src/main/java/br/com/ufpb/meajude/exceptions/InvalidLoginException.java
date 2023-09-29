package br.com.ufpb.meajude.exceptions;

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(String msg) {
        super(msg);
    }
}
