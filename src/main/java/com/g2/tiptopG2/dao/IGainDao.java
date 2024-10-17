package com.g2.tiptopG2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.g2.tiptopG2.models.GainEntity;

@Repository
public interface IGainDao extends JpaRepository<GainEntity, Integer> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
    // Par exemple, si vous voulez trouver un gain par code :
    GainEntity findByCode(String code);
}
