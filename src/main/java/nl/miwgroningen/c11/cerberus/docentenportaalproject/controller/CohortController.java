package nl.miwgroningen.c11.cerberus.docentenportaalproject.controller;

import lombok.RequiredArgsConstructor;
import nl.miwgroningen.c11.cerberus.docentenportaalproject.model.Cohort;
import nl.miwgroningen.c11.cerberus.docentenportaalproject.model.Student;
import nl.miwgroningen.c11.cerberus.docentenportaalproject.repository.CohortRepository;
import nl.miwgroningen.c11.cerberus.docentenportaalproject.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//TODO Validation of dates (startDate before endDate)
//TODO Cohort overview template --> students to table (like teacher table)

@Controller
@RequiredArgsConstructor
public class CohortController {

    private final CohortRepository cohortRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/cohort/all")
    private String showAllCohorts(Model model) {

        model.addAttribute("allCohorts", cohortRepository.findAll());

        return "/cohort/cohortOverview";
    }

    @GetMapping("/cohort/new")
    private String showCreateCohortForm(Model model) {
        model.addAttribute("cohort", new Cohort());

        List<Student> allStudents = getListOfStudentsWithoutCohort();

        model.addAttribute("allStudents", allStudents);

        return "/cohort/createCohortForm";
    }

    @GetMapping("/cohort/edit/{cohortId}")
    private String showEditCohortForm(@PathVariable("cohortId") Long cohortId, Model model) {
        Optional<Cohort> optionalCohort = cohortRepository.findById(cohortId);

        if(optionalCohort.isEmpty()) {
            return "redirect:/cohort/all";
        }

        Cohort cohort = optionalCohort.get();

        //In the edit form, show a list of students that either have no cohort or are in the current cohort
        List<Student> allStudents = getListOfStudentsWithoutCohort();
        List<Student> studentsCurrentCohort = cohort.getStudents();
        allStudents.addAll(studentsCurrentCohort);

        model.addAttribute("cohort", cohort);
        model.addAttribute("allStudents", allStudents);

        return "/cohort/createCohortForm";
    }

    @PostMapping("/cohort/new")
    private String saveOrUpdateCohort(@ModelAttribute("cohort") Cohort cohortToBeSaved, BindingResult result) {

        if(!result.hasErrors()) {
            //Save cohort first
            cohortRepository.save(cohortToBeSaved);

            List<Student> students = cohortToBeSaved.getStudents();

            //Set all selected students to that cohort
            for (Student student : students) {
                student.setCohort(cohortToBeSaved);
                studentRepository.save(student);
            }
        }

        return "redirect:/cohort/all";
    }

    private List<Student> getListOfStudentsWithoutCohort() {
        List<Student> allStudents = studentRepository.findAll();
        List<Student> studentsToBeRemoved = new ArrayList<>();

        //Done this way to not edit list during iteration
        for (Student student : allStudents) {

            if(student.getCohort() != null) {
                studentsToBeRemoved.add(student);
            }

        }

        allStudents.removeAll(studentsToBeRemoved);

        return allStudents;
    }
}
