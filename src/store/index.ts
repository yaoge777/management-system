import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'
import Cookie from 'js-cookie'
export const useMenuStore = defineStore('menu', {
  state: () => {
    return { 
      menuData:[] as any,
      token:'',
      addMenu:[] as any,
      router: useRouter(),
    }
  },

  actions: {
    setRoute:function(){
      if(!Cookie.get('token')) return
      if(this.addMenu.length == 0){
        this.addMenu = JSON.parse(Cookie.get('addMenu') as string)
        this.menuData = JSON.parse(Cookie.get('menuData') as string)
        this.token = Cookie.get('token') as string
        // this.setComponent()
      }
      this.addMenu.forEach((e:any) => {
        this.getParentRoutes(e)
      })

    },
    // setComponent:function(){
    //   this.addMenu.forEach((child:any) => {
    //     child.children.forEach((v:any) => {
    //       v.component = () => import(`@/views/${v.meta.title}`)
    //     })
    //   })
      
    // },
    getParentRoutes:function (r:any) {
      const route:any = {
        path:r.path,
        title:r.title,
        name:r.name,
        component: () => import('@/views/HomeView.vue'),
        children:[]
      }
      r.children.forEach((i:any) => {
        const child = this.getChildrenRoutes(i)
        route.children.push(child)
      })
      
      this.menuData.push(route)
    },

    getChildrenRoutes: function (r:any){
      const route:any = {
        path: r.path,
        title: r.title,
        name:r.name,
        component: () => import(`@/views/${r.component}`)
      }
      this.router.options.routes.filter(v => v.name == 'main')[0].children?.push(route)
      this.router.addRoute('main',route)
      return route
    }
    
  },

  getters: {
    hasChildren(state){
      return state.menuData.filter((v:any) => !!v.children)
    },

    noChildren(state){
      return state.menuData.filter((v:any) => !v.children)
    }
  }
})