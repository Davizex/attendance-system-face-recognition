package br.com.rekome.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rekome.entities.Attendance;
import br.com.rekome.entities.Group;
import br.com.rekome.entities.User;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUserAndGroupAndAttendanceDateTimeBetween(User user, Group group, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
