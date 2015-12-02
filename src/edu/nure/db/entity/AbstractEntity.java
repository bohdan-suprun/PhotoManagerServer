package edu.nure.db.entity;

import edu.nure.Manager;
import edu.nure.util.ByteUtils;

import java.io.*;

/**
 * Created by bod on 02.12.15.
 */
public abstract class AbstractEntity implements Transmittable {
    @Override
    public String toXML() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream bout = new ObjectOutputStream(out)) {

            String tagName = getClass().getSimpleName().toLowerCase();
            bout.writeObject(this);
            return "<" + tagName + " code=\"" + ByteUtils.toHex(out.toByteArray()) + "\"/>";
        } catch (IOException ex) {
            Manager.setLog(ex);
            return "";
        }
    }

    public static <T> T fromXml(String code, Class<T> tClass) throws Exception {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(ByteUtils.fromHex(code));
             ObjectInputStream in = new ObjectInputStream(inputStream);) {
            Object ob = in.readObject();
            return tClass.cast(ob);
        } catch (IOException | ClassNotFoundException ex) {
            throw new Exception("Undefined class");
        }
    }

    public static Transmittable fromXml(String code) throws Exception {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(ByteUtils.fromHex(code));
             ObjectInputStream in = new ObjectInputStream(inputStream);) {
            Object ob = in.readObject();
            return (Transmittable) ob;
        } catch (IOException | ClassNotFoundException ex) {
            throw new Exception("Undefined class");
        }
    }
}
