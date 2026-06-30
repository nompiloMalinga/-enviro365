package com.enviro.assessment.junior.nompilomalinga.service;

import com.enviro.assessment.junior.nompilomalinga.dto.InvestorDTO;
import com.enviro.assessment.junior.nompilomalinga.dto.ProductDTO;
import com.enviro.assessment.junior.nompilomalinga.entity.Investor;
import com.enviro.assessment.junior.nompilomalinga.entity.Product;
import com.enviro.assessment.junior.nompilomalinga.repository.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }


    /**
     * Saves a new investor together with their products.
     *
     * @param investor Investor entity received from the client.
     * @return Saved investor as a DTO.
     */
    public InvestorDTO saveInvestorDetails(Investor investor){
        if (investor.getProducts() != null) {
            for (Product product : investor.getProducts()) {
                product.setInvestor(investor);
            }
        }
        return convertEntityToDto(investorRepository.save(investor)) ;

    }

    public InvestorDTO getInvestorById(Long id){
        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investor not found"));
        return  convertEntityToDto(investor);

    }

    public List<InvestorDTO> getAllInvestors() {
        return investorRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
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

        return investorDTO;
    }
}
