package org.opu.platform.api;

import lombok.RequiredArgsConstructor;
import org.opu.platform.model.Student;
import org.opu.platform.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }

    @GetMapping("/student/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new-student";
    }

    @PostMapping("/student/new")
    public String createStudent(@ModelAttribute Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-student";
        }
        studentRepository.save(student);
        return "redirect:/";
    }

    @GetMapping("/student/edit/{id}")
    public String editStudentForm(@PathVariable("id") String id, Model model) {
        final Student student = studentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("student", student);
        return "edit-student";
    }

    @GetMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        studentRepository.deleteById(id);
        return "redirect:/";
    }
}
