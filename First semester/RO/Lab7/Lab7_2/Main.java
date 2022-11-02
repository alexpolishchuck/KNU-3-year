package Lab7_2;

import java.sql.SQLException;

public class Main {
    public static void main(String[]args)
    {
        try {
            DataBaseManager db = new DataBaseManager();
            db.addElement("check2","two");
            db.addElement("check2","two");
   //         db.deleteElement("check2","two");
            db.editElement("check","one","WHAT", DataBaseManager.parameter.CATEGORY);
            db.showTable();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
