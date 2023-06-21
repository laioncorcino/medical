package com.corcino.medical.service;

import com.corcino.medical.entity.Doctor;
import com.corcino.medical.error.exception.BadRequestException;
import com.corcino.medical.json.DoctorRequest;
import com.corcino.medical.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        return saveDoctor(new Doctor(doctorRequest));
    }

    private Doctor saveDoctor(Doctor doctor) {
        try {
            log.info("Saving doctor from crm {}", doctor.getCrm());
            return doctorRepository.save(doctor);
        }
        catch (DataIntegrityViolationException e) {
            log.error("Duplicated key --- {}", e.getMessage());
            throw new BadRequestException("Duplicated key");
        }
        catch (Exception e) {
            log.error("There was error saving doctor");
            throw new BadRequestException("Unknown error");
        }
    }

}
