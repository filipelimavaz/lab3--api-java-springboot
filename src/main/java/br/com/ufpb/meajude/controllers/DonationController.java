package br.com.ufpb.meajude.controllers;

import br.com.ufpb.meajude.dtos.donation.DonationDTO;
import br.com.ufpb.meajude.dtos.donation.DonationGiverDTO;
import br.com.ufpb.meajude.services.DonationService;
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

    @PatchMapping
    public ResponseEntity<DonationDTO> donationGiver(@RequestBody DonationGiverDTO donationGiverDTO) {
        return new ResponseEntity<>(donationService.donationGiver(donationGiverDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> returnDonation(@PathVariable String id) {
        return new ResponseEntity<>(donationService.returnDonation(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DonationDTO>> returnAllDonationList() {
        return new ResponseEntity<>(donationService.returnAllDonationList(), HttpStatus.OK);
    }
}
