import createAxios from '@/utils'


const http = createAxios({
  baseURL:'http://localhost:9999',
  timeout:5000,
  loading:true
})

export default http
