import http from '@/request/'
import {roleType} from '@/type/role'

export const getSelectMenu = (param:roleType) => {
  return http({
    url:"/sys/roleMenu/query",
    data: JSON.stringify(param),
    method:'post',
    headers:{
      "Content-Type": "application/json"
    }
  })
}