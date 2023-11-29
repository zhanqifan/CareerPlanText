package com.ruoyi.teacher.service.impl;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.PageUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.domain.TargetPosition;
import com.ruoyi.student.service.ICommonStudentService;
import com.ruoyi.student.service.ITargetPositionService;
import com.ruoyi.student.system.service.ISysDeptService;
import com.ruoyi.student.system.service.ISysPostService;
import com.ruoyi.student.system.service.ISysUserService;
import com.ruoyi.teacher.constant.PostCodeConstants;
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
import java.util.stream.Collectors;

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

    @Resource
    private ISysPostService sysPostService;


    /**
     * 任课教师查询所教学生列表
     */
    @Override
    public List<StudentVO> selectStudent(StudentDTO studentDTO) {
        //查看老师岗位
        Long userId = SecurityUtils.getUserId();
        List<String> postCodes = sysPostService.selectPostCodeList(userId);
        String postCode = postCodes.get(0);
        System.out.println(postCode);
        //校验教师岗位
        ArrayList<StudentVO> studentVOS = new ArrayList<>();
        if(StringUtils.isNotNull(postCode)){
            String code = this.verifyPostCode(postCode);
            switch (code) {
                    //院长
                case PostCodeConstants.PRESIDENT:
                    SysUser sysUser = sysUserService.selectUserById(userId);
                    SysDept dept = sysUser.getDept();
                    return this.president(studentVOS,dept.getDeptName(),studentDTO);
                    //系主任
                case PostCodeConstants.DEAN:
                    SysUser user = sysUserService.selectUserById(userId);
                    SysDept sysDept = user.getDept();
                    return this.dean(studentVOS,sysDept.getDeptName(),studentDTO);
                    //教师
                case PostCodeConstants.TEACHER:
                    return this.teacher(studentVOS, studentDTO);
                default:
                    //未知岗位
                    return studentVOS;
            }
        }
        return studentVOS;
    }

    /**
     * 院长
     */
    private ArrayList<StudentVO> president(ArrayList<StudentVO> studentVOS,String college,StudentDTO studentDTO ){
        CommonStudent commonStudent = new CommonStudent();
        if(StringUtils.isNull(studentDTO.getUserName())
                &&StringUtils.isNull(studentDTO.getClassName())
                &&StringUtils.isNull(studentDTO.getProfessional())
                &&StringUtils.isNull(studentDTO.getGrade())
                &&StringUtils.isNull(studentDTO.getCollege())
        ){
            commonStudent.setCollege(college);
        }else {
            QueryStudentInformation(studentDTO, commonStudent);
        }
        List<CommonStudent>  commonStudents = commonStudentService.selectCommonStudentList(commonStudent);
        for (CommonStudent student : commonStudents) {
            studentVOS.add(this.EncapsulateStudentInfo(student));
        }
        return studentVOS;
    }


    /**
     * 系主任
     */
    private ArrayList<StudentVO> dean(ArrayList<StudentVO> studentVOS,String professional,StudentDTO studentDTO ){
        List<CommonStudent> commonStudents;
        CommonStudent commonStudent = new CommonStudent();
        if(StringUtils.isNull(studentDTO.getUserName())
                &&StringUtils.isNull(studentDTO.getClassName())
                &&StringUtils.isNull(studentDTO.getProfessional())
                &&StringUtils.isNull(studentDTO.getGrade())
                &&StringUtils.isNull(studentDTO.getCollege())
        ){
            commonStudent.setProfessional(professional);
        }else {
            QueryStudentInformation(studentDTO, commonStudent);
        }

        commonStudents = commonStudentService.selectCommonStudentList(commonStudent);
        for (CommonStudent student : commonStudents) {
            studentVOS.add(this.EncapsulateStudentInfo(student));
        }
        return studentVOS;
    }



    /**
     * 普通教师
     */
    private ArrayList<StudentVO> teacher(ArrayList<StudentVO> studentVOS,StudentDTO studentDTO ){
        //查询该老师所教班级列表
        TeacherClass teacherClass = new TeacherClass();
        String teacherId = SecurityUtils.getUsername();
        teacherClass.setTeacherId(teacherId);
        List<TeacherClass> teacherClasses = teacherClassMapper.selectTeacherClassList(teacherClass);
        if (StringUtils.isEmpty(teacherClasses)) {
            return studentVOS;
        }
        String SNum = studentDTO.getUserName();
        List<CommonStudent> commonStudentList;
        if (StringUtils.isNotNull(SNum)) {
            CommonStudent commonStudent = new CommonStudent();
            //返回属于该教师所教的学生列表
            if (Characterutils(SNum)) {
                //根据学号查询
                commonStudent.setSNum(SNum);
            } else {
                //根据姓名查询
                commonStudent.setName(SNum);
            }
            commonStudentList = commonStudentService.selectCommonStudentList(commonStudent);
            //查询该教师所教的学生列表
            List<CommonStudent> commonStudents = commonStudentService.QueryStudentsTaughtByTeacherId(commonStudentList, teacherId);
            for (CommonStudent student : commonStudents) {
                studentVOS.add(this.EncapsulateStudentInfo(student));
            }
        } else {
            //通过学院数据筛选
            teacherClass.setCollege(studentDTO.getCollege());
            teacherClass.setProfessional(studentDTO.getProfessional());
            teacherClass.setGrade(studentDTO.getGrade());
            teacherClass.setClassName(studentDTO.getClassName());
            List<TeacherClass> teacherClassList = teacherClassMapper.selectTeacherClassList(teacherClass);
            //班级名称列表
            List<String> classNameList = teacherClassList.stream()
                    .map(TeacherClass::getClassName)
                    .collect(Collectors.toList());
            //查出班级的学生
            List<CommonStudent> commonStudents = commonStudentService.selectCommonStudentByClassNameList(classNameList);
            for (CommonStudent student : commonStudents) {
                studentVOS.add(this.EncapsulateStudentInfo(student));
            }
    }
        return studentVOS;
    }


    private static void QueryStudentInformation(StudentDTO studentDTO, CommonStudent commonStudent) {
        String userName = studentDTO.getUserName();
        if (StringUtils.isNotNull(userName)) {
            //返回属于该教师所教的学生列表
            if (Characterutils(userName)) {
                //根据学号查询
                commonStudent.setSNum(userName);
            } else {
                //根据姓名查询
                commonStudent.setName(userName);
            }
        } else {
            //根据传进来的条件过滤
            commonStudent.setCollege(studentDTO.getCollege());
            commonStudent.setGrade(studentDTO.getGrade());
            commonStudent.setProfessional(studentDTO.getProfessional());
            commonStudent.setClassname(studentDTO.getClassName());
        }
    }
    /**
     * 校验岗位编码
     */
    private String verifyPostCode(String postCode) {
        if (isNumeric(postCode)) {
            return PostCodeConstants.PRESIDENT;
        } else if (isAlpha(postCode)) {
            return PostCodeConstants.DEAN;
        } else if (postCode.equals("#")) {
            return PostCodeConstants.TEACHER;
        } else {
            return " ";
        }
    }


    /**
     * 绑定班级
     */
    @Override
    public int bingClass(ClassDTO classDTO) {
        TeacherClass teacherClass = new TeacherClass();
        String className = classDTO.getClassName();
        teacherClass.setClassName(className);
        teacherClass.setCollege(classDTO.getCollege());
        teacherClass.setProfessional(classDTO.getProfessional());
        String grade = className.substring(0, 4);
        teacherClass.setGrade(grade);
        teacherClass.setTeacherId(SecurityUtils.getUsername());
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
        studentVO.setGrade(commonStudent.getGrade());
        studentVO.setClassname(commonStudent.getClassname());
        //学生岗位目标信息
        String sNum = commonStudent.getSNum();
        List<TargetPosition> targetPositions = targetPositionService.selectTargetPositionListByUserName(sNum);
        if (StringUtils.isNotEmpty(targetPositions)) {
            ArrayList<TargetPosition> targetPositions1 = new ArrayList<>();
            for (TargetPosition targetPosition : targetPositions) {
                //主目标
                if (targetPosition.getIsMain() == 1) {
                    studentVO.setMainTarget(targetPosition);
                }
                //次目标
                if (targetPosition.getIsMain() == 0) {
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


    /**
     * 判断是学号还是姓名
     */
    private static boolean Characterutils(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}


