package br.com.rekome.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rekome.operations.AttendanceOperation;
import br.com.rekome.services.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	private final AttendanceService attendanceService;

	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> attendance(
			@RequestParam("file") MultipartFile image,
			@RequestPart("user") @Validated AttendanceOperation operation) throws Exception {
		this.attendanceService.take(image, operation);
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<?> justify() throws Exception{
		this.attendanceService.justify();
		return ResponseEntity.ok().build();
	}
}
