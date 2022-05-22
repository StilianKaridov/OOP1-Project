package Classes;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends XmlAdapter<ListOfEntry, Map<Date, List<Meet>>> {
    @Override
    public Map<Date, List<Meet>> unmarshal(ListOfEntry loe) {
        Map<Date, List<Meet>> map = new LinkedHashMap<>();
        for (Entry entry : loe.getList()) {
            map.put(entry.getKey(), entry.getList());
        }
        return map;
    }

    @Override
    public ListOfEntry marshal(Map<Date, List<Meet>> map) {
        ListOfEntry loe = new ListOfEntry();
        for (Map.Entry<Date, List<Meet>> mapEntry : map.entrySet()) {
            Entry entry = new Entry();
            entry.setKey(mapEntry.getKey());
            entry.getList().addAll(mapEntry.getValue());
            loe.getList().add(entry);
        }
        return loe;
    }
}