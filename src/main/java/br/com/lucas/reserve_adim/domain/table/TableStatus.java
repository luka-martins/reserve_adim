package br.com.lucas.reserve_adim.domain.table;

public enum TableStatus {

    AVAILABLE("available"),
    RESERVED("reserved"),
    INACTIVE("inactive");

    private String status;

    TableStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
