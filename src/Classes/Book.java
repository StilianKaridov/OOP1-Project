package Classes;

public class Book {
    private String startTime;   //start of the meet;
    private String endTime;     //end of the meet;
    private String name;        //person's name
    private String note;        //meet's note

    public Book(String startTime, String endTime, String name, String note) {
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
        return "Book{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", personToMeet='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
