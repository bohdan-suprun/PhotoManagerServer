package edu.nure.db.dao.domains.interfaces;

import edu.nure.db.dao.exceptions.DBException;
import edu.nure.db.dao.exceptions.SelectException;
import edu.nure.db.entity.User;

import java.util.List;

/**
 * Created by bod on 11.11.15.
 */
public interface UserDAO extends GenericDAO<User> {

    User login(String login, String pass) throws SelectException;

    List<User> getByName(String likeName) throws SelectException;

    List<User> getAllNames(String likeName) throws SelectException;

    List<User> getByName(String likeName, boolean withHiRights) throws SelectException;

    List<User> getByPhone(String likePhone) throws SelectException;

    boolean setPassword(int id, String pass) throws DBException;

    User authenticate(String code) throws DBException;

    public String insertCode(User ent) throws DBException;

}
