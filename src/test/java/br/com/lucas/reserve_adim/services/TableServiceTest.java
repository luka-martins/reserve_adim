package br.com.lucas.reserve_adim.services;

import br.com.lucas.reserve_adim.domain.table.CreateTableDTO;
import br.com.lucas.reserve_adim.domain.table.TableEntity;
import br.com.lucas.reserve_adim.domain.table.TableStatus;
import br.com.lucas.reserve_adim.domain.table.UpdateTableDTO;
import br.com.lucas.reserve_adim.repositories.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @InjectMocks
    private TableService service;

    @Mock
    private TableRepository repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTable_Success() {
        CreateTableDTO dto = new CreateTableDTO("Mesa 1", 4);
        when(repository.existsByName("Mesa 1")).thenReturn(false);

        boolean result = service.createTable(dto);

        assertTrue(result);
        verify(repository, times(1)).save(any(TableEntity.class));
    }

    @Test
    public void testCreateTable_DuplicateName() {
        CreateTableDTO dto = new CreateTableDTO("Mesa 1", 4);
        when(repository.existsByName("Mesa 1")).thenReturn(true);

        boolean result = service.createTable(dto);

        assertFalse(result);
        verify(repository, never()).save(any());
    }

    @Test
    public void testListAllTables() {
        List<TableEntity> mockList = List.of(new TableEntity("Mesa A", 2, TableStatus.AVAILABLE));
        when(repository.findAll()).thenReturn(mockList);

        List<TableEntity> result = service.listAllTables();

        assertEquals(1, result.size());
        assertEquals("Mesa A", result.get(0).getName());
    }

    // updateTable
    @Test
    public void testUpdateTable_Success() {
        long id = 1;
        TableEntity existing = new TableEntity("Mesa Antiga", 4, TableStatus.AVAILABLE);
        existing.setId(id);

        UpdateTableDTO dto = new UpdateTableDTO("Mesa Nova", 6, TableStatus.RESERVED);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByName("Mesa Nova")).thenReturn(false);

        ResponseEntity result = service.updateTable(dto, id);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Mesa Alterada com sucesso", result.getBody());
        assertEquals("Mesa Nova", existing.getName());
        assertEquals(TableStatus.RESERVED, existing.getStatus());
        assertEquals(6, existing.getCapacity());
    }

    @Test
    public void testUpdateTable_MesaNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity result = service.updateTable(new UpdateTableDTO("Nova", 4, TableStatus.RESERVED), 99L);

        assertEquals(404, result.getStatusCodeValue());
        assertEquals("Mesa não encontrada", result.getBody());
    }

    @Test
    public void testUpdateTable_NomeDuplicado() {
        long id = 1;
        TableEntity existing = new TableEntity("Mesa Antiga", 4, TableStatus.AVAILABLE);
        existing.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByName("Mesa Nova")).thenReturn(true);

        UpdateTableDTO dto = new UpdateTableDTO("Mesa Nova", null, null);
        ResponseEntity result = service.updateTable(dto, id);

        assertEquals(400, result.getStatusCodeValue());
        assertEquals("Já existe uma mesa com esse nome.", result.getBody());
    }

    @Test
    public void testUpdateTable_CapacidadeInvalida() {
        long id = 1;
        TableEntity existing = new TableEntity("Mesa Antiga", 4, TableStatus.AVAILABLE);
        existing.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        UpdateTableDTO dto = new UpdateTableDTO(null, -1, TableStatus.AVAILABLE);
        ResponseEntity result = service.updateTable(dto, id);

        assertEquals(400, result.getStatusCodeValue());
        assertEquals("Capacidade deve ser maior que zero.", result.getBody());
    }

    // deleteTable
    @Test
    public void testDeleteTable_Sucesso() {
        long id = 1;
        when(repository.findById(id)).thenReturn(Optional.of(new TableEntity("Mesa X", 2, TableStatus.AVAILABLE)));

        ResponseEntity result = service.deleteTable(id);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Mesa deletada com sucesso", result.getBody());
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTable_NaoEncontrada() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        ResponseEntity result = service.deleteTable(10L);

        assertEquals(404, result.getStatusCodeValue());
        assertEquals("Mesa não encontrada", result.getBody());
        verify(repository, never()).deleteById(anyLong());
    }


}
