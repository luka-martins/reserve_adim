package br.com.lucas.reserve_adim.services;

import br.com.lucas.reserve_adim.domain.table.CreateTableDTO;
import br.com.lucas.reserve_adim.domain.table.TableEntity;
import br.com.lucas.reserve_adim.domain.table.TableStatus;
import br.com.lucas.reserve_adim.domain.table.UpdateTableDTO;
import br.com.lucas.reserve_adim.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private TableRepository repository;

    public boolean createTable(CreateTableDTO data){
        if(repository.existsByName(data.name())) return false;

        TableEntity newTableEntity = new TableEntity(data.name(), data.capacity(), TableStatus.AVAILABLE);
        this.repository.save(newTableEntity);
        return true;
    }

    public List<TableEntity> listAllTables(){
        return repository.findAll();
    }

    public ResponseEntity updateTable(UpdateTableDTO data, long id){
        Optional<TableEntity> optionalTable = repository.findById(id);
        if(optionalTable.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa não encontrada") ;

        TableEntity table = optionalTable.get();

        if (data.name() != null && !data.name().equals(table.getName())) {
            if (repository.existsByName(data.name())) {
                return ResponseEntity.badRequest().body("Já existe uma mesa com esse nome.");
            }
            table.setName(data.name());
        }

        if (data.status() != null) {
            table.setStatus(data.status());
        }

        if(data.capacity() != null){
            if (data.capacity() <= 0) {
                return ResponseEntity.badRequest().body("Capacidade deve ser maior que zero.");
            }

            table.setCapacity(data.capacity());

        }
        return ResponseEntity.ok("Mesa Alterada com sucesso");
    }

    public ResponseEntity deleteTable(long id){
        Optional<TableEntity> table = repository.findById(id);
        if(table.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa não encontrada") ;

        repository.deleteById(id);

        return ResponseEntity.ok("Mesa deletada com sucesso");

    }
}

