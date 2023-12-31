package nl.miwgroningen.c11.cerberus.docentenportaalproject.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * A way for a student to practice the with the material of a subject.
 *
 * @author Marianne Kooistra, Mohammed Almameri, Joost Schreuder
 */

@Entity
@SuperBuilder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue
    private Long assignmentId;

    private String assignmentName;

    @ManyToOne
    private Subject subject;
}
