package br.com.ufpb.meajude.controllers;

import br.com.ufpb.meajude.dtos.campaign.CampaignDTO;
import br.com.ufpb.meajude.dtos.campaign.CampaignRegistrationDTO;
import br.com.ufpb.meajude.dtos.campaign.CampaignUpdateDTO;
import br.com.ufpb.meajude.services.CampaignService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/create")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignRegistrationDTO campaignRegistrationDTO) {
        return new ResponseEntity<>(campaignService.createCampaign(campaignRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> returnCampaign(@PathVariable String id) {
        return new ResponseEntity<>(campaignService.returnCampaign(id), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CampaignDTO>> returnActiveCampaignList() {
        return new ResponseEntity<>(campaignService.returnActiveCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<CampaignDTO>> returnUpcomingCampaignList() {
        return new ResponseEntity<>(campaignService.returnUpcomingCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<CampaignDTO>> returnClosedCampaignList() {
        return new ResponseEntity<>(campaignService.returnClosedCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampaignDTO>> returnAllCampaignList() {
        return new ResponseEntity<>(campaignService.returnAllCampaignList(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CampaignDTO> updateCampaign(@PathVariable String id, @RequestBody CampaignUpdateDTO campaignUpdateDTO) {
        return new ResponseEntity<>(campaignService.updateCampaign(id, campaignUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CampaignDTO> closeCampaign(@PathVariable String id) {
        return new ResponseEntity<>(campaignService.closeCampaign(id), HttpStatus.OK);
    }
}