package ru.hits.hitsback.timetable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hits.hitsback.timetable.model.dto.group.GroupIdDto;
import ru.hits.hitsback.timetable.model.dto.schedule.DayScheduleDto;
import ru.hits.hitsback.timetable.model.dto.schedule.LessonTimeDto;
import ru.hits.hitsback.timetable.model.dto.schedule.TimeIntervalDto;
import ru.hits.hitsback.timetable.model.dto.studyroom.StudyRoomIdDto;
import ru.hits.hitsback.timetable.model.dto.teacher.TeacherIdDto;
import ru.hits.hitsback.timetable.model.entity.Account;
import ru.hits.hitsback.timetable.service.authorisation.AuthorisationService;
import ru.hits.hitsback.timetable.service.schedule.ScheduleService;

import java.time.LocalDate;
import java.util.List;

import static ru.hits.hitsback.timetable.configuration.UrlConstant.BASE_URL;
import static ru.hits.hitsback.timetable.configuration.UrlConstant.SCHEDULE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = BASE_URL + SCHEDULE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final AuthorisationService authorisationService;

    @Operation(summary = "Получить расписание пользователя", description = "Для зарегистрированного пользователя автоматически вернётся расписание его группы/преподавателя", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<DayScheduleDto>> fetchSchedule(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        Account account = authorisationService.getUser();
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto(startDate, endDate);
        return ResponseEntity.ok(scheduleService.fetchSchedule(timeIntervalDto, account));
    }

    @Operation(summary = "Получить расписание группы", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping(value = "group/{id}")
    public ResponseEntity<List<DayScheduleDto>> fetchGroupSchedule(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PathVariable String id
    ) {
        GroupIdDto groupIdDto = new GroupIdDto(id);
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto(startDate, endDate);
        return ResponseEntity.ok(scheduleService.fetchGroupSchedule(timeIntervalDto, groupIdDto));
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping(value = "study-room/{id}")
    public ResponseEntity<List<DayScheduleDto>> fetchStudyRoomSchedule(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PathVariable String id
    ) {
        StudyRoomIdDto studyRoomIdDto = new StudyRoomIdDto(id);
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto(startDate, endDate);
        return ResponseEntity.ok(scheduleService.fetchStudyRoomSchedule(timeIntervalDto, studyRoomIdDto));
    }

    @Operation(summary = "Получить расписание преподавателя", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping(value = "teacher/{id}")
    public ResponseEntity<List<DayScheduleDto>> fetchTeacherSchedule(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PathVariable String id
    ) {
        TeacherIdDto teacherIdDto = new TeacherIdDto(id);
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto(startDate, endDate);
        return ResponseEntity.ok(scheduleService.fetchTeacherSchedule(timeIntervalDto, teacherIdDto));
    }

    @Operation(summary = "Получить объединение расписаний по преподавателям, группам и аудиториям", description = "Этот запрос доступен только составителям расписания для того, чтобы видеть наложения пар по преподавателям, группам и аудиториям одновременно", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "401", content = @Content),
            @ApiResponse(responseCode = "403", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping(value = "staff")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<DayScheduleDto>> fetchScheduleWithLessonOptions(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) String studyRoomId,
            @RequestParam(required = false) List<String> groupIds
    ) {
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto(startDate, endDate);
        return ResponseEntity.ok(scheduleService.fetchScheduleWithLessonOptions(timeIntervalDto, teacherId, studyRoomId, groupIds));
    }

    @Operation(summary = "Получить время каждой пары", description = "У каждой пары есть своё время. Например, первая пара начинается в 8:45 и заканчивается в 10:20", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content),
    })
    @GetMapping(value = "lesson-time")
    public ResponseEntity<List<LessonTimeDto>> fetchLessonTimes() {
        return ResponseEntity.ok(scheduleService.fetchLessonTimes());
    }
}
