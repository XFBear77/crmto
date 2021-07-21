package com.xf.service.impl;

import com.xf.dao.StudentDao;
import com.xf.domain.Student;
import com.xf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xf
 * @create 2021-05-22-20:08
 */

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Override
    public int addStudent(Student student) {
        int rows = studentDao.insertStudent(student);
        return rows;
    }

    @Override
    public List<Student> queryStudent() {
        List<Student> list = studentDao.selectStudent();
        return list;
    }
}
