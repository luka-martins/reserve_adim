package br.com.lucas.reserve_adim.domain.reservation;

import br.com.lucas.reserve_adim.domain.table.TableEntity;
import br.com.lucas.reserve_adim.domain.user.AppUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "reservations")
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private TableEntity table;

    @Column(name = "reservation_datetime", nullable = false)
    private LocalDateTime reservationDatetime;

    private String status;
}
