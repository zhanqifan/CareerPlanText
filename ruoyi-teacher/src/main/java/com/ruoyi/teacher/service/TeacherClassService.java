package com.ruoyi.teacher.service;

import com.ruoyi.teacher.domain.dto.ClassDTO;
import com.ruoyi.teacher.domain.dto.StudentDTO;
import com.ruoyi.teacher.domain.vo.StudentVO;

import java.util.List;

public interface TeacherClassService {
    int bingClass(ClassDTO classDTO);

    List<StudentVO> selectStudent(StudentDTO studentDTO);

}
