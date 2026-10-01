package com.clinica.citas.disponibilidad.repository;

import com.clinica.citas.disponibilidad.domain.HorarioDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<HorarioDoctor, Long> {
    List<HorarioDoctor> findByDoctorId(Long doctorId);
}
