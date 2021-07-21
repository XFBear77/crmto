package com.xf.dao;

import com.xf.domain.Student;

import java.util.List;

/**
 * @author xf
 * @create 2021-05-22-20:04
 */
public interface StudentDao {
    int insertStudent(Student student);

    List<Student> selectStudent();
}
