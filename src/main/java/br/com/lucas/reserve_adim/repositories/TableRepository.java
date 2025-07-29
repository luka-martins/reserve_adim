package br.com.lucas.reserve_adim.repositories;

import br.com.lucas.reserve_adim.domain.table.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity,Long> {
    boolean existsByName(String name);

}
