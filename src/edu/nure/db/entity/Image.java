package edu.nure.db.entity;

import edu.nure.db.dao.exceptions.DBException;
import edu.nure.db.entity.constraints.ValidationException;
import edu.nure.db.entity.primarykey.IntegerPrimaryKey;
import edu.nure.db.entity.primarykey.PrimaryKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bod on 17.09.15.
 */
public class Image extends AbstractEntity {

    public static final int ID_NOT_SET = -1;
    private static final long serialVersionUID = 5943412076492939426L;
    private int id = ID_NOT_SET;
    private String hash;
    private byte[] image;
    private int album;
    private Date createdIn;

    public Image() {

    }

    public Image(String hash, int id, byte[] image, int album, Date createdIn) {
        setHash(hash);
        setId(id);
        setImage(image);
        setAlbum(album);
        setCreatedIn(createdIn);
    }

    @Override
    public void parseResultSet(ResultSet rs) throws DBException, ValidationException {
        try {
            setHash(rs.getString("Hash"));
            setId(rs.getInt("Id"));
            setImage(rs.getBytes("Image"));
            setAlbum(rs.getInt("Album"));
            setCreatedIn(rs.getDate("CreatedIn"));
        } catch (SQLException ex) {
            throw new DBException(ex.getMessage());
        }
    }

    public String getCreatedIn() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(createdIn);
    }

    private void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public int getAlbum() {
        return album;
    }

    private void setAlbum(int album) {
        this.album = album;
    }

    public byte[] getImage() {
        return image;
    }

    private void setImage(byte[] image) {
        this.image = image;
    }

    public String getHash() {
        return hash;
    }

    private void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

//    @Override
//    public String toXML() {
//        return "<image id=\"" + id + "\" hash=\"" + hash + "\" album=\""
//                + album + "\" createdIn=\"" + getCreatedIn() + "\"/>";
//    }

    @Override
    public String toQuery() {
        return "id=" + id +
                "&hash=" + hash +
                "&album=" + album +
                "&createdIn=" + getCreatedIn();
    }

    public String[] getFields() {
        return new String[]{"Hash", "Album", "CreatedIn", "Image"};
    }

    @Override
    public Object[] getValues() {
        return new Object[]{
                getHash(),
                getAlbum(),
                getCreatedIn(),
                getImage()
        };
    }

    @Override
    public String entityName() {
        return "IMAGE";
    }

    @Override
    public PrimaryKey getPrimaryKey() {
        return new IntegerPrimaryKey(getId());
    }
}
