<template>
  <el-page-header :icon="null">
    <template #content>
      <div class="flex items-center">
        <el-avatar
          :size="32"
          class="mr-3"
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
        <span class="text-large font-600 mr-3"> Management System </span>
        
      </div>
    </template>
    <template #extra>
      <div class="flex items-center">
        <el-button type="primary" class="ml-2" @click="logOut">Log out</el-button>
      </div>
    </template>
  </el-page-header>
</template>


<script lang="ts" setup>
import {useRouter} from 'vue-router'
import Cookie from 'js-cookie'
import {useMenuStore} from '@/store/index'
const useMenu = useMenuStore()
const router = useRouter()
const logOut = () => {
  useMenu.menuData = []
  useMenu.addMenu=[]
  useMenu.token=''
  Cookie.remove('menuData', {path:'/', domain:'localhost'})
  Cookie.remove('addMenu', {path:'/', domain:'localhost'})
  Cookie.remove('token', {path:'/', domain:'localhost'})
  //router.push('/login') does not work, must re-load
  router.go(0)
}
</script>

<style scoped>

</style>