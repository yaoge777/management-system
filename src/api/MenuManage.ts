import http from "@/request"
export const getAllMenu = function(){
  return http({
    url:'sys/menu',
    method:'get'
  })
}