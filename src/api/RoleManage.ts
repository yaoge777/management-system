import http from '@/request'
import {roleType, pageType} from "@/type/role"

export const getAllRoles = function(param:pageType){
  return http({
    url:"sys/role/all",
    method:'get',
    params:param
  })
}

export const getRoleList = function(){
  return http({
    url:'sys/role/rolelist',
    method:"get",
  })
}


export const addRole = function(param: roleType){
  return http({
    url:'sys/role/add',
    method:'put',
    data:JSON.stringify(param),
    headers:{
      "Content-Type": "application/json"
    }
  })
}

export const searchRole = function(param1:object, param2:pageType){
  const param = {...param1, ...param2}
  return http({
    url:"sys/role/query",
    method:'get',
    params:param
  })
}

export const deleteRole = function(param:roleType){
  return http({
    url:'sys/role/delete',
    method:'delete',
    data:JSON.stringify(param),
    headers:{
      "Content-Type": "application/json"
    }
  })
}

export const updateRole = function(param: roleType){
  return http({
    url:'sys/role/edit',
    method:'post',
    data:JSON.stringify(param),
    headers:{
      "Content-Type": "application/json"
    }
  }) 
}