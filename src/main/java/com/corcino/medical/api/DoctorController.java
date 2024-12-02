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
    public ResponseEntity<DoctorResponse> create(@RequestBody @Valid DoctorRequest doctorRequest) {
        Doctor doctor = doctorService.createDoctor(doctorRequest);
        return ResponseEntity.ok(new DoctorResponse(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseList>> list(@PageableDefault(sort = "doctorId", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse> update(@RequestBody DoctorRequest doctorRequest, @PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.updateDoctor(doctorRequest, doctorId));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> delete(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }

}

