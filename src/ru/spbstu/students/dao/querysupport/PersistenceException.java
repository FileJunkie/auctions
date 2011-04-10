package ru.spbstu.students.dao.querysupport;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Represents persistent layer failure
 * <p/>
 * <p>Thrown when persistent layer encounter an error on application specific request execution</p>
 * <p>
 * This exception may be throws because of application error (like invalid query, query parameters, results retrieval),
 * or native persistent storage error (like if storage becomes unavailable or returns an internal error)<br/>
 * <p/>
 * In any case - all persistence-related errors returned as <code>Persistence exceptions</code> and must be considered as
 * unrecoverable serious faults.
 * </p>
 */
class PersistenceException extends RuntimeException {
    private static final long serialVersionUID = 6480743690163483788L;

    public PersistenceException(String message, SQLException cause, Object... detail) {
        super(createMessage(message, cause, detail), cause);
    }

    /**
     * @param message message
     * @param cause   cause {@link IOException}
     */
    public PersistenceException(String message, IOException cause) {
        super(message, cause);
    }

    /**
     * Creates detailed message about persistent subsystem error
     *
     * @param error  desribes an action to native persistent API that was fail
     * @param e      native persistent technology exteption as returned by API
     * @param detail action execution supplementary information
     * @return Brief message describing an error in a context of persistent technology and application speficic detail
     */
    static String createMessage(String error, SQLException e, Object... detail) {
        StringBuilder msg = new StringBuilder(error).append(": ").append(e.getMessage()).append(" (errorCode=").append(e.getErrorCode()).append(", ");
        for (Object d : detail) msg.append(d).append(", ");
        msg.setLength(msg.length() - 2);
        msg.append(')');
        return msg.toString();
    }
}