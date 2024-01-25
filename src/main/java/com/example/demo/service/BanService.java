
package com.example.demo.service;

import com.example.demo.model.Ban;
import com.example.demo.repository.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Ban> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<Ban> getTableById(Long tableId) {
        return tableRepository.findById(tableId);
    }
    
    public Optional<Ban> getTableByStatus(boolean statusTable){
    	return tableRepository.findByStatus(statusTable);
    }

    public Ban saveTable(Ban table) {
        return tableRepository.save(table);
    }

    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
