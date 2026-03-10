package com.p2p.server.p2p_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p2p.server.p2p_backend.model.Admin;
import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.AdminRepository;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AdminService {

    private final AdminRepository repository;

    public AdminService(AdminRepository repository) throws IOException {
        this.repository = repository;
    }

    public Admin getAdmin(String userId) throws Exception {
        return repository.getAdmin(userId);
    }
}
