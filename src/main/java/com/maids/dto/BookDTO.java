package com.maids.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private int editionNumber;
    private String category;
    private String city;
}
