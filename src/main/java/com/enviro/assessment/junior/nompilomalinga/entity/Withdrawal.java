package com.enviro.assessment.junior.nompilomalinga.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "withdrawals")
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private LocalDateTime withdrawalDate;
    private String status;


    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
