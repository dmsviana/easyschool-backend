package br.edu.ifpb.ads.easyschool.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User {

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_lesson", 
        joinColumns = @JoinColumn(name = "teacher_id"), 
        inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    @JsonManagedReference
    @JsonIgnore
    private List<Lesson> lessons = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (lessons == null) {
            lessons = List.of();
        }
    }


    public void addLessons(Lesson lesson) {
        lessons.add(lesson);
        lesson.getTeachers().add(this);
    }

    public void removeLessons(Lesson lesson) {
        lessons.remove(lesson);
        lesson.getTeachers().remove(this);
    }
    
}
