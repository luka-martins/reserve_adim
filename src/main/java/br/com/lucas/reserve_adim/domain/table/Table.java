package br.com.lucas.reserve_adim.domain.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@jakarta.persistence.Table(name = "tables")
@Entity(name = "tables")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private TableStatus status;

    public Table(String name, int capacity, TableStatus status){
        this.name = name;
        this.capacity = capacity;
        this.status = status;

    }



}
