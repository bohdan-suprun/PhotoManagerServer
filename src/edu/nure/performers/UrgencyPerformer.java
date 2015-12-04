package edu.nure.performers;

import edu.nure.Manager;
import edu.nure.db.dao.AbstractDAOFactory;
import edu.nure.db.dao.domains.interfaces.GenericDAO;
import edu.nure.db.dao.exceptions.DBException;
import edu.nure.db.dao.exceptions.InsertException;
import edu.nure.db.dao.exceptions.SelectException;
import edu.nure.db.entity.Urgency;
import edu.nure.db.entity.constraints.ValidationException;
import edu.nure.db.entity.primarykey.IntegerPrimaryKey;
import edu.nure.performers.exceptions.PerformException;
import edu.nure.util.ResponseBuilder;

import java.io.IOException;
import java.util.Objects;

public class UrgencyPerformer extends AbstractPerformer {

    private GenericDAO<Urgency> dao;

    public UrgencyPerformer(ResponseBuilder b) throws DBException {
        super(b);
        dao = AbstractDAOFactory.getDAO(AbstractDAOFactory.MYSQL).getUrgencyDAO();
    }

    public void perform() throws PerformException, IOException, DBException {
        int action = builder.getAction();
        switch (action) {
            case Action.GET_URGENCY:
                doGet();
                break;
            case Action.INSERT_URGENCY:
                doInsert();
                break;
            case Action.UPDATE_URGENCY:
                doUpdate();
                break;
            case Action.DELETE_URGENCY:
                doDelete();
                break;
            default:
                builder.setStatus(ResponseBuilder.STATUS_PARAM_ERROR);
        }
    }

    @Override
    protected void doGet() throws PerformException, IOException {
        try {
            for (Urgency urgency : dao.selectAll()) {
                builder.add(urgency);
            }
            builder.setStatus(ResponseBuilder.STATUS_OK);
        } catch (SelectException ex) {
            Manager.setLog(ex);
            throw new PerformException("Ошибка во время работы с базой данных");
        }
    }

    @Override
    protected void doInsert() throws PerformException, IOException {
        try {
            Urgency urgency = new Urgency(builder);
            urgency = dao.insert(urgency);
            if (urgency != null) {
                builder.add(urgency);
                builder.setStatus(ResponseBuilder.STATUS_OK);
            } else {
                builder.setStatus(ResponseBuilder.STATUS_ERROR_WRITE);
                builder.setText("Неудалось добавить срок");
            }
        } catch (InsertException ex) {
            Manager.setLog(ex);
            builder.setStatus(ResponseBuilder.STATUS_ERROR_WRITE);
            builder.setText(ex.getMessage());
        } catch (DBException e) {
            Manager.setLog(e);
            String msg = e.getMessage();
            if (msg.contains("Duplicate")) {
                throw new PerformException("Такая запись уже существует");
            } else {
                throw new PerformException("Ошибка обработки запроса");
            }
        } catch (ValidationException e) {
            throw new PerformException("Ошибка формата данных");
        }
    }

    @Override
    protected void doUpdate() throws PerformException, IOException {
        try {
            int oldTerm = Integer.valueOf(Objects.requireNonNull(builder.getParameter("oldTerm")));
            Urgency urgency = new Urgency(builder);
            if (dao.update(urgency, new IntegerPrimaryKey("Term", oldTerm))) {
                builder.setStatus(ResponseBuilder.STATUS_OK);
            } else {
                builder.setStatus(ResponseBuilder.STATUS_ERROR_WRITE);
                builder.setText("Неудалось обновить срок");
            }
        } catch (InsertException ex) {
            Manager.setLog(ex);
            builder.setStatus(ResponseBuilder.STATUS_ERROR_WRITE);
            builder.setText(ex.getMessage());
        } catch (DBException e) {
            Manager.setLog(e);
            throw new PerformException("Ошибка обработки запроса");
        } catch (ValidationException e) {
            throw new PerformException("Ошибка формата данных");
        }
    }

    @Override
    protected void doDelete() throws PerformException, IOException {
        try {
            Urgency urgency = new Urgency(builder);
            if (dao.delete(urgency)) {
                builder.setStatus(ResponseBuilder.STATUS_OK);
            } else {
                builder.setStatus(ResponseBuilder.STATUS_ERROR_WRITE);
                builder.setText("Неудалось удалить срок");
            }
        } catch (DBException e) {
            Manager.setLog(e);
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key"))
                throw new PerformException("Невозможно удалить запись: запись используется в заказе");
            else
                throw new PerformException("Ошибка при удалении записи");
        } catch (ValidationException e) {
            throw new PerformException("Ошибка формата данных");
        }

    }
}
