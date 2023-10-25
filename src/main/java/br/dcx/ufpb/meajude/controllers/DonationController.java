package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.dtos.donation.DonationDTO;
import br.dcx.ufpb.meajude.dtos.donation.DonationGiverDTO;
import br.dcx.ufpb.meajude.services.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    @Operation(summary = "Donate value", description = "Donating value", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Donations" }, responses = {
            @ApiResponse(responseCode = "200", description = "The donating was made"),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters")
    })
    public ResponseEntity<DonationDTO> donationGiver(@RequestBody DonationGiverDTO donationGiverDTO, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(donationService.donationGiver(donationGiverDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Donations by id", description = "Returning donations by id", tags = { "Donations" }, responses = {
            @ApiResponse(responseCode = "200", description = "Donations returned"),
            @ApiResponse(responseCode = "404", description = "No donation was found")
    })
    public ResponseEntity<DonationDTO> returnDonation(@PathVariable String id) {
        return new ResponseEntity<>(donationService.returnDonation(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all donations", description = "Returning all donations", tags = { "Donations" }, responses = {
            @ApiResponse(responseCode = "200", description = "All donations returned"),
            @ApiResponse(responseCode = "404", description = "No donation was made")
    })
    public ResponseEntity<List<DonationDTO>> returnAllDonationList() {
        return new ResponseEntity<>(donationService.returnAllDonationList(), HttpStatus.OK);
    }

    @GetMapping("/campaigns/{id}")
    @Operation(summary = "Donations by id", description = "Returning all campaign donations by id", tags = { "Donations" }, responses = {
            @ApiResponse(responseCode = "200", description = "Donations returned"),
            @ApiResponse(responseCode = "404", description = "No donation was found")
    })
    public ResponseEntity<List<DonationDTO>> returnCampaignDonationList(@PathVariable String id) {
        return new ResponseEntity<>(donationService.returnCampaignDonationList(id), HttpStatus.OK);
    }
}
