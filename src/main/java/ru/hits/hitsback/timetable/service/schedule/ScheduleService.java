package ru.hits.hitsback.timetable.service.schedule;

import ru.hits.hitsback.timetable.model.dto.group.GroupIdDto;
import ru.hits.hitsback.timetable.model.dto.schedule.DayScheduleDto;
import ru.hits.hitsback.timetable.model.dto.schedule.LessonTimeDto;
import ru.hits.hitsback.timetable.model.dto.schedule.TimeIntervalDto;
import ru.hits.hitsback.timetable.model.dto.studyroom.StudyRoomIdDto;
import ru.hits.hitsback.timetable.model.dto.teacher.TeacherIdDto;
import ru.hits.hitsback.timetable.model.entity.Account;

import java.util.List;

public interface ScheduleService {
    List<DayScheduleDto> fetchSchedule(TimeIntervalDto timeIntervalDto, Account account);

    List<DayScheduleDto> fetchGroupSchedule(TimeIntervalDto timeIntervalDto, GroupIdDto groupIdDto);

    List<DayScheduleDto> fetchStudyRoomSchedule(TimeIntervalDto timeIntervalDto, StudyRoomIdDto studyRoomIdDto);

    List<DayScheduleDto> fetchTeacherSchedule(TimeIntervalDto timeIntervalDto, TeacherIdDto teacherIdDto);

    List<DayScheduleDto> fetchScheduleWithLessonOptions(TimeIntervalDto timeIntervalDto, String teacherId, String studyRoomId, List<String> groupIds);

    List<LessonTimeDto> fetchLessonTimes();
}
