package com.stevenkolamkuzhiyil.SpringCrud.security;

import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.UserNotFoundException;
import com.stevenkolamkuzhiyil.SpringCrud.model.User;
import com.stevenkolamkuzhiyil.SpringCrud.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public UserDetailsServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((new UserNotFoundException(email)).getMessage()));

        return new UserDetailsImpl(user);
    }
}
