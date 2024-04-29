package com.dataflow.apidomrock.services;

import com.dataflow.apidomrock.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

    @Service
    public class AuthorizationService implements UserDetailsService {
        @Autowired
        UsuarioRepository usuarioRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            UserDetails userDetails = usuarioRepository.findByEmail(email);
            return userDetails;
        }
    }

