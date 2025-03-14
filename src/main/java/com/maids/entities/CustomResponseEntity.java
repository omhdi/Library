package com.maids.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseEntity<T> {

    private String error_code;
    private String error_description;
    private List<T> data;
}
