package com.enviro.assessment.junior.nompilomalinga.service;

import com.enviro.assessment.junior.nompilomalinga.dto.WithdrawalDTO;
import com.enviro.assessment.junior.nompilomalinga.entity.Investor;
import com.enviro.assessment.junior.nompilomalinga.entity.Product;
import com.enviro.assessment.junior.nompilomalinga.entity.Withdrawal;
import com.enviro.assessment.junior.nompilomalinga.repository.InvestorRepository;
import com.enviro.assessment.junior.nompilomalinga.repository.ProductRepository;
import com.enviro.assessment.junior.nompilomalinga.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WithdrawalService {


    /**
     * Service class responsible for handling all withdrawal-related business logic.
     * Enforces business rules such as age restrictions, balance limits,
     * and maximum withdrawal percentages before processing a withdrawal notice.
     */

    private final WithdrawalRepository withdrawalRepository;
    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;


    public WithdrawalService(WithdrawalRepository withdrawalRepository, InvestorRepository investorRepository, ProductRepository productRepository) {
        this.withdrawalRepository = withdrawalRepository;
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    /**
     * Processes a withdrawal notice by validating business rules,
     * deducting the withdrawal amount from the product balance,
     * and persisting the withdrawal record.
     * Business rules enforced:
     * - Retirement product withdrawals are only allowed for investors aged above 65
     * - Withdrawal amount must not exceed the current product balance
     * - Withdrawal amount must not exceed 90% of the product balance
     *
     * @param withdrawalDTO the withdrawal request containing investor ID,
     *                             product ID, and withdrawal amount
     * @return a DTO representing the saved withdrawal record
     * @throws RuntimeException if investor or product is not found,
     *                          or if any business rule is violated
     */

    public WithdrawalDTO withdrawalNotices(WithdrawalDTO withdrawalDTO) {
        Investor investor = investorRepository.findById(withdrawalDTO.getInvestorId())
                .orElseThrow(() -> new RuntimeException("Investor not found"));

        Product product = productRepository.findById(withdrawalDTO.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        if(product.getProductType().equalsIgnoreCase("Retirement") && investor.getAge()<= 65){
            throw new RuntimeException("Investor must be over 65 for retirement withdrawal");
        }
        if(withdrawalDTO.getAmount()> product.getBalance()){
            throw new RuntimeException("Withdrawal amount exceeds product balance");
        }
        if (withdrawalDTO.getAmount() > product.getBalance() * 0.90){
            throw  new RuntimeException("Withdrawal cannot exceed 90% of product balance");
        }

        product.setBalance(product.getBalance() - withdrawalDTO.getAmount());
        productRepository.save(product);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(withdrawalDTO.getAmount());
        withdrawal.setWithdrawalDate(LocalDateTime.now());
        withdrawal.setStatus("Successful");
        withdrawal.setInvestor(investor);
        withdrawal.setProduct(product);

        return convertEntityToDto(withdrawalRepository.save(withdrawal));

    }

    private WithdrawalDTO convertEntityToDto(Withdrawal withdrawal) {
        WithdrawalDTO withdrawal_dto = new WithdrawalDTO();
        withdrawal_dto.setInvestorId(withdrawal.getInvestor().getId());
        withdrawal_dto.setProductId(withdrawal.getProduct().getId());
        withdrawal_dto.setAmount(withdrawal.getAmount());
        withdrawal_dto.setWithdrawalDate(withdrawal.getWithdrawalDate());
        withdrawal_dto.setStatus(withdrawal.getStatus());
        return withdrawal_dto;
    }


    public List<WithdrawalDTO> getAllWithdrawalHistory() {
        return withdrawalRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }




}
