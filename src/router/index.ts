import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import {useMenuStore} from '@/store/index'
import Cookie from 'js-cookie'
const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'login',
    meta:{title:'LoginView'},
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "login" */ '../views/LoginView.vue')
    
  },
  {
    path:'/main',
    name: 'main',
    redirect:'/main/home',
    meta:{title:'MainView'},
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "login" */ '../views/MainView.vue'),
    children:[
      {
        path: 'home',
        name: 'home',
        meta:{title:'HomeView'},
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "login" */ '../views/HomeView.vue')
      },
      {
        path: 'p1',
        name: 'p1',
        meta:{title:'PageOne'},
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "login" */ '../views/PageOne.vue'),
      },
      {
        path: 'p2',
        name: 'p2',
        meta:{title:'PageTwo'},
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "login" */ '../views/PageTwo.vue')
      },
      
    ]
  },
  

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})


router.beforeEach((to, from) => {
  const token = Cookie.get('token')
  if (!token && to.name != 'login'){
    return {name:'login'}
  } else if (token && to.name == 'login'){
    return {name:'home'}
  }
})
export default router
