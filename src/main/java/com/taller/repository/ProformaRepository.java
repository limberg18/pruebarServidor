package com.taller.repository;

import com.taller.model.Proforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProformaRepository extends JpaRepository<Proforma, Long> {
    @Modifying
    @Query("""
UPDATE Producto p
SET p.stock = p.stock - :cantidad
WHERE p.id = :id
AND p.stock >= :cantidad
""")
    int descontarStockSeguro(@Param("id") Long id,
                             @Param("cantidad") Integer cantidad);
}
