package br.com.lucas.reserve_adim.repositories;

import br.com.lucas.reserve_adim.domain.table.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table,Long> {
    boolean existsByName(String name);
}
