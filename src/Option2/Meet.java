package Option2;

public class Meet {
    private String startTime;
    private String endTime;
    private String name;
    private String note;

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

    public boolean validateTime(){
        //Implement logic here
        return true;
    }

    @Override
    public String toString() {
        return "Meet: from " + startTime + " to " + endTime + " with " + name + " - " + note;
    }
}