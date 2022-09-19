package cass.myapp;

public class SubjectsTableView {
    private int id;
    private String subject;
    private String code;
    private String semester;
    private int duration;

    public SubjectsTableView(int id, String subject, String code, String semester, int duration) {
        this.id = id;
        this.subject = subject;
        this.code = code;
        this.semester = semester;
        this.duration = duration;
    }

    public void setId(int id) {this.id = id; }
    public int getId() {return id;}

    public void setSubject(String subject) {this.subject = subject;}
    public String getSubject() {return subject;}

    public void setcode(String code) {this.code = code;}
    public String getCode() {return code;}

    public void setSemester(String semester) {this.semester = semester;}
    public String getSemester() {return semester;}

    public void setDuration(int duration) {this.duration = duration;}
    public int getDuration() {return duration;}



}
