package org.example.entity;

public enum ScheduleStatus {
    SCHEDULED("scheduled"),
    ONGOING("ongoing"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private final String value;

    ScheduleStatus(String value) {
        this.value = value;
    }

    public static ScheduleStatus from(String value) {
        for (ScheduleStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}

