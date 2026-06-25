package com.enviro.assessment.junior.nompilomalinga.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private  Long id;
    private String productType;
    private double balance;
}
