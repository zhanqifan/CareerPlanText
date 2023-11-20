package com.ruoyi.student.service.impl;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.student.domain.dto.CollegeAnalysisDTO;
import com.ruoyi.student.system.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.CommonStudentMapper;
import com.ruoyi.student.domain.CommonStudent;
import com.ruoyi.student.service.ICommonStudentService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 公用 学生信息Service业务层处理
 * 
 * @author lihong
 * @date 2023-11-09
 */
@Service
@Slf4j
public class CommonStudentServiceImpl implements ICommonStudentService 
{
    @Resource
    private CommonStudentMapper commonStudentMapper;

    @Resource
    private ISysDeptService sysDeptService;

    /**
     * 查询公用 学生信息
     * 
     * @param sId 公用 学生信息主键
     * @return 公用 学生信息
     */
    @Override
    public CommonStudent selectCommonStudentBySId(Long sId)
    {
        return commonStudentMapper.selectCommonStudentBySId(sId);
    }

    /**
     * 构建学院组织架构
     */


    /**
     * 查询公用 学生信息列表
     * 
     * @param commonStudent 公用 学生信息
     * @return 公用 学生信息
     */
    @Override
    public List<CommonStudent> selectCommonStudentList(CommonStudent commonStudent)
    {

        return commonStudentMapper.selectCommonStudentList(commonStudent);


    }

    /**
     * 新增公用 学生信息
     * 
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    @Override
    public int insertCommonStudent(CommonStudent commonStudent)
    {
        return commonStudentMapper.insertCommonStudent(commonStudent);
    }

    /**
     * 修改公用 学生信息
     * 
     * @param commonStudent 公用 学生信息
     * @return 结果
     */
    @Override
    public int updateCommonStudent(CommonStudent commonStudent)
    {
        return commonStudentMapper.updateCommonStudent(commonStudent);
    }

    /**
     * 批量删除公用 学生信息
     * 
     * @param sIds 需要删除的公用 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonStudentBySIds(Long[] sIds)
    {
        return commonStudentMapper.deleteCommonStudentBySIds(sIds);
    }

    /**
     * 删除公用 学生信息信息
     * 
     * @param sId 公用 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonStudentBySId(Long sId)
    {
        return commonStudentMapper.deleteCommonStudentBySId(sId);
    }

    @Override
    public CommonStudent selectCommonStudentBySNum(String userName) {
        return commonStudentMapper.selectCommonStudentBySNum(userName);
    }

    @Override
    public List<CommonStudent> selectCommonStudentListByCollegeAnalysis(CollegeAnalysisDTO commonStudent) {
        return commonStudentMapper.selectCommonStudentListByCollegeAnalysis(commonStudent);
    }

    @Override
    @Transactional
    public String BuildDepartment(){
        List<CommonStudent> commonStudents = commonStudentMapper.selectCollege();
        HashMap<String, Long> collegeMap = new HashMap<>();
        //统计学院信息
        for(CommonStudent commonStudent:commonStudents){
            SysDept sysDept = new SysDept();
            sysDept.setDeptId(Long.valueOf(commonStudent.getCollegeCode()));
            sysDept.setDeptName(commonStudent.getCollege());
            sysDept.setParentId(0L);
            sysDept.setType("学院");
            try {
                sysDeptService.insertDept(sysDept);
                collegeMap.put(sysDept.getDeptName(),sysDept.getDeptId());
            }catch (Exception e){
                log.error("错误的数据:"+sysDept);
            }
        }
        //统计专业
        List<CommonStudent> commonProfessional =commonStudentMapper.selectProfessional();
        for(CommonStudent commonStudent:commonProfessional){
            SysDept sysDept = new SysDept();
            sysDept.setDeptName(commonStudent.getProfessional());
            Long parentId = collegeMap.get(commonStudent.getCollege());
            sysDept.setParentId(parentId);
            sysDept.setType("系部");
            sysDeptService.insertDept(sysDept);
        }
        //统计班级
        //查询专业列表
        List<SysDept> professionalList =sysDeptService.selectProfessionalList();
        List<CommonStudent> classList =commonStudentMapper.selectClassList();
        for(SysDept sysDept:professionalList){
            String professionalName = sysDept.getDeptName();
            Long collegeCode = sysDept.getParentId();
            for(CommonStudent commonStudent:classList){
                if(Long.valueOf(commonStudent.getCollegeCode()).equals(collegeCode) && commonStudent.getProfessional().equals(professionalName)){
                    SysDept sysDept1 = new SysDept();
                    sysDept1.setParentId(sysDept.getDeptId());
                    sysDept1.setDeptName(commonStudent.getClassname());
                    String classCode = commonStudent.getClassCode();
                    Long substring = Long.valueOf(classCode.substring(1));
                    sysDept1.setDeptId(substring);
                    sysDept1.setType("班级");
                    try{
                        sysDeptService.insertDept(sysDept1);
                    }catch (Exception e){
                        System.out.println(sysDept1);
                    }

                }
            }
        }
        return null;
    }
}
