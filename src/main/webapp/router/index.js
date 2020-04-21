import Vue from 'vue'
import Router from 'vue-router'

import Layout from '@/components/layout'
// import Admin from '@/components/admin'

Vue.use(Router)

const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error'),
    hidden: true
  },

  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/home',
  //   children: [{
  //     path: 'home',
  //     name: 'Home',
  //     component: () => import('@/views/home/index'),
  //     meta: { title: 'Home', icon: 'home' }
  //   },
  //   {
  //     path: 'library',
  //     name: 'Library',
  //     component: () => import('@/views/library/index'),
  //     meta: { title: 'Library', icon: 'library' }
  //   },
  //   {
  //     path: 'user',
  //     name: 'User',
  //     component: () => import('@/views/user/index'),
  //     meta: { title: 'User', icon: 'user' }
  //   }]
  // },

  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'Home',
      component: () => import('@/views/home/index'),
      meta: { title: 'Home', icon: 'home' }
    }]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/library',
    children: [{
      path: 'library',
      name: 'Library',
      component: () => import('@/views/library/index'),
      meta: { title: 'Library', icon: 'library' }
    }]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/user',
    children: [{
      path: 'user',
      name: 'User',
      component: () => import('@/views/user/index'),
      meta: { title: 'User', icon: 'user' }
    }]
  },


  // {
  //   path: '/',
  //   component: Admin,
  //   redirect: '/dashboard',
  //   children: [{
  //     path: 'dashboard',
  //     name: 'Dashboard',
  //     component: () => import('@/views/dashboard/index'),
  //     meta: { title: 'Dashboard', icon: 'dashboard' }
  //   }]
  // },
  // 404 page must be placed at the end !
  { path: '*', redirect: '/404', hidden: true }
]

// create router
const createRouter = () => new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// reset router
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

// add router
export function addRouter(newRoutes) {
  router.addRoutes(newRoutes)
}

export default router
