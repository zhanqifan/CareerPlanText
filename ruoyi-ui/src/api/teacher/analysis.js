import request from '@/utils/request'

// 查询学院列表
export const getCollegeList = () =>{
    return request.get('/system/dept/getCollegeList')
}
// 查询专业列表
export const getProfessionalList=(deptname)=>{
    return request.get('/system/dept/getProfessionalList/'+deptname)
}
// 查看学院数据分析
export const getanalysis = (data) =>{
    return request.post('/teacher/analysis',data)
}
