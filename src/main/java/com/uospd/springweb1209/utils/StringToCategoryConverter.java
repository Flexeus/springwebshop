package com.uospd.springweb1209.utils;

import com.uospd.springweb1209.entities.Category;
import com.uospd.springweb1209.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class StringToCategoryConverter implements Converter<String, Category>, WebMvcConfigurer {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category convert(final String id) {
        return repository.getCategoryById(Long.parseLong(id));
    }


    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(this);
    }

}
