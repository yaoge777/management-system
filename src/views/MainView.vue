<template>
  <div>
    <el-container>
      <el-header><header-view/></el-header>
      <el-container>
        <el-aside width="200px"><aside-view/></el-aside>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import HeaderView from '@/components/HeaderView.vue'
import AsideView from '@/components/AsideView.vue'


</script>

<style scoped>

</style>