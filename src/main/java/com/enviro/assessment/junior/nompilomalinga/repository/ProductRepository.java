package com.enviro.assessment.junior.nompilomalinga.repository;

import com.enviro.assessment.junior.nompilomalinga.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
