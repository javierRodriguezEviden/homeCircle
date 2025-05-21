package com.app.homeCircle.Entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CasaRepository extends JpaRepository <Casa, Integer> {

        List<Casa> findByUsuarioId(Long id);

        List<Casa> findAll();
}
