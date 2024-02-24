package com.corcino.medical.service;

import com.corcino.medical.entity.Address;
import com.corcino.medical.json.AddressRequest;
import com.corcino.medical.json.DoctorRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public Address updateAddress(DoctorRequest doctorRequest) {
        AddressRequest addressRequest = doctorRequest.getAddress();
        Address address = new Address();

        if (StringUtils.isNotBlank(addressRequest.getAddressLine())) {
            address.setAddressLine(address.getAddressLine());
        }

        if (StringUtils.isNotBlank(addressRequest.getCep())) {
            address.setCep(addressRequest.getCep());
        }

        if (addressRequest.getNumber() != null) {
            address.setNumber(addressRequest.getNumber());
        }

        if (StringUtils.isNotBlank(addressRequest.getComplement())) {
            address.setComplement(address.getComplement());
        }

        if (StringUtils.isNotBlank(addressRequest.getDistrict())) {
            address.setDistrict(addressRequest.getDistrict());
        }

        if (StringUtils.isNotBlank(addressRequest.getCity())) {
            address.setCity(address.getCity());
        }

        if (StringUtils.isNotBlank(addressRequest.getState())) {
            address.setState(addressRequest.getState());
        }

        return address;
    }

}
