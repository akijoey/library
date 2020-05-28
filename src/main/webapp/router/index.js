import Vue from 'vue'
import Router from 'vue-router'

import Client from '@/layout/client'
// import Admin from '@/layout/admin'

Vue.use(Router)

const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error'),
    hidden: true
  },

  {
    path: '/',
    component: Client,
    redirect: '/home',
    children: [
      {
        path: '/user',
        name: 'User',
        component: () => import('@/views/user'),
        meta: { title: '个人中心', icon: 'user' }
      },
      {
        path: '/library',
        name: 'Library',
        component: () => import('@/views/library'),
        meta: { title: '图书馆', icon: 'book' }
      },
      {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/home'),
        meta: { title: '主页', icon: 'home' }
      }
    ]
  },

  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/home',
  //   children: [
  //     {
  //       path: '/home',
  //       name: 'Home',
  //       component: () => import('@/views/home/index'),
  //       meta: { title: '主页', icon: 'home' }
  //     },
  //     {
  //       path: '/library',
  //       name: 'Library',
  //       component: () => import('@/views/library/index'),
  //       meta: { title: '图书馆', icon: 'book' }
  //     },
  //     {
  //       path: '/user',
  //       name: 'User',
  //       component: () => import('@/views/user/index'),
  //       meta: { title: '个人中心', icon: 'user' },
  //       redirect: '/user/info',
  //       children: [
  //         {
  //           path: 'info',
  //           name: 'Info',
  //           component: () => import('@/views/user/info/index'),
  //           meta: { title: 'Info', icon: 'info' }
  //         },
  //         {
  //           path: 'passwd',
  //           name: 'Passwd',
  //           component: () => import('@/views/user/passwd/index'),
  //           meta: { title: 'Password', icon: 'info' }
  //         },
  //         {
  //           path: 'record',
  //           name: 'Record',
  //           component: () => import('@/views/user/record/index'),
  //           meta: { title: 'Record', icon: 'info' }
  //         }
  //       ]
  //     }
  //   ]
  // },

  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/home',
  //   children: [{
  //     path: 'home',
  //     name: 'Home',
  //     component: () => import('@/views/home/index'),
  //     meta: { title: 'Home', icon: 'home' }
  //   }]
  // },
  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/library',
  //   children: [{
  //     path: 'library',
  //     name: 'Library',
  //     component: () => import('@/views/library/index'),
  //     meta: { title: 'Library', icon: 'library' }
  //   }]
  // },
  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/user',
  //   children: [{
  //     path: 'user',
  //     name: 'User',
  //     component: () => import('@/views/user/index'),
  //     meta: { title: 'User', icon: 'user' },
  //     redirect: '/user/info',
  //     children: [{
  //       path: 'info',
  //       name: 'Info',
  //       component: () => import('@/views/user/info/index'),
  //       meta: { title: 'Info', icon: 'info' }
  //     }]
  //   }]
  // },
  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/user',
  //   children: [{
  //     path: 'user',
  //     name: 'User',
  //     component: () => import('@/views/user/index'),
  //     meta: { title: 'User', icon: 'user' },
  //     redirect: '/user/passwd',
  //     children: [{
  //       path: 'passwd',
  //       name: 'Passwd',
  //       component: () => import('@/views/user/passwd/index'),
  //       meta: { title: 'Passwd', icon: 'passwd' }
  //     }]
  //   }]
  // },
  // {
  //   path: '/',
  //   component: Client,
  //   redirect: '/user',
  //   children: [{
  //     path: 'user',
  //     name: 'User',
  //     component: () => import('@/views/user/index'),
  //     meta: { title: 'User', icon: 'user' },
  //     redirect: '/user/record',
  //     children: [{
  //       path: 'record',
  //       name: 'Record',
  //       component: () => import('@/views/user/record/index'),
  //       meta: { title: 'Record', icon: 'record' }
  //     }]
  //   }]
  // },


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
