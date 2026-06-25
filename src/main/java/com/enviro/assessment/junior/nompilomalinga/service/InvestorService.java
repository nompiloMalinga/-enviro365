package com.enviro.assessment.junior.nompilomalinga.service;

import com.enviro.assessment.junior.nompilomalinga.dto.InvestorDTO;
import com.enviro.assessment.junior.nompilomalinga.dto.ProductDTO;
import com.enviro.assessment.junior.nompilomalinga.entity.Investor;
import com.enviro.assessment.junior.nompilomalinga.entity.Product;
import com.enviro.assessment.junior.nompilomalinga.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public InvestorDTO saveInvestorDetails(Investor investor){
        return convertEntityToDto(investorRepository.save(investor)) ;

    }

    public InvestorDTO getInvestorById(Long id){
        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investor not found"));
        return  convertEntityToDto(investor);

    }


    private InvestorDTO convertEntityToDto(Investor investor){
        InvestorDTO investorDTO = new InvestorDTO();
        investorDTO.setId(investor.getId());
        investorDTO.setName(investor.getName());
        investorDTO.setSurname(investor.getSurname());
        investorDTO.setAge(investor.getAge());
        investorDTO.setProducts(investor.getProducts()
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getProductType(), product.getBalance()))
                .collect(Collectors.toList()));

        return  investorDTO;
    }
}
