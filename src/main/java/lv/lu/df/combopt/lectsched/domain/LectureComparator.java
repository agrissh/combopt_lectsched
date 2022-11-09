package lv.lu.df.combopt.lectsched.domain;

import java.util.Comparator;

public class LectureComparator implements Comparator<Lecture> {
    @Override
    public int compare(Lecture o1, Lecture o2) {
        return o1.getRegistered().size() - o2.getRegistered().size();
    }
}
