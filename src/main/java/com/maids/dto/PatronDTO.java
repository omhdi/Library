package com.maids.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatronDTO {

    private Long id;
    private String name;
    private String contactInformation;
    private Date birthdate;
}
