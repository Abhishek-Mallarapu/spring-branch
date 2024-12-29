package com.example.spring_batch.config;

import org.springframework.batch.item.ItemProcessor;
import com.example.spring_batch.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        // Example logic: Convert the customer's first and last name to uppercase
        if (item.getFirstName() != null) {
            item.setFirstName(item.getFirstName().toUpperCase());
        }
        if (item.getLastName() != null) {
            item.setLastName(item.getLastName().toUpperCase());
        }

        // Add more processing logic if needed

        return item;
    }
}
