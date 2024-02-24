package com.corcino.medical.api;

import com.corcino.medical.entity.Doctor;
import com.corcino.medical.json.DoctorRequest;
import com.corcino.medical.json.DoctorResponse;
import com.corcino.medical.json.DoctorResponseList;
import com.corcino.medical.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid DoctorRequest doctorRequest, UriComponentsBuilder uriBuilder) {
        Doctor doctor = doctorService.createDoctor(doctorRequest);
        URI uri = uriBuilder.path("/api/v1/doctors/{doctorId}").buildAndExpand(doctor.getDoctorId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseList>> list(@PageableDefault(sort = "doctorId", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> delete(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

}

