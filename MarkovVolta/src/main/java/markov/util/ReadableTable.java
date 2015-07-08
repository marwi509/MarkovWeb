package markov.util;

import java.util.Iterator;

public interface ReadableTable<Element> {

    Element get(Element theElement);
    Iterator<Element> iterator();
    boolean contains(Element element);

}
