package com.example.ration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalRecipe implements Serializable {
    private String name;

    private String encodedView;

    private String description;

    private Map<String, Integer> products;

}
