package com.enviro.assessment.junior.nompilomalinga.repository;

import com.enviro.assessment.junior.nompilomalinga.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WithdrawalRepository  extends JpaRepository<Withdrawal,Long> {
    List<Withdrawal> findByInvestorId(Long investorId);

}
