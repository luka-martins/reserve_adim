package br.com.lucas.reserve_adim.domain.table;

import br.com.lucas.reserve_adim.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@jakarta.persistence.Table(name = "tables")
@Entity(name = "tables")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int capacity;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public TableEntity(String name, int capacity, TableStatus status){
        this.name = name;
        this.capacity = capacity;
        this.status = status;

    }



}
