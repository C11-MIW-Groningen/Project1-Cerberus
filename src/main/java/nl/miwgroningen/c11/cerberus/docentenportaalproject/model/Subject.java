package nl.miwgroningen.c11.cerberus.docentenportaalproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of a programme, consisting of a number of learning units.
 *
 * @author Marianne Kooistra, Mohammed Almameri, Joost Schreuder
 */

@Entity
@Getter @Setter
public class Subject {

    @Id
    @GeneratedValue
    private Long subjectId;

    private String subjectName;

    private int durationWeeks;

    @ManyToMany
    private List<Teacher> teachers = new ArrayList<>();

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
}
