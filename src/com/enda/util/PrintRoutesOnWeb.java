

package com.enda.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PrintRoutesOnWeb<K, V> {
    private Map<K, V> map;

    public PrintRoutesOnWeb(Map<K, V> map) {
        this.map = map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<K, V> entry = iter.next();
            sb.append(entry.getKey());
            sb.append(" This route has been recorded by ");
            sb.append(entry.getValue()+ " times.");

            if (iter.hasNext()) {
                sb.append("<br>");
            }
        }
        return sb.toString();

    }
}
