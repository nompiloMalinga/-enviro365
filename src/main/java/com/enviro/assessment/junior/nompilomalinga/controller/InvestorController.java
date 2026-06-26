package com.enviro.assessment.junior.nompilomalinga.controller;

import com.enviro.assessment.junior.nompilomalinga.dto.InvestorDTO;
import com.enviro.assessment.junior.nompilomalinga.entity.Investor;
import com.enviro.assessment.junior.nompilomalinga.service.InvestorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {

    private final InvestorService investorService;


    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }


    @PostMapping()
    public ResponseEntity<InvestorDTO> createInvestor(@RequestBody Investor investor){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(investorService.saveInvestorDetails(investor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorDTO> getInvestorById(@PathVariable Long id) {
        return ResponseEntity.ok(investorService.getInvestorById(id));
    }

}
