package markov.util;

import java.util.Iterator;

public interface WritableTable<Element> extends ReadableTable<Element> {

	Element insert(Element theElement);

}
