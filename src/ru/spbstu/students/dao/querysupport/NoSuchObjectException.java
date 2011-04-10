
package ru.spbstu.students.dao.querysupport;

/**
 * Thrown when requested object is not found
 */
public class NoSuchObjectException extends Exception {

    private static final long serialVersionUID = -4610019743542947106L;

    /**
     * Constructs exception with message
     *
     * @param missingObjectDescription error message describing missing object detail based on available data from user request
     */
    public NoSuchObjectException(final String missingObjectDescription) {
        super(missingObjectDescription);
    }
}
