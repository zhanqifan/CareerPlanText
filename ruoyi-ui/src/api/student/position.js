import request from '@/utils/request'
import req from 'express/lib/request'

// 查询岗位管理列表
export function listPosition(positionName, state, pageNum, pageSize ) {
  return request.get('/student/position/list', {
    params: {
      positionName,
      state,
      pageNum,
      pageSize
    },
  })
}

// 查询岗位管理详细
export function getPosition(positionId) {
  return request({
    url: '/student/position/' + positionId,
    method: 'get'
  })
}

// 新增岗位管理
export function addPosition(data) {
  return request({
    url: '/student/position',
    method: 'post',
    data: data
  })
}

// 修改岗位管理
export function updatePosition(data) {
  return request({
    url: '/student/position',
    method: 'put',
    data: data
  })
}

// 删除岗位管理
export function delPosition(positionIds) {
  return request({
    url: '/student/position/' + positionIds,
    method: 'delete'
  })
}

// 发布草稿岗位
export const PublicPosition = (positionId) => {
  return request.get('/student/position/publish/' + positionId)
}
// 删除草稿行数据
export const DeleteRow = (skillsId) => {
  return request.delete('/student/position/skills/' + skillsId)
}

// 获取目录列表
export const getcateLog = () => {
  return request.get('/student/ctatlogue/list')
}
// 添加岗位
export const AddPosition = (data) => {
  return request.post('/student/position', data)
}
// 设置主目标
export const SetPositoin = (positionId) => {
  return request.get('/student/position/setPrimaryTarget/' + positionId)
}
// 废止目标
export const StopPosition = (positionId) => {
  return request.get('/student/position/repeal/' + positionId)
}
// 修改草稿岗位名称
export const changeName =(data)=>{
  return request.put('/student/position/draft',data)
}
export const ReviewList = ()=>{
  return request.get('/teacher/review')
}
// 学生查看教室批阅
export const GetRead =(positionId)=>{
  return request.get('/teacher/review/evaluate/'+positionId)
}