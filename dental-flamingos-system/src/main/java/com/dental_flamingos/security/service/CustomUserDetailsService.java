package com.dental_flamingos.security.service;

import com.dental_flamingos.model.Dentista;
import com.dental_flamingos.repository.DentistaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DentistaRepository dentistaRepository;

    public CustomUserDetailsService(DentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Dentista dentista = dentistaRepository.findByUsuario(usuario);

        if (dentista == null) {
           throw new UsernameNotFoundException("Usuario no encontrado: "+usuario);
        }

        return User.builder()
                .username(dentista.getUsuario())
                .password(dentista.getPassword())
                .authorities("USER")
                .build();
    }
}
