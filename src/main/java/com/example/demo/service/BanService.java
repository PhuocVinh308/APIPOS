package com.example.demo.service;

import com.example.demo.model.Ban;
import com.example.demo.repository.BanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BanService {
    @Autowired
    private final BanRepository tableRepository;


    @Autowired
    public BanService(BanRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Cacheable("tables")
    public List<Ban> getAllTables() {
        return tableRepository.findBan();
    }

    public Optional<Ban> getTableById(Long tableId) {
        return tableRepository.findById(tableId);
    }

    public Optional<Ban> getTableByStatus(boolean statusTable){
        return tableRepository.findByStatus(statusTable);
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
        System.out.print(tableId);
        tableRepository.deleteBanById(tableId);
    }
    @CacheEvict(value = "tables", allEntries = true)
    public void revertOrUpdate(Long tableId) {
        Optional<Ban> tableOptional = tableRepository.findById(tableId);
        if (tableOptional.isPresent()) {
            Ban table = tableOptional.get();
            if (table.is_deleted()) {
                table.set_deleted(false);
                tableRepository.save(table);
            } else {
                Ban obj = new Ban();
                obj.setId(tableId);
                obj.setStatus(false);
                obj.set_deleted(false);
                tableRepository.save(obj);
            }
        } else {
            Ban obj = new Ban();
            obj.setId(tableId);
            obj.setStatus(false);
            obj.set_deleted(false);
            tableRepository.save(obj);        }
    }
}
