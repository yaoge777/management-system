import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@element-plus/icons-vue'
import { login } from './api/LoginManage'

const pinia = createPinia()
const app:any = createApp(App) 
app.use(router).use(ElementPlus).use(pinia).mount('#app')

