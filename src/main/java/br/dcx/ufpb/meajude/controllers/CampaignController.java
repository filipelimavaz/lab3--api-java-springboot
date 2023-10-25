package br.dcx.ufpb.meajude.controllers;

import br.dcx.ufpb.meajude.dtos.campaign.CampaignDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignRegistrationDTO;
import br.dcx.ufpb.meajude.dtos.campaign.CampaignUpdateDTO;
import br.dcx.ufpb.meajude.services.CampaignService;
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
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/create")
    @Operation(summary = "Crete a campaign", description = "Creating a campaign", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "201", description = "Campaign created"),
            @ApiResponse(responseCode = "400", description = "Incorrect parameters"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated")

    })
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignRegistrationDTO campaignRegistrationDTO, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(campaignService.createCampaign(campaignRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Return a campaign by id", description = "Return a campaign by ID", tags = { "Campaigns"}, responses = {
            @ApiResponse(responseCode = "200", description = "Campaign returned"),
            @ApiResponse(responseCode = "404", description = "The campaign was not found")
    })
    public ResponseEntity<CampaignDTO> returnCampaign(@PathVariable String id) {
        return new ResponseEntity<>(campaignService.returnCampaign(id), HttpStatus.OK);
    }

    @GetMapping("/active")
    @Operation(summary = "Return active campaigns", description = "Listing all active campaigns", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Campaign list returned"),
            @ApiResponse(responseCode = "404", description = "No campaigns were found")
    })
    public ResponseEntity<List<CampaignDTO>> returnActiveCampaignList() {
        return new ResponseEntity<>(campaignService.returnActiveCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/active_by_creationDate")
    @Operation(summary = "Active campaigns", description = "Return active campaigns ordered by creation date", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Active campaigns"),
            @ApiResponse(responseCode = "404", description = "No campaigns were found")
    })
    public ResponseEntity<List<CampaignDTO>> returnActiveCampaignsOrderedByCreationDate() {
        return new ResponseEntity<>(campaignService.returnActiveCampaignsOrderedByCreationDate(), HttpStatus.OK);
    }

    @GetMapping("/closed_by_creationDate")
    @Operation(summary = "Closed campaigns", description = "Return closed camapaigns ordered by creation date", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Active campaigns"),
            @ApiResponse(responseCode = "404", description = "No campaigns were found")
    })
    public ResponseEntity<List<CampaignDTO>> returnClosedCampaignsOrderedByCreationDate() {
        return new ResponseEntity<>(campaignService.returnActiveCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Upcoming campaign list", description = "Return the upcoming campaign list", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Upcoming campaigns"),
            @ApiResponse(responseCode = "404", description = "No upcoming campaign was found")
    })
    public ResponseEntity<List<CampaignDTO>> returnUpcomingCampaignList() {
        return new ResponseEntity<>(campaignService.returnUpcomingCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/closed")
    @Operation(summary = "Closed campaigns", description = "Return closed campaigns", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Closed campaigns"),
            @ApiResponse(responseCode = "404", description = "No closed campaign was found")
    })
    public ResponseEntity<List<CampaignDTO>> returnClosedCampaignList() {
        return new ResponseEntity<>(campaignService.returnClosedCampaignList(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "User campaign list", description = "Return all user campaign list", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "User campaign list"),
            @ApiResponse(responseCode = "404", description = "No campaign was found by this id")
    })
    public ResponseEntity<List<CampaignDTO>> returnAllUserCampaignList(@PathVariable String id) {
        return new ResponseEntity<>(campaignService.returnAllUserCampaignList(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Campaign list", description = "Return campaign list", tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Campaign list"),
            @ApiResponse(responseCode = "404", description = "No campaign was found")
    })
    public ResponseEntity<List<CampaignDTO>> returnAllCampaignList() {
        return new ResponseEntity<>(campaignService.returnAllCampaignList(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update campaign", description = "Updating campaign", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "Campaign updated"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated"),
            @ApiResponse(responseCode = "404", description = "Incorrect parameters")

    })
    public ResponseEntity<CampaignDTO> updateCampaign(@PathVariable String id, @RequestBody CampaignUpdateDTO campaignUpdateDTO, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(campaignService.updateCampaign(id, campaignUpdateDTO), HttpStatus.OK);
    }

    @PatchMapping("/close/{id}")
    @Operation(summary = "Close campaign", description = "Closing campaign", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "The campaign was closed"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated"),
            @ApiResponse(responseCode = "404", description = "Incorrect parameters")
    })
    public ResponseEntity<CampaignDTO> closeCampaign(@PathVariable String id, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(campaignService.closeCampaign(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete campaign", description = "Deleting campaign", security = @SecurityRequirement(name = "bearerAuth"), tags = { "Campaigns" }, responses = {
            @ApiResponse(responseCode = "200", description = "The campaign was deleted"),
            @ApiResponse(responseCode = "403", description = "User must be authenticated"),
            @ApiResponse(responseCode = "404", description = "Incorrect parameters")
    })
    public ResponseEntity<CampaignDTO> deleteCampaign(@PathVariable String id, @Parameter(description = "Bearer Token Authorization", required = true, hidden = true, schema = @Schema(implementation = String.class)) @RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(campaignService.deleteCampaign(id), HttpStatus.OK);
    }
}
