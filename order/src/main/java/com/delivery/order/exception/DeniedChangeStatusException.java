package com.delivery.order.exception;

public class DeniedChangeStatusException extends RuntimeException {

    public DeniedChangeStatusException(String message) {
        super(message);
    }
}
