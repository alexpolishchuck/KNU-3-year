package comparator;

import utils.Phone;

import java.util.Comparator;

public class AlphabetComparator implements Comparator<Phone> {
    @Override
    public int compare(Phone phone, Phone t1) {
        return phone.getSurname().compareToIgnoreCase(t1.getSurname());
    }
}
