<template>
  <div>
    <el-menu
        active-text-color="#ffd04b"
        background-color="#545c64"
        class="el-menu-vertical"
        default-active="2"
        text-color="#fff"
      
      >
        <el-menu-item :index="child.name" v-for="child in noChildren" :key="child.name" @click="() => router.push(child.path)">
          <span>{{child.name}}</span>
        </el-menu-item>    
        <div :index="child.name" v-for="child in hasChildren" :key="child.name">
          <el-menu-item v-for="c in child.children" :index="c.name" :key="c.name" @click="() => router.push(c.path)">{{c.name}}</el-menu-item>
        </div>
        
      </el-menu>
  </div>
</template>

<script lang="ts" setup>
import {useMenuStore} from '@/store/index'
import { useRouter } from 'vue-router'

const data = useMenuStore()
const hasChildren = data.hasChildren
const noChildren = data.noChildren
const router = useRouter()

</script>

<style scoped>
.el-menu-vertical{
  height:100vh
}

span {

  text-align: center;
  vertical-align: middle;
  font-size:20px
   
}

.el-menu-item, .el-sub-menu{
  height:60px;
  margin-bottom:50px;
} 
</style>