package com.fatec.scc.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fatec.scc.model.Usuario;
import com.fatec.scc.model.UsuarioRepository;
@Service
public class UserDetailsServiceI implements UserDetailsService{
    Logger logger = LogManager.getLogger(UserDetailsServiceI.class);
    @Autowired
    private UsuarioRepository repository;
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = repository.findByLogin(login);
		if(usuario == null) {
			logger.info(">>>>>>>>>>> usuario invalido");
			throw new UsernameNotFoundException("Usuario não encontrado.");
		}
		logger.info(">>>>>>>>>>> UserDetailsServiceI - usuario valido login = " + usuario.getLogin());
		logger.info(">>>>>>>>>>> UserDetailsServiceI - usuario valido login = " + usuario.getSenha());
		return usuario;
	}

}
