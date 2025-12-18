package com.example.backend.service;

import com.example.backend.entity.Language;
import com.example.backend.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    // CREATE
    public Language create(Language language) {
        return languageRepository.save(language);
    }

    // GET ALL
    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    // GET BY ID
    public Language getById(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Language not found with id: " + id));
    }

    // UPDATE
    public Language update(Long id, Language updated) {
        Language language = getById(id);

        language.setName(updated.getName());
        language.setLevel(updated.getLevel());

        return languageRepository.save(language);
    }

    // DELETE
    public void delete(Long id) {
        Language language = getById(id);
        languageRepository.delete(language);
    }
}
