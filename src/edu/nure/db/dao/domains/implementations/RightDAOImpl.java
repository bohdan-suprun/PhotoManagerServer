package edu.nure.db.dao.domains.implementations;

import edu.nure.db.dao.exceptions.SelectException;
import edu.nure.db.entity.Right;
import edu.nure.db.entity.primarykey.PrimaryKey;

import java.sql.Connection;
import java.util.List;

/**
 * Created by bod on 11.11.15.
 */
public class RightDAOImpl extends GenericDAOImpl<Right> {

    public RightDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Right select(PrimaryKey key) throws SelectException {
        return getAll(Right.class, "WHERE `"+key.getName()+"` = "+key.getValue()).iterator().next();
    }

    @Override
    public List<Right> selectAll() throws SelectException {
        return getAll(Right.class, null);
    }
}
