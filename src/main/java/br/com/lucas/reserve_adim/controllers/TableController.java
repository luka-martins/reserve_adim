package br.com.lucas.reserve_adim.controllers;

import br.com.lucas.reserve_adim.domain.table.CreateTableDTO;
import br.com.lucas.reserve_adim.domain.table.TableEntity;
import br.com.lucas.reserve_adim.domain.table.TableStatus;
import br.com.lucas.reserve_adim.repositories.TableRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tables")
public class TableController {

    @Autowired
    private TableRepository repository;

    @PostMapping
    public ResponseEntity createTable(@RequestBody @Valid CreateTableDTO data){
        if(repository.existsByName(data.name())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome de mesa Duplicado");

        }
        TableEntity newTableEntity = new TableEntity(data.name(), data.capacity(), TableStatus.AVAILABLE);
        System.out.println(newTableEntity.getCapacity());
        this.repository.save(newTableEntity);

        return ResponseEntity.ok().build();
    }



}
