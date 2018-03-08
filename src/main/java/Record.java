public class Record {
    private long id;
    private int startTime;
    private int endTime;
    private String note;
    private String company;
    private String username;

    public Record(long id, int startTime, int endTime, String note, String company, String username) {
        this.id = id;
        this.startTime = startTime;

        this.endTime = endTime;
        this.note = note;
        this.company = company;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
