package ru.hits.hitsback.timetable.template.model.entity;

import jakarta.persistence.Id;

public class SubjectEntity {
    @Id
    private Long id;
    private String title;

    public SubjectEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
