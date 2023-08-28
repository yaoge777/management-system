import { ElLoading, ElMessageBox } from "element-plus";
import axios, {AxiosInstance} from "axios";


let loading = 0
let El:any = null
const addLoading = () =>  {
  if(loading == 0){
    loading++

    El = ElLoading.service({
      lock:true,
      text: "Loading...",
      background: 'rgba(0, 0, 0, 0.7)'
    })
  }
}

const cancelLoading = () => {
  if(loading == 1){
    loading--
    setTimeout(() => El.close(), 500)
  }
}

const createAxios = function(config : any) : AxiosInstance{
   const http = axios.create({

    withCredentials: true,
    ...config
  })

  http.interceptors.request.use((config:any) => {
    const {loading = true} = config 
    if(loading) addLoading()
    return config
  }, 
  (err) => {
    cancelLoading()
    ElMessageBox({
      type:'warning',
      message:'nextwork error'    
    })
    return Promise.reject(err)
  })

  http.interceptors.response.use((rep: any) => {
    const {loading} = rep.config
    if(loading) cancelLoading()
    return rep
  },(err) => {
    const {loading} = err.config
    if(loading) cancelLoading()
    return Promise.reject(err)
  })
  return http
}

export default createAxios