package com.storyfactory.architect.repository;

import com.storyfactory.architect.entity.WorldSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorldSettingRepository extends JpaRepository<WorldSetting, Long> {
    List<WorldSetting> findByNovelId(Long novelId);
}
