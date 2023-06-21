package com.corcino.medical.api;

import com.corcino.medical.entity.Doctor;
import com.corcino.medical.json.DoctorRequest;
import com.corcino.medical.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid DoctorRequest doctorRequest, UriComponentsBuilder uriBuilder) throws UnsupportedEncodingException {
        Doctor doctor = doctorService.createDoctor(doctorRequest);
        URI uri = uriBuilder.path("/api/v1/doctors/{doctorId}").buildAndExpand(doctor.getDoctorId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
































