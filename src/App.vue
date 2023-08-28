<template>
  <div id="app">
      <router-view></router-view>
  </div>

</template>

<script setup lang='ts'>
import {useMenuStore} from '@/store/index'
import Cookie from 'js-cookie';
import {useRouter} from 'vue-router'
const menuStore = useMenuStore()
menuStore.setRoute()
const router = useRouter()
router.push('/main/home')
</script>

<style lang="scss">
*{
  padding:0px;
  margin:0px
}

html,body,#app {
  width:100%;
  height:100%
}
</style>
