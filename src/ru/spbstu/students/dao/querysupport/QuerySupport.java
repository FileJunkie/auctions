package ru.spbstu.students.dao.querysupport;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class QuerySupport extends SimpleJdbcDaoSupport{

    private static final Logger LOG = Logger.getLogger(QuerySupport.class);

    /**
     * {@link SQLBuilder} is used in creation of dynamic and complicated queries.
     * It represents a part of query, such as for example search criteria,
     * conjunction of criterias and etc. Actually it's just a function, that
     * modifies the query by appending something to it. Using it instead of raw
     * String allows to avoid excessive and unnecessary String copying in large
     * queries during building them from object model.
     */
    public static interface SQLBuilder {
        /**
         * Modifies query, implementations should add their representation to the
         * query and return it
         *
         * @param query current query
         * @return query
         *         modified query
         */
        Query buildSql(Query query);
    }


    /**
     * Query to Persistence storage
     * <p/>
     * <p>Represents abstract query to persistence storage
     * <p/>
     * <p>This class allows to create dynamic and static parametrized queries for concrete persistence technology written in its native language (like SQL, XQUERY, etc.),
     * then execute and retrieve query results hiding complexity of concrete persistence API.
     */
    protected class Query {
        private StringBuilder query;
        private List<Serializable> parameters = new ArrayList<Serializable>();

        /**
         * Creates new query
         *
         * @param query      native query (or part of query if it's shuold be dynamic)
         * @param parameters optional query parameters
         */
        public Query(String query, Serializable... parameters) {
            this.query = new StringBuilder(query);
            parameters(parameters);
        }

        /**
         * Appends part of dynamic query
         *
         * @param query      part of dynamic query
         * @param parameters optional parameters
         * @return a reference to this object
         */
        public Query append(CharSequence query, Serializable... parameters) {
            this.query.append(query);
            return parameters(parameters);
        }

        /**
         * Appends part of dynamic query
         *
         * @param query      part of dynamic query
         * @param parameters optional parameters
         * @return a reference to this object
         */
        public Query append(String query, Collection<? extends Serializable> parameters) {
            this.query.append(query);
            return parameters(parameters);
        }

        /**
         * Adds string to Query, if expression is true
         *
         * @param expression
         * @param query      String to add
         * @param parameters optional parameters
         * @return Query object
         */
        public Query condition(boolean expression, String query, Serializable... parameters) {
            return expression ? append(query, parameters) : this;
        }

        /**
         * Appends part of dynamic query
         *
         * @param builder    {@link SQLBuilder} representing part of sql query
         * @param parameters optional parameters
         * @return Query object
         */
        public Query append(SQLBuilder builder, Serializable... parameters) {
            return builder.buildSql(this).parameters(parameters);
        }

        /**
         * Appends part of dynamic query
         *
         * @param builder    {@link SQLBuilder} representing part of sql query
         * @param parameters optional parameters
         * @return Query object
         */
        public Query append(SQLBuilder builder, Collection<? extends Serializable> parameters) {
            return builder.buildSql(this).parameters(parameters);
        }

        /**
         * Appends query parameters
         *
         * @param parameters query parameters
         * @return a reference to this object
         */
        public Query parameters(Collection<? extends Serializable> parameters) {
            this.parameters.addAll(parameters);
            return this;
        }

        /**
         * Appends query parameters
         *
         * @param parameters query parameters
         * @return a reference to this object
         */
        public Query parameters(Serializable... parameters) {
            return parameters(Arrays.asList(parameters));
        }

        /**
         * Clears parameters
         *
         * @return a reference to this object
         */
        public Query clearParameters() {
            this.parameters.clear();
            return this;
        }

        /**
         * Returns query parameters
         *
         * @return query parameters
         */
        public List<Serializable> parameters() {
            return parameters;
        }

        /**
         * Executes this query
         */
        public void execute() {
            execute(new SimpleExecutor());
        }

        /**
         * Executes this query and allows simple query execution customization
         *
         * @param executor Provides simple query execution customization
         * @throws PersistenceException if query execution wasn't successful
         */
        public void execute(SimpleExecutor executor) throws PersistenceException {
            try {
                executor.execute(this);
            } finally {
                executor.close();
            }
        }

        /**
         * Retrieves query results as list of java objects
         *
         * @param fetcher Responsible for populating java objects from query result
         * @return List of java objects returned by this query
         */
        public <E> E fetch(Fetcher<E> fetcher) {
            try {
                if (fetcher.execute(this).hasNext()) {
                    return fetcher.fetch();
                } else {
                    return null;
                    //throw new PersistenceException("Query returns empty result", null);
                }
            } finally {
                fetcher.close();
            }
        }

        /**
         * Retrieves query results as list of java objects
         *
         * @param fetcher Responsible for populating java objects from query result
         * @return List of java objects returned by this query
         */
        public <E> List<E> list(Fetcher<E> fetcher) {
            return fill(new ArrayList<E>(), fetcher);
        }


        /**
         * Retrieves query results as java objects into custom collection
         *
         * @param collection Collection holding query results
         * @param fetcher    Responsible for populating java objects from query results
         * @return collection Same collection holding query results that is specified as methos parameter
         */
        public <E, C extends Collection<E>> C fill(C collection, Fetcher<E> fetcher) {
            try {

                fetcher.execute(this);
                while (fetcher.hasNext()) {
                    collection.add(fetcher.fetch());
                }
                return collection;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                fetcher.close();
            }
        }


        /**
         * Retrieves query results as list of java objects
         *
         * @param fetcher Responsible for populating java objects from query result
         * @return List of java objects returned by this query
         */
        public <E> E fillin(Fetcher<E> fetcher) {
            try {
                E result = null;
                fetcher.execute(this);
                while (fetcher.hasNext()) {
                    result = fetcher.fetch();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                fetcher.close();
            }
        }


        /**
         * This method is used to retrieve results in external collections or variables.
         * <p/>
         * <p>Sometimes doesn't necessary to fetch all results to one collection. Sometimes
         * necesasary to build different objects or to fill maps. This method can be used to
         * provide avaibility to fetch data and fill any external collections or variables.</p>
         *
         * @param fetcher fetcher which iterates by all result of query
         *                and provides fetching data to external storage
         * @return fetcher object defined in parameters
         */
        public <T extends SimpleFetcher> T fetch(T fetcher) {
            try {
                fetcher.execute(this);
                while (fetcher.hasNext()) {
                    fetcher.fetch();
                }
                return fetcher;
            } finally {
                fetcher.close();
            }
        }

        /**
         * Returns iterator to java objects representing query results
         * <p>This method should be used if this query returns big results (that usually consumes segnificant amount of memory) or returned objects should be connected to persistent storage
         * and provide access to InputStreams, CLOBS, and other I/O facilities, otherwise please use {@link #list(com.merckserono.persistence.PersistenceManager.Fetcher)} list} and
         * {@link #fill(java.util.Collection, com.merckserono.persistence.PersistenceManager.Fetcher)} fill} mathods
         *
         * @param fetcher Responsible for populating java objects from query results
         * @return iterator Iterator to java objects representing query results
         */
        public <E> EntityIterator<E> iterator(Fetcher<E> fetcher) {
            try {
                return new EntityIterator<E>(fetcher.execute(this));
            } catch (RuntimeException e) {
                fetcher.close();
                throw e;
            }
        }

        /**
         * Returns query result as single object
         *
         * @param finder Responsible for populating query result in single java object and allows to customize the case, when query returns no results
         * @return query result as single java object
         * @throws NoSuchObjectException when query result is expected but it's empty
         */
        public <E> E find(Finder<E> finder) throws NoSuchObjectException {
            try {
                return finder.execute(this).hasNext() ? finder.fetch() : finder.notFound();
            } finally {
                finder.close();
            }
        }

        /**
         * Allows to customize query execution
         *
         * @param executor responsible for overal query execution and results processing implementation, using native persistence API
         * @return query result
         * @throws PersistenceException if query execution wasn't successful
         */
        public <R, E extends AbstractExecutor<R>> R execute(E executor) throws PersistenceException {
            return executor.execute(this);
        }

        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder("SQL QUERY: ").append(query);
            if (!parameters.isEmpty()) {
                tmp.append(" PARAMETERS: ").append(parameters);
            }
            return tmp.toString();
        }

    }


    /**
     * Query execution implementation
     */
    protected interface AbstractExecutor<R> {

        /**
         * Implements query execution against concrete persistence storage uing its native API
         *
         * @param query Query
         * @return Query result
         * @throws PersistenceException if query execution wasn't successful
         */
        R execute(Query query) throws PersistenceException;
    }


    /**
     * Allows to execute queries agains JDBC compliant persistent storage
     *
     * @param <RET> query execution result
     * @param <RES> result of JDBC PreparedStatement execution
     */
    private abstract class JDBCExecutor<RET, RES> implements AbstractExecutor<RET> {

        /**
         * This class executes all queries using PreparedStatement and its subinterfaces
         * (JDBC Statement is weak as it's query cannot be parametrized and it's functionality is achievable with PreparedStatement)
         */
        private PreparedStatement ps;
        private Connection con;

        public RET execute(Query query) throws PersistenceException {
            long t1 = System.currentTimeMillis();
            try {
                con = getConnection();
                ps = initStatement(query.query.toString(), con);
                setParameters(query.parameters);
                return process(run(ps));
            } catch (SQLException e) {
                close();
                throw new PersistenceException("Error executing sql query", e, query);
            } finally {
                long t2 = System.currentTimeMillis();
                LOG.debug(query.toString() + " time=" + (t2-t1) + " milliseconds");
            }
        }

        /**
         * Returns JDBC Connection for executing query
         *
         * @return Connection
         * @throws java.sql.SQLException is database access error occurs
         */
        protected Connection getConnection() throws SQLException {
            return QuerySupport.this.getConnection();
        }

        /**
         * Creates concrete PreparedStatement that will handle query execution
         *
         * @param query SQL query
         * @param con   JDBC Connection
         * @return JDBC PreparedStatement prepared with specified query
         * @throws SQLException if query preparation wasn't successful
         */
        protected abstract PreparedStatement initStatement(String query, Connection con) throws SQLException;

        /**
         * Set query parameters in PreparedStatement
         *
         * @param params query parameters
         * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement;
         *                      if a database access error occurs or this method is called on a closed PreparedStatement
         */
        private void setParameters(List<Serializable> params) throws SQLException {
            for (int i = 1; i <= params.size(); i++) {
                Serializable param = params.get(i - 1);
                if (param == null) {
                    ps.setNull(i, Types.OTHER);
                } else if (param instanceof Integer) {
                    ps.setInt(i, (Integer) param);
                } else if (param instanceof String) {
                    ps.setString(i, (String) param);
                } else if (param instanceof Boolean) {
                    ps.setBoolean(i, (Boolean) param);
                } else if (param instanceof Float) {
                    ps.setFloat(i, (Float) param);
                } else if (param instanceof Long) {
                    ps.setLong(i, (Long) param);
                } else if (param instanceof Date) {
                    ps.setDate(i, new java.sql.Date(((Date) param).getTime()));
                } else if (param instanceof Short) {
                    ps.setShort(i, (Short) param);
                } else {
                    throw new RuntimeException("Illegal query parameter type found: " + param.getClass().getName());
                }
            }
        }

        /**
         * Executes query with parameters in database
         *
         * @param ps PreparedStatement with query and parameters fully set
         * @return PreparedStatement execution result
         * @throws SQLException if a database access error occurs; this method is called on a closed PreparedStatement or an argument is supplied to this method
         */
        protected abstract RES run(PreparedStatement ps) throws SQLException;

        /**
         * Analyzing PreparedStatement execution result, process it and wrap it as java objects (abstracting query result from JDBC API specific)
         *
         * @param result PreparedStatement execution result
         * @return query execution result
         * @throws SQLException if a database error occurs
         */
        protected abstract RET process(RES result) throws SQLException;

        /**
         * Releases this <code>JDBCExecutor</code> object's database
         * and involved JDBC resources
         * <p/>
         * <p/>
         * Calling the method <code>close</code> on a <code>JDBCExecutor</code>
         * object that is already closed has no effect.
         * <p/>
         */
        protected void close() {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("", new PersistenceException("Error closing JDBC PreparedStatement", e));
                }
                ps = null;
            }
            if (con != null) {
                releaseConnection(con);
                con = null;
            }
        }
    }

    /**
     * Executing SQL Query without returning any results
     * <p/>
     * <p>Allows to customize only JDBC PreparedStatement and JDBC Connection used for query execution
     */
    protected class SimpleExecutor extends JDBCExecutor<SimpleExecutor, Boolean> {
        @Override
        protected PreparedStatement initStatement(String query, Connection con) throws SQLException {
            return con.prepareStatement(query);
        }

        @Override
        protected final Boolean run(PreparedStatement ps) throws SQLException {
            return ps.execute();
        }

        @Override
        protected final SimpleExecutor process(Boolean result) throws SQLException {
            return this;
        }
    }

    /**
     * Fetcher is used to get data from database and to provide way to build results.
     * <p>
     * Fetcher has common methods to get several data - integer values, string values,
     * large objects, boolean flags, etc from database.
     * Abstract method fetch() defined to be implemented in superclasses and provides building
     * concrete data transfer objects.
     * </p>
     *
     * @param <RET> type of object to build
     */
    protected abstract class Fetcher<RET> extends JDBCExecutor<Fetcher<RET>, ResultSet> {

        private ResultSet rs;

        @Override
        protected PreparedStatement initStatement(String query, Connection con) throws SQLException {
            return con.prepareStatement(query);
        }

        @Override
        protected ResultSet run(PreparedStatement ps) throws SQLException {
            return ps.executeQuery();
        }

        @Override
        protected final Fetcher<RET> process(ResultSet result) {
            rs = result;
            return this;
        }

        protected abstract RET fetch();

        protected boolean hasNext() {
            try {
                return rs.next();
            } catch (SQLException e) {
                throw new PersistenceException("Error retrieving next row from JDBC ResultSet", e);
            }
        }

        protected final RET get(Fetcher<RET> fetcher) {
            try {
                fetcher.rs = rs;
                return fetcher.fetch();
            } finally {
                fetcher.rs = null;
            }
        }

        protected final int getInt(String fieldName) {
            try {
                return rs.getInt(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' int id from JDBC ResultSet", e);
            }
        }

        protected final long getLong(String fieldName) {
            try {
                return rs.getLong(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as long id from JDBC ResultSet", e);
            }
        }

        protected final String getString(String fieldName) {
            try {
                return rs.getString(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as String id from JDBC ResultSet", e);
            }
        }

        protected final boolean getBoolean(String fieldName) {
            try {
                return rs.getBoolean(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as boolean id from JDBC ResultSet", e);
            }
        }

        protected final Boolean getBooleanFromChar(String fieldName) {
            String str = null;
            try {
                str = rs.getString(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as String id from JDBC ResultSet", e);
            }
            return str == null ? null : ("1".equals(str.trim()));
        }

        protected final float getFloat(String fieldName) {
            try {
                return rs.getFloat(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as float id from JDBC ResultSet", e);
            }
        }

        protected final double getDouble(String fieldName) {
            try {
                return rs.getDouble(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as double id from JDBC ResultSet", e);
            }
        }

        protected final Date getDate(String fieldName) {
            try {
                return rs.getDate(fieldName);
            } catch (SQLException e) {
                throw new PersistenceException("Error getting '" + fieldName + "' as date id from JDBC ResultSet", e);
            }
        }

        /**
         * Builds string from clob data
         *
         * @param - clob data
         * @return - string with data
         * @throws SQLException when read exception occurs
         */
        protected final String getStringFromClob(String fieldName) throws PersistenceException {
            Clob clob = null;
            try {
                clob = rs.getClob(fieldName);
                if (clob != null) {
                    StringBuilder sbuf = new StringBuilder((int) clob.length());
                    Reader rdr;
                    rdr = clob.getCharacterStream();

                    char[] buf = new char[(int) clob.length()];
                    int len;
                    while ((len = rdr.read(buf)) != -1) {
                        sbuf.append(buf, 0, len);
                    }
                    return sbuf.toString();
                }
            } catch (IOException e) {
                throw new PersistenceException("IOException occurs while reading from clob id", e);
            } catch (SQLException sql) {
                throw new PersistenceException("cannot get character stream from clob", sql, clob);
            }
            return null;
        }


        @Override
        protected void close() {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("", new PersistenceException("Error closing JDBC ResultSet", e));
                }
                rs = null;
            }
            super.close();
        }
    }


    /**
     * This type of {@link Fetcher} is used to find single result by some key.
     * If result didn't found, the method notFound is called.
     * Good way is to throw {@link NoSuchObjectException} exception with query parameters
     * to tell that object was not found.
     *
     * @param <RES> Type of result object.
     */
    protected abstract class Finder<RES> extends Fetcher<RES> {
        protected abstract RES notFound() throws NoSuchObjectException;
    }

    /**
     * <p>
     * This fetcher is used to fetch all results of given query and provide
     * flexible way to fill external collections or maps.
     * </p>
     * <p>
     * This fetcher must be used when doesn't necessary to get one parameterized
     * collection with all results. If necessary to build collection with all results
     * please use {@link Fetcher}.
     * </p>
     */
    protected abstract class SimpleFetcher extends Fetcher<SimpleFetcher> {
        @Override
        protected SimpleFetcher fetch() {
            mapRow();
            return null;
        }
        abstract void mapRow();
    }


    /**
     * EntityIterator is used for fetching from data source a lot of big objects.
     * <p>
     * EntityIterator provides lazy getting objects, it helps to save memory and improves perfomance.
     * </p>
     *
     * @param <E> type of result objects
     */
    public static class EntityIterator<E> implements Iterator<E> {

        /**
         * Fetcher for iterator to provide building result objects
         */
        private Fetcher<E> fetcher;

        /**
         * Constructs new EntityIterator with given fetcher
         *
         * @param fetcher {@link Fetcher} object to build results
         */
        private EntityIterator(Fetcher<E> fetcher) {
            this.fetcher = fetcher;
        }

        /**
         * Returns <tt>true</tt> if the iteration has more elements
         *
         * @return <tt>true</tt> if the iterator has more elements.
         */
        public boolean hasNext() {
            try {
                if (fetcher.hasNext()) {
                    return true;
                } else {
                    close();
                    return false;
                }
            } catch (RuntimeException e) {
                close();
                throw e;
            }
        }

        /**
         * Returns next object
         *
         * @return next object
         */
        public E next() {
            try {
                return fetcher.fetch();
            } catch (RuntimeException e) {
                close();
                throw e;
            }
        }

        /**
         * Remove operation is not allowed and this method always throws <code>UnsupportedOperationException</code>
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        /**
         * Method closes all resources, associated with iterator
         */
        public void close() {
            if (fetcher != null) {
                fetcher.close();
                fetcher = null;
            }
        }
    }
    
    /**
     * Represents builder than does nothing with the query.
     */
    public static final SQLBuilder EMPTY_BUILDER = new SQLBuilder() {

        public Query buildSql(Query query) {
            return query;
        }

    };

    /**
     * Constructs binary operator builder. Method is used only inside class for
     * constructing "and" and "or" builders.
     *
     * @param rf           right child
     * @param lf           left child
     * @param operatorName operator name
     * @return constructed filter
     */
    private static SQLBuilder createBinarySQLBuilder(final SQLBuilder rf, final SQLBuilder lf, final String operatorName) {
        if (lf == EMPTY_BUILDER && rf == EMPTY_BUILDER) {
            return EMPTY_BUILDER;
        }

        if (lf == EMPTY_BUILDER) {
            return rf;
        }

        if (rf == EMPTY_BUILDER) {
            return lf;
        }

        return new SQLBuilder() {

            public Query buildSql(Query query) {
                query.append("(");
                lf.buildSql(query);
                query.append(operatorName);
                rf.buildSql(query);
                return query.append(")");
            }
        };

    }

    /**
     * Creates conjunction of two {@link SQLBuilder}s. Both SQLBuilders can be
     * EMPTY_BUILDER. If one is empty other returned. If both,
     * EMPTY_BUILDER returned.
     *
     * @param left  left expression
     * @param right right expression
     * @return constructed SQLBuilder
     */
    public static SQLBuilder and(SQLBuilder left, SQLBuilder right) {
        return createBinarySQLBuilder(left, right, " and ");
    }


    /**
     * Creates union of two {@link SQLBuilder}s. Both SQLBuilders can be
     * EMPTY_BUILDER. If one is empty other returned. If both,
     * EMPTY_BUILDER returned.
     *
     * @param left  left expression
     * @param right right expression
     * @return constructed SQLBuilder
     */
    public static SQLBuilder or(SQLBuilder left, SQLBuilder right) {
        return createBinarySQLBuilder(left, right, " or ");
    }

    /**
     * Creates sql IN expression: "field in (value1, value2, ...)"
     *
     * @param field    field name
     * @param elements elements in "in" expression, can be empty
     * @return SQL "IN" Expression, EMPTY_BUILDER if elements are empty
     */
    public static SQLBuilder in(final String field, final Collection<? extends Serializable> elements) {
        return elements.isEmpty()
                ?
                EMPTY_BUILDER
                :
                new SQLBuilder() {
                    
                    public Query buildSql(Query query) {
                        return buildInExpression(query.append(" ").append(field).append(" in "), elements);
                    }
                };
    }


    public static SQLBuilder in(final String field, final SQLBuilder builder) {
        if (builder == EMPTY_BUILDER) {
            return EMPTY_BUILDER;
        } else {
            return new SQLBuilder() {

                
                public Query buildSql(Query query) {
                    return query.append(" ").append(field).append(" in (").append(builder).append(")");
                }

            };
        }

    }

    /**
     * Creates sql "not in" expression.
     *
     * @see Query#in
     */
    public static SQLBuilder notIn(final String field, final Collection<? extends Serializable> elements) {
        return elements.isEmpty()
                ?
                EMPTY_BUILDER
                :
                new SQLBuilder() {
                    
                    public Query buildSql(Query query) {
                        return buildInExpression(query.append(" ").append(field).append(" NOT IN "), elements);
                    }
                };
    }


    /**
     * Wraps string into SQLBuilder
     *
     * @param string
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder builder(final String string, final Serializable... parameters) {
        return new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(string).parameters(Arrays.asList(parameters));
            }
        };
    }

    public static SQLBuilder subquery(final String mainquery, final SQLBuilder criteria) {
        return subquery(mainquery, criteria, EMPTY_BUILDER);
    }

    public static SQLBuilder subquery(final String mainquery, final SQLBuilder criteria, final SQLBuilder appendix) {
        if (criteria == EMPTY_BUILDER) {
            return EMPTY_BUILDER;
        }
        return new SQLBuilder() {

            
            public Query buildSql(Query query) {
                query.append(mainquery);
                query.append(" where ");
                query.append(criteria);
                query.append(appendix);
                return query;
            }

        };
    }

    public static SQLBuilder intersect(SQLBuilder lf, SQLBuilder rf) {
        return createBinarySQLBuilder(rf, lf, " intersect ");
    }

    public static SQLBuilder union(SQLBuilder lf, SQLBuilder rf) {
        return createBinarySQLBuilder(rf, lf, " union ");
    }

    /**
     * Creates builder for equals expression
     *
     * @param fieldName
     * @param value
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder eq(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(fieldName).append("=?", value);
            }
        };
    }

    /**
     * Creates builder for between expression
     *
     * @param fieldName
     * @param val1
     * @param val2
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder between(final String fieldName, final Serializable val1, final Serializable val2) {
        return (val1 == null || val2 == null) ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(fieldName).append(" between ? and ?", val1, val2);
            }
        };
    }

    /**
     * Creates builder for equal fields expression
     * (e.g. leftField = rightField, where both variables are names)
     *
     * @param leftField  name of left field
     * @param rightField name of right field
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder eqField(final String leftField, final String rightField) {
        boolean noField = (leftField == null) || (rightField == null);
        return noField ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(leftField).append(" = ").append(rightField);
            }
        };
    }

    /**
     * Creates builder for equals lower expression
     *
     * @param fieldName
     * @param value
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder lowerEq(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("lower(").append(fieldName).append(")=lower(?)", value);
            }
        };
    }

    /**
     * Creates builder for equals upper expression
     *
     * @param fieldName
     * @param value
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder upperEq(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("upper(").append(fieldName).append(")=upper(?)", value);
            }
        };
    }

    /**
     * Creates builder for start with expression
     *
     * @param fieldName
     * @param value
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder startWith(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(" start with ").append(lowerEq(fieldName, value));
            }
        };
    }

    /**
     * Creates builder for connect by prior expression
     *
     * @param nameChildId  - ID name for children in a hierarchy structure
     * @param nameParentId - ID name for parent in a hierarchy structure
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder connectByPrior(final String nameChildId, final String nameParentId) {
        return (nameChildId == null || nameParentId == null) ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(" connect by prior ").append(nameChildId).append("=").append(nameParentId);
            }
        };
    }

    /**
     * Creates SQLBuilder with a query string. This query is used to get ID
     * of all children for the demanded parent ID. The value of parent ID should be defined
     * when start with expression is being created.
     *
     * @param nameChildId    - name for children ID
     * @param tableName      - name of table, where search with hierarchy structure
     *                       should be provided
     * @param startWith      - SQLBuilder for startWith expression
     * @param connectByPrior - SQLBuilder for connectByPrior expression
     * @return {@link SQLBuilder} with a query
     */
    public static SQLBuilder createQueryWithHierarchy(final String nameChildId, final String tableName, final SQLBuilder startWith, final SQLBuilder connectByPrior) {
        boolean noCreate = (nameChildId == null) || (tableName == null);
        noCreate = noCreate || (startWith == EMPTY_BUILDER) || (connectByPrior == EMPTY_BUILDER);
        return noCreate ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                query = query.append(" select ").append(nameChildId).append(" from ").append(tableName);
                query = query.append(startWith).append(connectByPrior);
                return query;
            }
        };
    }

    /**
     * Creates builder for where statement
     *
     * @param criteria - SQLBuilder, contained all search conditions
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder where(final SQLBuilder criteria) {
        return criteria == EMPTY_BUILDER ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(" where ").append(criteria);
            }
        };
    }

    public static SQLBuilder likeLower(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("lower(").append(fieldName).append(") like lower (?)", value);
            }
        };
    }
    
    public static SQLBuilder likeUpper(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("upper(").append(fieldName).append(") like upper (?)", value);
            }
        };
    }

    public static SQLBuilder like(final String fieldName, final Serializable value) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(fieldName).append(" like ?", value);
            }
        };
    }

    /**
     * Adds "(object1, object2, ...)" to query, where object1, object2 are objects from entities collection
     *
     * @param entities {@link Collection} of objects
     * @return modified query
     */
    public static Query buildInExpression(Query builder, Collection<? extends Serializable> entities) {
        builder.append("(");
        for (int i = 0; i < entities.size(); i++) builder.append("?,");
        builder.query.setLength(builder.query.length() - 1);
        return builder.append(")", entities);
    }

    /**
     * Creates builder for group by expression
     *
     * @param fields - string with comma-separated fields for group by
     *               expression
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder groupBy(final String fields) {
        return fields == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append(" group by ").append(fields);
            }
        };
    }

    /**
     * Creates builder for order by expression
     *
     * @param field     - string with a name of field for order by
     *                  expression
     * @param direction - string with direction for sorting of results
     *                  (ASC or DESC)
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder orderBy(final String field, final String direction) {
        if (field == null) {
            return EMPTY_BUILDER;
        } else if (direction == null) {
            return new SQLBuilder() {
                
                public Query buildSql(Query query) {
                    return query.append(" order by ").append(field);
                }
            };
        } else {
            return new SQLBuilder() {
                
                public Query buildSql(Query query) {
                    return query.append(" order by ").append(field).append(" ")
                            .append(direction);
                }
            };
        }
    }

    /**
     * Creates builder attaching additional field and direction for sorting
     * to group by or order by expression
     *
     * @param field     - string with a name of field for order by
     *                  expression
     * @param direction - string with direction for sorting of results
     *                  (ASC or DESC)
     * @return {@link SQLBuilder}
     */
    public static SQLBuilder addField(final String field, final String direction) {
        if (field == null) {
            return EMPTY_BUILDER;
        } else if (direction == null) {
            return new SQLBuilder() {
                
                public Query buildSql(Query query) {
                    return query.append(", ").append(field);
                }
            };
        } else {
            return new SQLBuilder() {
                
                public Query buildSql(Query query) {
                    return query.append(", ").append(field).append(" ")
                            .append(direction);
                }
            };
        }
    }

    public static SQLBuilder not(final SQLBuilder builder) {
        return builder != EMPTY_BUILDER ? new SQLBuilder() {

            
            public Query buildSql(Query query) {
                return query.append("not ").append(builder);
            }

        } : EMPTY_BUILDER;
    }

    public static SQLBuilder notNull(final String fieldName) {
        return fieldName != null ? new SQLBuilder() {

            
            public Query buildSql(Query query) {
                return query.append("not ").append(fieldName).append(" is null ");
            }
        } : EMPTY_BUILDER;
    }
    
    public static SQLBuilder isNotNull(final String fieldName) {
        return fieldName != null ? new SQLBuilder() {

            
            public Query buildSql(Query query) {
                return query.append(fieldName).append(" is not null ");
            }
        } : EMPTY_BUILDER;
    }
    
    protected SQLBuilder disjunction(Collection<SQLBuilder> criterias) {
        Iterator<SQLBuilder> it = criterias.iterator();
        SQLBuilder disjunction = it.next();
        
        while(it.hasNext()) {
            disjunction = or(disjunction, it.next());
        }
        return disjunction;
    }

    protected SQLBuilder conjunction(Collection<SQLBuilder> criterias) {
        Iterator<SQLBuilder> it = criterias.iterator();
        SQLBuilder disjunction = it.next();
        
        while(it.hasNext()) {
            disjunction = and(disjunction, it.next());
        }
        return disjunction;
    }
    
    public static SQLBuilder catsearch(final String fieldName, final Serializable value, final String group) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("catsearch(" + fieldName + ",'" + value + "',null) > 0");
            }
        };
    }
    
    public static SQLBuilder instrUpper(final String fieldName, final Serializable value, final Integer start) {
        return value == null ? EMPTY_BUILDER : new SQLBuilder() {
            
            public Query buildSql(Query query) {
                return query.append("instr(upper(" + fieldName + "), upper('" + value + "')," + start + ") > 0");
            }
        };
    }
    
    protected class IntegerFetcher extends Fetcher<Integer> {

        private String fieldName;

        public IntegerFetcher(final String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        protected final Integer fetch() {
            return getInt(fieldName);
        }
    }
    
    protected class StringFetcher extends Fetcher<String> {
        
        private String stringFieldName;
        
        public StringFetcher(final String stringFieldName) {
            this.stringFieldName = stringFieldName;
        }
        
        @Override
        protected final String fetch() {
            return getString(stringFieldName);
        }
    }
}
