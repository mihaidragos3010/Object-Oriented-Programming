package Iterator;

import DataBase.Stream;

public interface Iterator {

    boolean hasNext();
    Stream getNext();
    void reset();
}
