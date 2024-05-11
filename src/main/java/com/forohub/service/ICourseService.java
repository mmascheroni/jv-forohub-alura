package com.forohub.service;

import com.forohub.dto.CourseDto;
import com.forohub.entity.Course;
import com.forohub.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICourseService {

    List<CourseDto> getCourses();

    CourseDto getCourseById(Long id) throws ResourceNotFoundException;

    CourseDto postCourse(Course course);

    CourseDto updateCourse(Course course);

    String deleteCourse(Long id) throws ResourceNotFoundException;
}
