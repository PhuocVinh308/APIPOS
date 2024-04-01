package com.example.demo.service;

import com.example.demo.model.Ban;
import com.example.demo.repository.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BanService {

    private final BanRepository tableRepository;

    @Autowired
    public BanService(BanRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Cacheable("tables")
    public List<Ban> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<Ban> getTableById(Long tableId) {
        return tableRepository.findById(tableId);
    }

    public Optional<Ban> getTableByStatus(boolean statusTable){
        return tableRepository.findByStatus(statusTable);
    }

    @CacheEvict(value = "tables", allEntries = true)
    public Ban saveTable(Ban table) {
        return tableRepository.save(table);
    }

    public Ban updateTable(Long tableId, Ban updatedTable) {
        Optional<Ban> existingTableOptional = tableRepository.findById(tableId);

        if (existingTableOptional.isPresent()) {
            Ban existingTable = existingTableOptional.get();
            existingTable.setStatus(updatedTable.isStatus());

            return tableRepository.save(existingTable);
        } else {
            throw new RuntimeException("Không tìm thấy bàn có id: " + tableId);
        }
    }

    @CacheEvict(value = "tables", key = "#result.id")
    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
