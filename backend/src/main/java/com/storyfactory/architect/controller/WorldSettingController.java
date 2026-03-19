package com.storyfactory.architect.controller;

import com.storyfactory.architect.entity.WorldSetting;
import com.storyfactory.architect.repository.WorldSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/architect/world-settings")
public class WorldSettingController {

    @Autowired
    private WorldSettingRepository worldSettingRepository;

    @PostMapping
    public WorldSetting createSetting(@RequestBody WorldSetting setting) {
        return worldSettingRepository.save(setting);
    }

    @GetMapping("/novel/{novelId}")
    public List<WorldSetting> getSettingsByNovelId(@PathVariable Long novelId) {
        return worldSettingRepository.findByNovelId(novelId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorldSetting> getSettingById(@PathVariable Long id) {
        return worldSettingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorldSetting> updateSetting(@PathVariable Long id, @RequestBody WorldSetting settingDetails) {
        return worldSettingRepository.findById(id)
                .map(setting -> {
                    setting.setType(settingDetails.getType());
                    setting.setName(settingDetails.getName());
                    setting.setAttributes(settingDetails.getAttributes());
                    return ResponseEntity.ok(worldSettingRepository.save(setting));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        return worldSettingRepository.findById(id)
                .map(setting -> {
                    worldSettingRepository.delete(setting);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
