package lv.lu.df.combopt.lectsched.domain;

public class Student {
    private String name;

    public Student() {}

    public Student(String name) {
        setName(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
