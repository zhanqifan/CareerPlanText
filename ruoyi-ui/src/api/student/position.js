import request from '@/utils/request'

// 查询岗位管理列表
export function listPosition(query) {
  return request({
    url: '/student/position/list',
    method: 'get',
    params: query
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
export function delPosition(positionId) {
  return request({
    url: '/student/position/' + positionId,
    method: 'delete'
  })
}
