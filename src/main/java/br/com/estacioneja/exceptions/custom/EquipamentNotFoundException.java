package br.com.estacioneja.exceptions.custom;

public class EquipamentNotFoundException extends RuntimeException {
    public EquipamentNotFoundException() {
        super("Equipamento não encontrado");
    }
    
    public EquipamentNotFoundException(String message) {
        super(message);
    }
    
}
