package com.storyfactory.architect.repository;

import com.storyfactory.architect.entity.CharacterCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterCardRepository extends JpaRepository<CharacterCard, Long> {
    List<CharacterCard> findByNovelId(Long novelId);
}
