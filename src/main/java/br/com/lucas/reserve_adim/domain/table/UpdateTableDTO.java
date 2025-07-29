package br.com.lucas.reserve_adim.domain.table;

public record UpdateTableDTO(
        String name,
        Integer capacity,
        TableStatus status) {
}
