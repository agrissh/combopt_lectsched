package lv.lu.df.combopt.lectsched.domain;

public class Room {
    private String code;
    private Integer maxNumberOfStudents;

    public Room() {}

    public Room(String code, Integer maxNumberOfStudents) {
        setCode(code);
        setMaxNumberOfStudents(maxNumberOfStudents);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxNumberOfStudents() {
        return maxNumberOfStudents;
    }

    public void setMaxNumberOfStudents(Integer maxNumberOfStudents) {
        this.maxNumberOfStudents = maxNumberOfStudents;
    }
}
