package com.enviro.assessment.junior.nompilomalinga.service;

import com.enviro.assessment.junior.nompilomalinga.entity.Withdrawal;
import com.enviro.assessment.junior.nompilomalinga.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvExportService {

    private final WithdrawalRepository withdrawalRepository;

    public CsvExportService(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    public String exportCsv(Long investorId, String status,
                                       LocalDateTime startDate, LocalDateTime endDate) {
        List<Withdrawal> withdrawals = withdrawalRepository.findByInvestorId(investorId)
                .stream()
                .filter(w -> status == null || w.getStatus().equals(status))
                .filter(w -> startDate == null || !w.getWithdrawalDate().isBefore(startDate))
                .filter(w -> endDate == null || !w.getWithdrawalDate().isAfter(endDate))
                .toList();

        StringBuilder csv = new StringBuilder();
        csv.append("Amount,Date,Status,Product Type\n");
        for (Withdrawal w : withdrawals) {
            csv.append(w.getAmount()).append(",")
                    .append(w.getWithdrawalDate()).append(",")
                    .append(w.getStatus()).append(",")
                    .append(w.getProduct().getProductType()).append("\n");
        }
        return csv.toString();
    }
}
