package br.com.rekome.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.entities.Attendance;
import br.com.rekome.entities.Group;
import br.com.rekome.entities.User;
import br.com.rekome.interfaces.CloudProviderService;
import br.com.rekome.operations.AttendanceOperation;
import br.com.rekome.repository.AttendanceRepository;

@Service
public class AttendanceService {
		
	private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceService.class);

	private final CloudProviderService cloudProviderService;

	private final AttendanceRepository attendanceRepository;
	
	private final UserService userService;
	
	private final GroupsServices groupsServices;

	public AttendanceService(CloudProviderService cloudProviderService, AttendanceRepository attendanceRepository,
			UserService userService, GroupsServices groupsServices) {
		super();
		this.cloudProviderService = cloudProviderService;
		this.attendanceRepository = attendanceRepository;
		this.userService = userService;
		this.groupsServices = groupsServices;
	}

	public Attendance take(MultipartFile image, AttendanceOperation operation) throws Exception {

		LOGGER.info("Start Attendance Validations for user: {} in group: {}", operation.getUserUuid(), operation.getGroupUuid());
		
		new AttendanceTakeValidation(operation.getAttencadeDateTime()).execute();

		Group group = groupsServices.findByUUID(operation.getGroupUuid());

		User user = this.userService.getUserUuidIfInGroup(operation.getUserUuid(), group)
				.orElseThrow(() -> new RuntimeException(
						"User " + operation.getUserUuid() + " not in group " + operation.getGroupUuid()));

		this.userHasAttendance(user, group, operation.getAttencadeDateTime());

		cloudProviderService.takeAttendance(user, image);
		
		var attendanceDateTime = new Date();
		
		LOGGER.info("Attendance valid. user: {} is present in day: {} ", user.getUuid(), attendanceDateTime);
		
	    Attendance attendance = new Attendance(user, group, attendanceDateTime, true, false);
	    
		return this.attendanceRepository.save(attendance);
	}
	
	private Boolean userHasAttendance(User user, Group group, LocalDate attendanceDay) {
		LocalDateTime startOfDay = attendanceDay.atStartOfDay();

		LocalDateTime endOfDay = attendanceDay.atTime(LocalTime.MAX);

		return attendanceRepository.findByUserAndGroupAndAttendanceDateTimeBetween(user, group, startOfDay, endOfDay)
				.isPresent();
	}

	public void justify() throws Exception{
		throw new UnsupportedOperationException("Attendance Justify not implemented");
	}
	
}
