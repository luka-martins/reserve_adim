package br.com.lucas.reserve_adim.controllers;

import br.com.lucas.reserve_adim.domain.table.CreateTableDTO;
import br.com.lucas.reserve_adim.domain.table.TableEntity;
import br.com.lucas.reserve_adim.domain.table.UpdateTableDTO;
import br.com.lucas.reserve_adim.services.TableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tables")
public class TableController {



    @Autowired
    private TableService service;

    @PostMapping
    public ResponseEntity createTable(@RequestBody @Valid CreateTableDTO data){

        boolean result = service.createTable(data);

        if(result){
            return ResponseEntity.ok("Mesa criada com sucesso");
        }else{
            return ResponseEntity.badRequest().body("Erro - mesa com nome duplicado");
        }

    }

    @GetMapping
    public ResponseEntity listAllTables(){

        List<TableEntity> tableEntities  = service.listAllTables();

        if(tableEntities.isEmpty()) return ResponseEntity.badRequest().body("Erro - nenhuma mesa criada");

        return ResponseEntity.ok(tableEntities);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateTable(@PathVariable long id, @RequestBody UpdateTableDTO data){
        return service.updateTable(data,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTable(@PathVariable long id){
        return service.deleteTable(id);
    }



}
