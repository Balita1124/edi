package com.balita.edi.utils;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(Long entityId) {
        super("Entity: " + entityId + " not found.");
    }
}
