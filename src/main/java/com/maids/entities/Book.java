package com.maids.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Book", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "ISBN", unique = true, nullable = false)
    private String isbn;

    @Column(name = "edition_number")
    private int editionNumber;

    @Column(name = "category")
    private String category;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Transactions> transactions;

//    public Book(Integer o, String title, String author, int publicationYear, String isbn, int editionNumber, String category, String city) {
//    }
}
