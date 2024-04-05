package com.example.demo.controller;

import com.example.demo.model.Ban;
import com.example.demo.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/table")
public class BanController {

    private final BanService tableService;

    @Autowired
    public BanController(BanService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<Ban> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/{tableId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public Optional<Ban> getTableById(@PathVariable Long tableId) {
        return tableService.getTableById(tableId);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Ban> getTableByStatus(@RequestParam boolean status) {
        return tableService.getTableByStatus(status)
                .map(table -> new ResponseEntity<>(table, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Ban saveTable(@RequestBody Ban table) {
        return tableService.saveTable(table);
    }

    @DeleteMapping("/{tableId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteTable(@PathVariable Long tableId) {
        System.out.print(tableId);
        tableService.deleteTable(tableId);
    }

    @PutMapping("/{tableId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateTable(@PathVariable Long tableId, @RequestBody Ban updateTable) {
        tableService.updateTable(tableId, updateTable);
    }
}
