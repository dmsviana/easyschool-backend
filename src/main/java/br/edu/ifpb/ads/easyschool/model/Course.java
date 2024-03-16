package br.edu.ifpb.ads.easyschool.model;

import br.edu.ifpb.ads.easyschool.exception.MaximumCapacityExceededException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_courses")
public class Course {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 50)
    private String name;


    @Column(nullable = false)
    private String description;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "course_day_week", joinColumns = @JoinColumn(name = "course_id"))
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysOfWeek;

    @Column(nullable = false)
    private int maxCapacity;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Student> students;

    @PrePersist
    public void prePersist() {
        if (students == null) {
            students = List.of();
        }
    }


    public void addStudent(Student student) {
        if (students.size() < maxCapacity) {
            students.add(student);
        } else {
            throw new MaximumCapacityExceededException("Capacidade máxima de alunos atingida.");
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }
}
