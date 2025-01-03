package com.corcino.medical.service;

import com.corcino.medical.entity.Address;
import com.corcino.medical.entity.Patient;
import com.corcino.medical.error.exception.BadRequestException;
import com.corcino.medical.error.exception.NotFoundException;
import com.corcino.medical.json.PatientRequest;
import com.corcino.medical.json.PatientResponse;
import com.corcino.medical.json.PatientResponseList;
import com.corcino.medical.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.corcino.medical.util.UtilProperties.getNullPropertyNames;

@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(PatientRequest patientRequest) {
        return savePatient(new Patient(patientRequest));
    }

    private Patient savePatient(Patient patient) {
        try {
            log.info("Saving patient with CPF {}", patient.getCpf());
            return patientRepository.save(patient);
        } catch (DataIntegrityViolationException e) {
            log.error("Duplicated key --- {}", e.getMessage());
            throw new BadRequestException("Duplicated key");
        } catch (Exception e) {
            log.error("There was an error saving patient");
            throw new BadRequestException("Unknown error");
        }
    }

    public PatientResponse updatePatient(PatientRequest patientRequest, Long patientId) {
        Patient patient = getById(patientId);
        BeanUtils.copyProperties(patientRequest, patient, getNullPropertyNames(patientRequest));

        if (Objects.nonNull(patientRequest.getAddress())) {
            Address address = patient.getAddress();
            BeanUtils.copyProperties(patientRequest.getAddress(), address, getNullPropertyNames(patientRequest.getAddress()));
            patient.setAddress(address);
        }

        log.info("Updating patient {}", patient.getPatientId());

        Patient savedPatient = savePatient(patient);
        return new PatientResponse(savedPatient);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseList> getAllPatients(Pageable pageable) {
        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.map(PatientResponseList::new);
    }

    public PatientResponse getPatientById(Long patientId) {
        return new PatientResponse(getById(patientId));
    }

    private Patient getById(Long patientId) {
        log.info("Finding patient with ID {}", patientId);
        Optional<Patient> patient = patientRepository.findById(patientId);

        return patient.orElseThrow(() -> {
            log.error("Patient with ID {} not found", patientId);
            return new NotFoundException("Patient not found");
        });
    }

    public void deletePatient(Long patientId) {
        getById(patientId);
        log.info("Deleting patient with ID {}", patientId);
        patientRepository.deleteById(patientId);
    }

}
