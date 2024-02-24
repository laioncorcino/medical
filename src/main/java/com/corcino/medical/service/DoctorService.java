package com.corcino.medical.service;

import com.corcino.medical.entity.Address;
import com.corcino.medical.entity.Doctor;
import com.corcino.medical.error.exception.BadRequestException;
import com.corcino.medical.error.exception.NotFoundException;
import com.corcino.medical.json.DoctorRequest;
import com.corcino.medical.json.DoctorResponse;
import com.corcino.medical.json.DoctorResponseList;
import com.corcino.medical.repository.DoctorRepository;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AddressService addressService;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, AddressService addressService) {
        this.doctorRepository = doctorRepository;
        this.addressService = addressService;
    }

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        return saveDoctor(new Doctor(doctorRequest));
    }

    public DoctorResponse updateDoctor(DoctorRequest doctorRequest, Long doctorId) {
        Doctor doctor = getById(doctorId);

        if (StringUtils.isNotBlank(doctorRequest.getName())) {
            doctor.setName(doctorRequest.getName());
        }

        if (StringUtils.isNotBlank(doctorRequest.getEmail())) {
            doctor.setEmail(doctorRequest.getEmail());
        }

        if (StringUtils.isNotBlank(doctorRequest.getCrm())) {
            doctor.setCrm(doctorRequest.getCrm());
        }

        if (StringUtils.isNotBlank(doctorRequest.getPhone())) {
            doctor.setPhone(doctorRequest.getPhone());
        }

        Address address = addressService.updateAddress(doctorRequest);

        if (address != null) {
            doctor.setAddress(address);
        }

        log.info("Updating doctor " + doctor.getDoctorId());

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

    public void deleteDoctor(Long doctorId) {
        getById(doctorId);
        log.info("Deleting doctor of id {}", doctorId);
        doctorRepository.deleteById(doctorId);
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

    private Doctor getById(Long doctorId) {
        log.info("Finding doctor of id {}", doctorId);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        return doctor.orElseThrow(() -> {
            log.error("Doctor of id {} not found", doctorId);
            return new NotFoundException("Doctor not found");
        });
    }

}
