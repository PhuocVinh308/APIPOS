package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer saveOrUpdateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
}
