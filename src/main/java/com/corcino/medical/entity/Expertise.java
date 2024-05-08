package com.corcino.medical.entity;

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
        throw new IllegalArgumentException(String.format("Invalid expertise: %s", expertiseName));
    }

}
