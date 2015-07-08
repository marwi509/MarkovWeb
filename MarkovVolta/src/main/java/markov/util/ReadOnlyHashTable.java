package markov.util;

import java.util.Iterator;

public class ReadOnlyHashTable<Element> implements ReadableTable<Element> {

    private final ReadableTable<Element> table;

    public ReadOnlyHashTable(ReadableTable<Element> table) {
        this.table = table;
    }

    @Override
    public Element get(Element theElement) {
        return table.get(theElement);
    }

    @Override
    public Iterator<Element> iterator() {
        return table.iterator();
    }

    @Override
    public boolean contains(Element element) {
        return table.contains(element);
    }
}
