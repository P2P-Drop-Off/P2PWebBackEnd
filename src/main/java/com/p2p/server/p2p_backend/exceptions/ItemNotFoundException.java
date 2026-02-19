package com.p2p.server.p2p_backend.exceptions;

public class ItemNotFoundException
    extends Exception {
        public ItemNotFoundException(String errorMessage) {
            super(errorMessage);
        }
}
