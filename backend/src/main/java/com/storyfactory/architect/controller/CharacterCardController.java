package com.storyfactory.architect.controller;

import com.storyfactory.architect.entity.CharacterCard;
import com.storyfactory.architect.repository.CharacterCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/architect/characters")
public class CharacterCardController {

    @Autowired
    private CharacterCardRepository characterCardRepository;

    @PostMapping
    public CharacterCard createCharacter(@RequestBody CharacterCard character) {
        return characterCardRepository.save(character);
    }

    @GetMapping("/novel/{novelId}")
    public List<CharacterCard> getCharactersByNovelId(@PathVariable Long novelId) {
        return characterCardRepository.findByNovelId(novelId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterCard> getCharacterById(@PathVariable Long id) {
        return characterCardRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterCard> updateCharacter(@PathVariable Long id, @RequestBody CharacterCard characterDetails) {
        return characterCardRepository.findById(id)
                .map(character -> {
                    character.setName(characterDetails.getName());
                    character.setRoleType(characterDetails.getRoleType());
                    character.setMotivation(characterDetails.getMotivation());
                    character.setPersonalityTags(characterDetails.getPersonalityTags());
                    character.setHiddenSecrets(characterDetails.getHiddenSecrets());
                    return ResponseEntity.ok(characterCardRepository.save(character));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        return characterCardRepository.findById(id)
                .map(character -> {
                    characterCardRepository.delete(character);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
