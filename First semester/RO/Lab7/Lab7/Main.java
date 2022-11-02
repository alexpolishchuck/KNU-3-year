package Lab7;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataBaseManager db = new DataBaseManager();
        db.addObject("check","le pain");
        db.deleteObject("veg","potato");
        db.editObject("fruit","apple", DataBaseManager.PARAMETER.CATEGORY,"veg");
        ArrayList<vegetable> vegetables = db.readFile();

    }
}