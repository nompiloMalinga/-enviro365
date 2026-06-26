package com.enviro.assessment.junior.nompilomalinga.controller;


import com.enviro.assessment.junior.nompilomalinga.dto.WithdrawalRequestDTO;
import com.enviro.assessment.junior.nompilomalinga.service.CsvExportService;
import com.enviro.assessment.junior.nompilomalinga.service.WithdrawalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;
    private final CsvExportService csvExportService;


    public WithdrawalController(WithdrawalService withdrawalService, CsvExportService csvExportService) {
        this.withdrawalService = withdrawalService;
        this.csvExportService = csvExportService;
    }


    @PostMapping
    public ResponseEntity<WithdrawalRequestDTO> processWithdrawal(@RequestBody WithdrawalRequestDTO withdrawalDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(withdrawalService.withdrawalNotices(withdrawalDTO));
    }

    @GetMapping("/{investorId}")
    public ResponseEntity<List<WithdrawalRequestDTO>> getWithdrawalHistory(@PathVariable Long investorId) {
        return ResponseEntity.ok(withdrawalService.getWithdrawalHistory(investorId));
    }

    @GetMapping("/{investorId}/export")
    public ResponseEntity<byte[]> exportCsv(
            @PathVariable Long investorId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {

        String csv = csvExportService.exportCsv(investorId, status, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=withdrawals.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok().headers(headers).body(csv.getBytes());
    }
}
