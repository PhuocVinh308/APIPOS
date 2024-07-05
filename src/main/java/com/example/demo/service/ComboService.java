package com.example.demo.service;

import com.example.demo.DTO.ComboDto;
import com.example.demo.DTO.ComboReponse;
import com.example.demo.model.Combo;
import com.example.demo.model.Product;
import com.example.demo.repository.ComboRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
            Long drinkId = (Long) result[0];
            Long foodId = (Long) result[1];
            Product drink = productRepository.findById(drinkId)
                    .orElseThrow(() -> new RuntimeException("Drink with ID " + drinkId + " not found"));
            Product food = productRepository.findById(foodId)
                    .orElseThrow(() -> new RuntimeException("Food with ID " + foodId + " not found"));
            int check = comboRepository.getFoodAndDrink(drinkId, foodId).size();
            if (check == 0) {
                Combo combo = new Combo();
                combo.setDrink(drink);
                combo.setFood(food);
                comboList.add(combo);
            }
        }
        return comboList;
    }

    @Transactional
    public Combo saveCombo(ComboReponse comboResponse) {
        Product food = productRepository.findById(comboResponse.getFood()).orElseThrow(() -> new RuntimeException("Khong co do an!"));
        Product drink = productRepository.findById(comboResponse.getDrink()).orElseThrow(() -> new RuntimeException("Khong co nuoc!"));

        int totalPriceCb = (int) (food.getPrice() + drink.getPrice());
        comboResponse.setTotalPrice(comboResponse.getTotalPrice() == 0 ? (int) (totalPriceCb * 0.9) : comboResponse.getTotalPrice());

        Combo combo = new Combo();
        combo.setFood(food);
        combo.setDrink(drink);
        combo.setTotalPrice(comboResponse.getTotalPrice());

        return comboRepository.save(combo);
    }

    public Combo getComboById(Long id) {
        return comboRepository.getComboById(id);
    }

    public void deleteCombo(Long id) {
        comboRepository.deleteById(id);
    }

    @Transactional()
    public List<Combo> getAll() {
       return comboRepository.getAll();
    }
}
