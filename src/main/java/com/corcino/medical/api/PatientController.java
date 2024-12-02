package com.corcino.medical.api;

import com.corcino.medical.entity.Patient;
import com.corcino.medical.json.PatientRequest;
import com.corcino.medical.json.PatientResponse;
import com.corcino.medical.json.PatientResponseList;
import com.corcino.medical.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping()
    public ResponseEntity<PatientResponse> create(@RequestBody @Valid PatientRequest patientRequest) {
        Patient patient = patientService.createPatient(patientRequest);
        return ResponseEntity.ok(new PatientResponse(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseList>> list(@PageableDefault(sort = "patientId", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponse> update(@RequestBody @Valid PatientRequest patientRequest, @PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.updatePatient(patientRequest, patientId));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> delete(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }

}
