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

    @Override
    public String toString() {
        return value; // ✅ 이제 toString()이 소문자 값 반환!
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

