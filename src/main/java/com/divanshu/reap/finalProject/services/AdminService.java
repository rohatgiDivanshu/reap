package com.divanshu.reap.finalProject.services;

import com.divanshu.reap.finalProject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;


}
