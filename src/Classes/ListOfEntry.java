package Classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOfEntry {
    @XmlElement
    private List<Entry> list = new ArrayList<>();
    public List<Entry> getList(){ return list; }
}