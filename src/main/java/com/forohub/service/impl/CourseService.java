package com.forohub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forohub.dto.CourseDto;
import com.forohub.entity.Course;
import com.forohub.exceptions.ResourceNotFoundException;
import com.forohub.repository.CourseRepository;
import com.forohub.service.ICourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
public class CourseService implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<CourseDto> getCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> coursesDto = courses.stream().map(course -> objectMapper.convertValue(course, CourseDto.class)).collect(Collectors.toList());

        if ( coursesDto.size() > 0 ) {
            log.info("List of courses found: {}", coursesDto);
        }

        log.warn("List of course is empty");

        return coursesDto;
    }

    @Override
    public CourseDto getCourseById(Long id) throws ResourceNotFoundException {
        Optional<Course> course = courseRepository.findById(id);
        CourseDto courseDto;

        if ( course != null ) {
            courseDto = objectMapper.convertValue(course, CourseDto.class);
            log.info("Course with id {} was found", id);
            return courseDto;
        }

        log.error("Not found course with id {}", id);
        throw new ResourceNotFoundException("Not found course with id: " + id);
    }

    @Override
    public CourseDto postCourse(Course course) {
        Course courseSave = courseRepository.save(course);
        CourseDto courseDto = objectMapper.convertValue(courseSave, CourseDto.class);
        log.info("Save course successfully {}", courseDto);
        return courseDto;
    }

    @Override
    public CourseDto updateCourse(Course course) {
        return null;
    }

    @Override
    public String deleteCourse(Long id) throws ResourceNotFoundException {
        if ( getCourseById(id) != null ) {
            courseRepository.deleteById(id);
            log.warn("The course with id {} was delete successfully", id);
        }

        return "The course was delete successfully";
    }
}
