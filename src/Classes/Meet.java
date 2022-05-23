package Classes;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "startTime", "endTime", "name", "note"})
public class Meet implements Comparable<Meet>, Serializable {
    @XmlElement(name = "startTime")
    private String startTime;
    @XmlElement(name = "endTime")
    private String endTime;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "note")
    private String note;

    public Meet() {
    }

    public Meet(String startTime, String endTime, String name, String note) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.note = note;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Meet: from " + startTime + " to " + endTime + " with " + name + " - " + note;
    }

    @Override
    public int compareTo(Meet o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
