package com.enviro.assessment.junior.nompilomalinga.service;

import com.enviro.assessment.junior.nompilomalinga.dto.WithdrawalRequestDTO;
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

    private final WithdrawalRepository withdrawalRepository;
    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;


    public WithdrawalService(WithdrawalRepository withdrawalRepository, InvestorRepository investorRepository, ProductRepository productRepository) {
        this.withdrawalRepository = withdrawalRepository;
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    public WithdrawalRequestDTO withdrawalNotices(WithdrawalRequestDTO withdrawalRequestDTO) {
        Investor investor = investorRepository.findById(withdrawalRequestDTO.getInvestorId())
                .orElseThrow(() -> new RuntimeException("Investor not found"));

        Product product = productRepository.findById(withdrawalRequestDTO.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        if(product.getProductType().equalsIgnoreCase("Retirement") && investor.getAge()<= 65){
            throw new RuntimeException("Investor must be over 65 for retirement withdrawal");
        }
        if(withdrawalRequestDTO.getAmount()> product.getBalance()){
            throw new RuntimeException("Withdrawal amount exceeds product balance");
        }
        if (withdrawalRequestDTO.getAmount() > product.getBalance() * 0.90){
            throw  new RuntimeException("Withdrawal cannot exceed 90% of product balance");
        }

        product.setBalance(product.getBalance() - withdrawalRequestDTO.getAmount());
        productRepository.save(product);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(withdrawalRequestDTO.getAmount());
        withdrawal.setWithdrawalDate(LocalDateTime.now());
        withdrawal.setStatus("Successful");
        withdrawal.setInvestor(investor);
        withdrawal.setProduct(product);

        return convertEntityToDto(withdrawalRepository.save(withdrawal));

    }

    private WithdrawalRequestDTO convertEntityToDto(Withdrawal withdrawal) {
        WithdrawalRequestDTO withdrawal_dto = new WithdrawalRequestDTO();
        withdrawal_dto.setInvestorId(withdrawal.getInvestor().getId());
        withdrawal_dto.setProductId(withdrawal.getProduct().getId());
        withdrawal_dto.setAmount(withdrawal.getAmount());
        withdrawal_dto.setWithdrawalDate(withdrawal.getWithdrawalDate());
        withdrawal_dto.setStatus(withdrawal.getStatus());
        return withdrawal_dto;
    }

    public List<WithdrawalRequestDTO> getWithdrawalHistory(Long investorId) {
        return withdrawalRepository.findByInvestorId(investorId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }




}
