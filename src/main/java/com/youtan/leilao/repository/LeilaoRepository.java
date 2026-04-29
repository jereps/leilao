package com.youtan.leilao.repository;

import com.youtan.leilao.model.Leilao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeilaoRepository extends JpaRepository<Leilao,Long> {

    @Query("SELECT l FROM Leilao l LEFT JOIN FETCH l.itens WHERE l.id = :id")
    Optional<Leilao> findLeilaoWithItens(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM leilao_itens WHERE item_id = :itemId AND item_type = :type", nativeQuery = true)
    void deleteItemReferenceFromJoinTable(Long itemId, String type);
}
