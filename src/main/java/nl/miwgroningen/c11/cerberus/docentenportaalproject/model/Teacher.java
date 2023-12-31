package nl.miwgroningen.c11.cerberus.docentenportaalproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A person teaching a subject.
 *
 * @author Marianne Kooistra, Mohammed Almameri, Joost Schreuder
 */

@Entity
@Getter @Setter
public class Teacher extends User implements Comparable<Teacher> {

    @ManyToMany(mappedBy="teachers")
    private List<Subject> subjects = new ArrayList<>();

    @Override
    public int compareTo(Teacher otherTeacher) {
        return fullName.compareTo(otherTeacher.fullName);
    }
}
