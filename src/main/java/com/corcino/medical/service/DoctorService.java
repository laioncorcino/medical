package com.corcino.medical.service;

import com.corcino.medical.entity.Address;
import com.corcino.medical.entity.Doctor;
import com.corcino.medical.error.exception.BadRequestException;
import com.corcino.medical.error.exception.NotFoundException;
import com.corcino.medical.json.DoctorRequest;
import com.corcino.medical.json.DoctorResponse;
import com.corcino.medical.json.DoctorResponseList;
import com.corcino.medical.repository.DoctorRepository;
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

    public DoctorResponse updateDoctor(DoctorRequest doctorRequest, Long doctorId) {
        Doctor doctor = getById(doctorId);
        BeanUtils.copyProperties(doctorRequest, doctor, getNullPropertyNames(doctorRequest));

        if (Objects.nonNull(doctorRequest.getAddress())) {
            Address address = doctor.getAddress();
            BeanUtils.copyProperties(doctorRequest.getAddress(), address, getNullPropertyNames(doctorRequest.getAddress()));
            doctor.setAddress(address);
        }

        log.info("Updating doctor {}", doctor.getDoctorId());

        Doctor savedDoctor = saveDoctor(doctor);
        return new DoctorResponse(savedDoctor);
    }

    @Transactional(readOnly = true)
    public Page<DoctorResponseList> getAllDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll(pageable);
        return doctors.map(DoctorResponseList::new);
    }

    public DoctorResponse getDoctorById(Long doctorId) {
        return new DoctorResponse(getById(doctorId));
    }

    private Doctor getById(Long doctorId) {
        log.info("Finding doctor of id {}", doctorId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        return doctor.orElseThrow(() -> {
            log.error("Doctor of id {} not found", doctorId);
            return new NotFoundException("Doctor not found");
        });
    }

    public void deleteDoctor(Long doctorId) {
        getById(doctorId);
        log.info("Deleting doctor of id {}", doctorId);
        doctorRepository.deleteById(doctorId);
    }

}
