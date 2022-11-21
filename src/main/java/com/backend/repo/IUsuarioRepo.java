package com.backend.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Usuario;

//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer>{
	Usuario findByEmail(String email);
	
	@Transactional
	void deleteByEmail(String email);

	Usuario findByUsername(String userName);
}
