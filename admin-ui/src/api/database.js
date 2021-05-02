import request from '@/utils/request'

export function listDatabase() {
  return request({
    url: '/databases/list',
    method: 'get'
  })
}


export function pageDatabase(params) {
  return request({
    url: '/databases',
    method: 'get',
    params: params
  })
}


export function addDatabase(data) {
  return request({
    url: '/database',
    method: 'post',
    data
  })
}

export function getById(id) {
  return request({
    url: '/database/' + id,
    method: 'get'
  })
}


export function updateDatabase(data) {
  return request({
    url: '/database',
    method: 'put',
    data
  })
}


export function deleteDatabase(id) {
  return request({
    url: '/database/' + id,
    method: 'delete'
  })
}

export function dbTypes() {
  return request({
    url: '/database/dbType',
    method: 'get'
  })
}
