package com.example.demo.service;

import com.example.demo.DTO.ComboDto;
import com.example.demo.model.Combo;
import com.example.demo.model.Product;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            Product food = productRepository.getProduct((Long) result[1]);
            Product drink = productRepository.getProduct((Long) result[0]);

            combo.setFood(food);
            combo.setDrink(drink);
            comboList.add(combo);
        }
        return comboList;
    }

    public Combo saveCombo(Combo combo) {
        return comboRepository.save(combo);
    }

    public Optional<Combo> getComboById(Long id) {
        return comboRepository.findById(id);
    }

    public void deleteCombo(Long id) {
        comboRepository.deleteById(id);
    }


}
