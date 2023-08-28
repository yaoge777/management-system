import http from '@/request'
import { userType, searchType } from '@/type/user'
interface pageData{
  PageSize: number,
  PageNumber: number
}



export function getAllUsers(data:pageData) {
  return http(
    {url:'/sys/user/all',
    method:'get',
    params:data}
  )
}

export const addUser = (para: userType) => {
  return http({
    method: "put",
    url:"/sys/user/add",
    data: JSON.stringify(para),
    headers:{
      "Content-Type": "application/json"
    }
  })
}

export const getUserById = (id:string) => {
  return http({
    url:`/sys/user/${id}`,
    method:'get'
  })
}

export const updateUser = (para:userType) => {
  return http({
    method:"post",
    url: "/sys/user/edit",
    data: JSON.stringify(para),
    headers:{
      "Content-Type": "application/json"
    }
  })
}

export const deleteUser = (para:userType) => {
  return http({
    method:'delete',
    url:'/sys/user/delete',
    data:JSON.stringify(para),
    headers:{
      "Content-Type": "application/json"
    }
  })
}

export const searchUser = (para:object) => {
  return http({
    method: "get",
    url:"/sys/user/query",
    params: para
  })
}