package com.example.demo.controller;

import com.example.demo.DTO.ComboDto;
import com.example.demo.DTO.ComboReponse;
import com.example.demo.model.Combo;
import com.example.demo.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combos")
public class ComboController {

    private final ComboService comboService;

    @Autowired
    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping()
    public List<Combo> viewCombo() {
        return   comboService.viewCombo();
    }

    @PostMapping
    public ResponseEntity<Combo> createCombo(@RequestBody ComboReponse comboReponse) {
        Combo savedCombo = comboService.saveCombo(comboReponse);
        return ResponseEntity.ok(savedCombo);
    }

    @GetMapping("/{id}")
    public Combo getComboById(@PathVariable Long id) {
        Combo combo = comboService.getComboById(id);
        return combo;
    }

    @GetMapping("/all")
    public List<Combo> getComboById() {
        List<Combo> combo = comboService.getAll();
        return combo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        comboService.deleteCombo(id);
        return ResponseEntity.noContent().build();
    }
}
