package com.xf.controller;

import com.xf.domain.Student;
import com.xf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xf
 * @create 2021-05-22-20:11
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    //添加学生
    @RequestMapping("/addStudent.do ")
    public ModelAndView addStudent(Student student) {
        System.err.println(0000000);
        ModelAndView mv = new ModelAndView();
        String msg = "注册失败！！";
        //调用service处理业务逻辑
        int rows = service.addStudent(student);
        if(rows>0){
           msg="注册成功！";
        }
        mv.addObject(msg,student.getName()+","+msg);
        mv.setViewName("result");
        System.err.println(11111);
        return mv;
    }
}
