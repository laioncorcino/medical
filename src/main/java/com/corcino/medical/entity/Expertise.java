package com.corcino.medical.entity;

import com.corcino.medical.error.exception.BadRequestException;

public enum Expertise {

    CARDIOLOGIA,
    DERMATOLOGIA,
    GASTROENTEROLOGIA,
    GINECOLOGIA,
    ORTOPEDIA,
    NEUROLOGIA,
    UROLOGIA,
    PEDIATRIA,
    PSICOLOGIA;

    public static Expertise confirmExpertise(String expertiseName) {
        for (Expertise expertise : Expertise.values()) {
            if (expertiseName.equals(expertise.toString())) {
                return Expertise.valueOf(expertiseName);
            }
        }
        throw new BadRequestException(String.format("Invalid expertise: %s", expertiseName));
    }

}
