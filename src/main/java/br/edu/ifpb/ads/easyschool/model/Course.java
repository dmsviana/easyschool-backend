package br.edu.ifpb.ads.easyschool.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import br.edu.ifpb.ads.easyschool.exception.MaximumCapacityExceededException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> students;


    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int maxCapacity;

    @PrePersist
    public void prePersist(){
        this.endTime = startTime.plusHours(2);
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
