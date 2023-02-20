package ru.hits.hitsback.timetable.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "timeSlot")
@Getter @Setter
public class TimeSlot {
    @Id
    private UUID id;
    private LocalTime date;
    private DayOfWeek dayOfWeek;
    @OneToMany
    private Set<Lesson> lessons;
    @ManyToOne
    private LessonTime lessonTime;
    public TimeSlot() {
    }

}
