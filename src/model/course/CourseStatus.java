/*
 * Course Approval Status Enum
 */
package model.course;

/**
 * Represents the approval status of a course
 * 
 * @author moaz
 */
public enum CourseStatus {
    PENDING("Pending Review"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String displayName;

    CourseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Get CourseStatus from string
     */
    public static CourseStatus fromString(String status) {
        if (status == null) {
            return PENDING;
        }

        for (CourseStatus cs : CourseStatus.values()) {
            if (cs.name().equalsIgnoreCase(status) ||
                    cs.displayName.equalsIgnoreCase(status)) {
                return cs;
            }
        }
        return PENDING;
    }
}