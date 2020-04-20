import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/'

Vue.use(Router)

const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/About.vue')
  },
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
