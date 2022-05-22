package Classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {
    @XmlElement(name = "date")
    private Date key;
    @XmlElement(name = "meet")
    private List<Meet> list = new ArrayList<>();

    public Date getKey() {
        return key;
    }

    public void setKey(Date value) {
        key = value;
    }

    public List<Meet> getList() {
        return list;
    }
}
