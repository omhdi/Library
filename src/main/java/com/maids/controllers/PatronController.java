package com.maids.controllers;
import com.maids.dto.PatronDTO;
import com.maids.entities.Book;
import com.maids.entities.CustomResponseEntity;
import com.maids.entities.Patron;
import com.maids.exception.NoPatrensFoundException;
import com.maids.services.PatronsService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PatronController {

    @Autowired
    PatronsService patronsService;

    @GetMapping("/patrons")
    @Cacheable(value = "AllPatronsCache")
    public ResponseEntity<Object> getAllPatrons() {
        List<Patron> patrons = patronsService.getAllPatrons();
        CustomResponseEntity<List<Patron>> response = new CustomResponseEntity(HttpStatus.OK.toString(), "Success", patrons);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<CustomResponseEntity<Patron>> getPatron(@PathVariable Long id) {
        Patron patron = patronsService.getPatronById(id);
        CustomResponseEntity<Patron> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", Collections.singletonList(patron));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/patrons")
    @Transactional
    public ResponseEntity<Object> createPatron(@Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = Patron.builder()
                .name(patronDTO.getName())
                .contactInformation(patronDTO.getContactInformation())
                .birthdate(patronDTO.getBirthdate())
                .build();
        Patron patronCreate = patronsService.createPatron(patron);
        CustomResponseEntity<Book> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/patrons/{id}")
    @Transactional
    public ResponseEntity<CustomResponseEntity<Patron>> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronDTO patronDTO) {
        Patron patron = Patron.builder()
                .name(patronDTO.getName())
                .contactInformation(patronDTO.getContactInformation())
                .birthdate(patronDTO.getBirthdate())
                .build();
        Patron patronUpdate = patronsService.updatePatron(patron, id);
        CustomResponseEntity<Patron> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/patrons/{id}")
    @Transactional
    public ResponseEntity<Object> deletePatron(@PathVariable Long id) {
            patronsService.deleteById(id);
            CustomResponseEntity<Patron> response = new CustomResponseEntity<>(HttpStatus.OK.toString(), "Success", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
