import http from '@/request'

interface loginData{
  username:string,
  password:string
}


export function login(data:loginData){
  return http({
    url:'/sys/user/login',
    method:'post',
    data
  })
}

