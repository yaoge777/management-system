<template>
  <div class="login-box">
    <el-form
    ref="ruleFormRef"
    :model="data.ruleForm"
    status-icon
    :rules="rules"
    label-width="80px"
    class="demo-ruleForm"
  >
    <el-form-item label="username" prop="username">
      <el-input v-model="data.ruleForm.username" autocomplete="off" />
    </el-form-item>
    <el-form-item label="password" prop="password">
      <el-input v-model="data.ruleForm.password" type="password" autocomplete="off"/>
    </el-form-item>
   
    <el-form-item>
      <el-button class="login-button" type="primary" 
        @click="submitForm(ruleFormRef)">Submit</el-button
      >
      <el-button class="login-button" @click="resetForm">Reset</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script lang="ts" setup>
import {reactive,ref } from 'vue'
import {LoginData} from '../type/login'
import type { FormInstance } from 'element-plus'
import {login} from '@/api/LoginManage'
import {useRouter} from 'vue-router'
import {useMenuStore} from '@/store/index'
import Cookie from 'js-cookie'
const menuStore = useMenuStore()
const data = reactive(new LoginData())
const rules = {
    username:[{required:true, message:'username input', trigger:'blur'}],
    password:[{required:true, message:'pwd input', trigger:'blur'}],
  }
//login
const router = useRouter()
const ruleFormRef = ref<FormInstance>()
const submitForm = (form:FormInstance | undefined) => {
  if(!form) return;
  form.validate(valid => {
    if (valid) {
      login(data.ruleForm).then(res => {
        const routeData = res.data.data.menu
      
        Cookie.set('menuData', JSON.stringify([...router.getRoutes().filter(v => v.name=='main')[0].children]))
        Cookie.set('addMenu', JSON.stringify(routeData))
        Cookie.set('token', '1')
        menuStore.menuData = [...router.getRoutes().filter(v => v.name=='main')[0].children]
        menuStore.addMenu = routeData
        menuStore.setRoute()
        // routeData.forEach((e:any) => {
        //   getParentRoutes(e)
        // })
        menuStore.token='1'
        
        router.push('/main/home')
      })
    } else {
      console.log('err')
      return false
    }
  })
}
//reset
const resetForm = () => {
  data.ruleForm.username='',
  data.ruleForm.password=''
}

// const getParentRoutes = (r:any) => {
//   let route:any = {
//     path:r.path,
//     redirect:`main${r.redirect}`,
//     title:r.title,
//     name:r.name,
//     component: () => import('@/views/HomeView.vue'),
//     children:[]
//   }
//   r.children.forEach((i:any) => {
//     let child = getChildrenRoutes(i)
//     route.children.push(child)
//   })
  
//   menuStore.menuData.push(route)
// }

// const getChildrenRoutes = (r:any) => {
//   let route:any = {
//     path: r.path,
//     title: r.title,
//     name:r.name,
//     component: () => import(`@/views/${r.component}`)
//   }
//   router.options.routes.filter(v => v.name == 'main')[0].children?.push(route)
//   router.addRoute('main',route)
//   return route
// }

</script>

<style lang='scss' scoped>
.login-box{
  width:100%;
  height:100%;
  background-color:gray;
  padding: 1px;
  .demo-ruleForm{
    width:500px;
    margin:200px auto;
    background-color: white;
    padding:40px;
    border-radius: 20px;
  }
  .login-button{
    width:48%;
  }

}
</style>