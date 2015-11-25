package edu.nure.db;

public class RequestPreparing {

    public static final String DB_NAME = "pmanager";
    public static String update(String table, String[] colNames, Object[] values, String whereCond){
        String req = "UPDATE `" +DB_NAME+ "`.`"+table.toUpperCase()+"` SET ";
        for (int i = 0; i < colNames.length; i++) {
            if(values[i] != null) {
                if(values[i] instanceof String) {
                    req += "`" + colNames[i] + "`= '" + values[i].toString().replace("\"","\\\"").trim()  + "', ";
                }else{
                    req += "`" + colNames[i] + "`= " + values[i].toString().trim() + ", ";
                }
            }else{
                req += "`" + colNames[i] + "`= NULL, ";
            }
        }
        req = req.substring(0, req.lastIndexOf(','));
        if(whereCond != null)
            req += " WHERE "+whereCond;
        return req +";";
    }

    public static String insert(String table, String[] colNames, Object[] values){
        String req = "INSERT INTO `"+DB_NAME+"`.`"+table.toUpperCase()+"`(";
        for(String v: colNames)
            req+="`"+v+"`, ";
        req = req.substring(0, req.length() - 2)+") Values(";
        for (int i = 0; i < colNames.length; i++) {
            if(values[i] != null) {
                if (values[i] instanceof String) {
                    req += "'" + values[i].toString().replace("\"","\\\"").trim() + "', ";
                }else{
                        req += values[i].toString().trim() + ", ";
                }
            }else{
                req +=  "NULL, ";
            }
        }
        req = req.substring(0, req.lastIndexOf(','));
        return req +");";
    }

    public static String select(String table, String[] colNames, String add){
        String req = "SELECT ";
        if(colNames.length == 1 && colNames[0].equals("*")) req += "*";
        else{
            for(String v: colNames) {
                req += "`" + v + "`, ";
            }
            req = req.substring(0, req.length() - 2);
        }
        req += " FROM `"+DB_NAME+"`."+table.toUpperCase();
        if(add != null)
            req += " "+add;
        return req +";";
    }

    public static String join(String table, String type, String onCond){
        return type +" JOIN `"+DB_NAME+"`."+table.toUpperCase()+" ON "+onCond+" ";
    }
}
