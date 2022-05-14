package com.example.ration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class NormalRecipe implements Serializable {
    private String name;

    private String description;

    private Map<String, Integer> products;

}
