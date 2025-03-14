package com.maids.services;


import com.maids.entities.Patron;
import com.maids.exception.NoPatrensFoundException;
import com.maids.repositories.PatronsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronsService {

    @Autowired
    PatronsRepositories patronsRepositories;

    public List<Patron> getAllPatrons(){
        return patronsRepositories.findAll();
    }

    public Optional<Patron> findPatronById(Long id){
        return patronsRepositories.findById(id);
    }
    public Patron getPatronById(Long id){
        return findPatronById(id).orElseThrow(() -> new NoPatrensFoundException("No Patron found with id:" + id));
    }

    public Patron createPatron(Patron patron) {
        return patronsRepositories.save(patron);
    }

    public void deleteById(Long id){
        if(!patronsRepositories.existsById(id)){
            throw new NoPatrensFoundException("Patron not found with id: " + id);
        }
        patronsRepositories.deleteById(id);
    }

    public Patron updatePatron(Patron patron, Long id) {
        Patron oldPatron = patronsRepositories.findById(id)
                .orElseThrow(() -> new NoPatrensFoundException("Patron not found with id: " + id));
        oldPatron.setName(patron.getName());
        oldPatron.setBirthdate(patron.getBirthdate());
        oldPatron.setContactInformation(patron.getContactInformation());
        return patronsRepositories.save(oldPatron);
    }
}
