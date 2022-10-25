package lv.lu.df.combopt.lectsched.domain;

public class Teacher {
    private String name;

    public Teacher() {}

    public Teacher(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
