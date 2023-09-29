package br.com.ufpb.meajude.dtos.errors;

import lombok.Data;

@Data
public class ErrorDetailsDTO {
    private int status;
    private String type;
    private String title;
    private String detail;
}