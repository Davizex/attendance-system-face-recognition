package br.com.rekome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
