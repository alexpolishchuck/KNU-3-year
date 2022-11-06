package FileManager;

import Comparators.ConcreteComparator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class FileManagerTest {

    @BeforeAll
    static void init()
    {
        assumed = new ArrayList<>();
        assumed.add(new BankDeposit(
                "name",
                "country",
                "type",
                "depositor",
                "2",
                "amount",
                "profitability",
                "timeconstraints"
        ));
        assumed.add(new BankDeposit(
                "name2",
                "country2",
                "type2",
                "depositor2",
                "1",
                "amount2",
                "profitability2",
                "timeconstraints2"
        ));
        fm = new FileManager();
    }

    @org.junit.jupiter.api.Test
    void parseFileDOM() {
        ArrayList<BankDeposit> actual = fm.parseFileDOM();
        assertEquals(assumed.toString(),actual.toString());

    }

    @org.junit.jupiter.api.Test
    void parseFileStAX() {
        ArrayList<BankDeposit> actual = fm.parseFileStAX();
        assertEquals(assumed.toString(),actual.toString());
    }

    @org.junit.jupiter.api.Test
    void parseFileSAX() {
        ArrayList<BankDeposit> actual = fm.parseFileSAX();
        assertEquals(assumed.toString(),actual.toString());
    }
    @Test
    void comparatorTest()
    {
        ArrayList<BankDeposit> actual = fm.parseFileDOM();
        assertTrue(assumed.toString().compareTo(actual.toString())==0);
        ConcreteComparator cc = new ConcreteComparator();
        ArrayList<BankDeposit> assumedSorted = new ArrayList<>();
        assumedSorted.add(actual.get(1));
        assumedSorted.add(actual.get(0));
        actual.sort(cc);
        assertEquals(assumedSorted.toString(),actual.toString());
    }

  static private ArrayList<BankDeposit> assumed;
    static private FileManager fm;
}