package edu.nure.db.dao.domains.implementations;

import edu.nure.db.dao.domains.interfaces.StockDAO;
import edu.nure.db.dao.exceptions.SelectException;
import edu.nure.db.entity.Stock;
import edu.nure.db.entity.primarykey.PrimaryKey;

import java.sql.Connection;
import java.util.List;

/**
 * Created by bod on 11.11.15.
 */
public class StockDAOImpl extends GenericDAOImpl<Stock> implements StockDAO {

    public StockDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<Stock> getStock(int orderId) throws SelectException {
        return getAll(Stock.class,
                "WHERE `Id_order` = " + orderId
                );
    }

    @Override
    public Stock select(PrimaryKey key) throws SelectException {
        return getAll(Stock.class, "WHERE `"+key.getName()+"` = "+key.getValue()).iterator().next();
    }
}
