import request from '@/utils/request'

// 添加自评
export const TodoComments = (data)=>{
    return request.post('/students/evaluate',data)
}

// 查看自评
export const getComment = (skillsId)=>{
    return request.get(`/students/evaluate/${skillsId}`)
}
// 修改自评
export const ChangeComment = (data) =>{
    return request.put('/students/evaluate',data)
}
// 删除自评
export const DeleteComment = (evaluateId) =>{
    return request.delete("/students/evaluate/"+ evaluateId)
}
// 删除技能
export const DeleteSkill = (skillsId)=>{
    return request.delete('/student/position/skills/'+skillsId)
}