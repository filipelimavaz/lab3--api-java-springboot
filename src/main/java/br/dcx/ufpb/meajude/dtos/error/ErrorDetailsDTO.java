package br.dcx.ufpb.meajude.dtos.error;

import lombok.Data;

@Data
public class ErrorDetailsDTO {
    private int status;
    private String type;
    private String title;
    private String detail;
}