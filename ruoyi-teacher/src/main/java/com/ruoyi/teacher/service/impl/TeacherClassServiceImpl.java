package com.ruoyi.teacher.service.impl;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ICommonStudentService;
import com.ruoyi.student.service.ITargetPositionService;
import com.ruoyi.student.system.service.ISysDeptService;
import com.ruoyi.student.system.service.ISysUserService;
import com.ruoyi.teacher.domain.TeacherClass;
import com.ruoyi.teacher.domain.dto.ClassDTO;
import com.ruoyi.teacher.domain.dto.StudentDTO;
import com.ruoyi.teacher.domain.vo.StudentVO;
import com.ruoyi.teacher.mapper.TeacherClassMapper;
import com.ruoyi.teacher.service.TeacherClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherClassServiceImpl implements TeacherClassService {

    @Resource
    private TeacherClassMapper teacherClassMapper;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ICommonStudentService commonStudentService;

    @Autowired
    private ITargetPositionService targetPositionService;


    /**
     * 任课教师查询所教学生列表
     *
     * @return
     */
    @Override
    public List<StudentVO> selectStudent(StudentDTO studentDTO) {
        //使用学院，专业，年级查询
        ArrayList<StudentVO> studentVOS = new ArrayList<>();
        if (StringUtils.isNull(studentDTO.getUserName())) {
            TeacherClass teacherClass = new TeacherClass();

            teacherClass.setTeacherId(Long.parseLong(SecurityUtils.getUsername()));
            teacherClass.setCollegeId(studentDTO.getCollegeId());
            teacherClass.setProfessionalId(studentDTO.getProfessionalId());
            teacherClass.setClassId(studentDTO.getClassId());
            teacherClass.setGrade(studentDTO.getGrade());
            //根据筛选参数查询教师教的班级
            List<TeacherClass> teacherClasses = teacherClassMapper.selectTeacherClassList(teacherClass);
            //查询该班级下的学生
            for (TeacherClass teacherClass1 : teacherClasses) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(teacherClass1.getClassId());
                List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
                for (SysUser sysUser1 : sysUsers) {
                    CommonStudent commonStudent = commonStudentService.selectCommonStudentBySNum(sysUser1.getUserName());
                    StudentVO studentVO = this.EncapsulateStudentInfo(commonStudent);
                    studentVOS.add(studentVO);
                }
            }
        } else {
            //查询该教师所教的班级
            List<TeacherClass> teacherClass = teacherClassMapper.selectTeacherClassByTeacherId(SecurityUtils.getUserId());
            SysUser sysUser = new SysUser();
            //通过学号查询
            String sNumberOrStudentName = studentDTO.getUserName();
            if (Characterutils(sNumberOrStudentName)) {
                sysUser.setUserName(sNumberOrStudentName);
            }else {
                sysUser.setNickName(sNumberOrStudentName);
            }
            List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
            //查询这个学生是否是在该教师所教授的班级中
            for (TeacherClass teacherClass1 : teacherClass) {
                for (SysUser student : sysUsers) {
                    if (student.getDeptId().equals(teacherClass1.getClassId())) {
                        //学生学号
                        String SNumber = student.getUserName();
                        CommonStudent commonStudent = commonStudentService.selectCommonStudentBySNum(SNumber);
                        StudentVO studentVO = this.EncapsulateStudentInfo(commonStudent);
                        studentVOS.add(studentVO);
                    }
                }
            }
        }
        return studentVOS;
    }


    /**
     * 绑定班级
     *
     * @return
     */
    @Override
    public int bingClass(ClassDTO classDTO) {
        TeacherClass teacherClass = new TeacherClass();
        Long classId = classDTO.getClassId();
        SysDept sysDept = sysDeptService.selectDeptById(classId);
        //系部id
        teacherClass.setProfessionalId(classDTO.getProfessionalId());
        //院系id
        teacherClass.setCollegeId(classDTO.getCollegeId());
        String deptName = sysDept.getDeptName();
        String grade = deptName.substring(0, 4);
        teacherClass.setGrade(grade);
        teacherClass.setClassId(classId);
        teacherClass.setTeacherId(SecurityUtils.getUserId());
        return teacherClassMapper.insertTeacherClass(teacherClass);

    }

    private StudentVO EncapsulateStudentInfo(CommonStudent commonStudent) {
        StudentVO studentVO = new StudentVO();
        //学生基础信息
        studentVO.setStudentId(commonStudent.getSNum());
        studentVO.setCollege(commonStudent.getCollege());
        studentVO.setCultivationLevel(commonStudent.getCultivationLevel());
        studentVO.setProfessional(commonStudent.getProfessional());
        studentVO.setStudentName(commonStudent.getName());
        studentVO.setClassname(commonStudent.getClassname());
        //学生岗位目标信息
        String sNum = commonStudent.getSNum();
        List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionListByUserName(sNum);
        if (StringUtils.isNotEmpty(targetPositions)) {
            ArrayList<TargetPosition> targetPositions1 = new ArrayList<>();
            for (TargetPosition targetPosition : targetPositions) {
                //主目标
                if (targetPosition.getIsMain() == 0) {
                    studentVO.setMainTarget(targetPosition);
                }
                //次目标
                if (targetPosition.getIsMain() == 1) {
                    studentVO.setSecondaryTarget(targetPosition);
                }
                //废弃目标
                if (targetPosition.getState() == 0) {
                    targetPositions1.add(targetPosition);
                }
                studentVO.setAbandonedTarget(targetPositions1);
            }
        }
        return studentVO;
    }


    private static boolean Characterutils(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}


