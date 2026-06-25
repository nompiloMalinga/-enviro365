package com.enviro.assessment.junior.nompilomalinga.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorDTO {
    private String name;
    private String surname;
    private int age;
    private double balance;
    private List<ProductDTO> products;
}
