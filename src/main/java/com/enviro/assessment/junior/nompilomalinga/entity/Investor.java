package com.enviro.assessment.junior.nompilomalinga.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

}
