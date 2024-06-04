package com.example.demo.service;

import com.example.demo.DTO.ComboDto;
import com.example.demo.model.Combo;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ComboService {

    private final ComboRepository comboRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ComboService(ComboRepository comboRepository,ProductRepository productRepository)  {
        this.comboRepository = comboRepository;
       this.productRepository = productRepository;

    }

    public List<Combo> viewCombo() {
        List<Object[]> resultList = comboRepository.viewCombo();

        List<Combo> comboList = new ArrayList<>();
        for (Object[] result : resultList) {
            Combo combo = new Combo();
            combo.setFood(productRepository.getById((Long) result[0]));
            combo.setDrink(productRepository.getById((Long) result[1]));
            comboList.add(combo);
        }
        return comboList;
    }

}
