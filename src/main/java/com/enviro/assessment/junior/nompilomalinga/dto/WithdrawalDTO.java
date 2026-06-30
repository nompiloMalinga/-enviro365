package com.enviro.assessment.junior.nompilomalinga.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalDTO {

    private Long investorId;
    private Long productId;
    private double amount;
    private LocalDateTime withdrawalDate;
    private String status;

}
