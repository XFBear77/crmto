package com.xf.service;

import com.xf.domain.Student;

import java.util.List;

/**
 * @author xf
 * @create 2021-05-22-20:07
 */
public interface StudentService {
    int addStudent(Student student);
    List<Student> queryStudent();
}
