import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// override push
const push = Router.prototype.push
Router.prototype.push = function newPush(location, resolve, reject) {
  if (resolve || reject) {
    return push.call(this, location, resolve, reject)
  }
  return push.call(this, location).catch(error => error)
}

const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/error',
    component: () => import('@/views/error'),
    hidden: true
  },
  
  // {
  //   path: '/',
  //   component: () => import('@/layout/client'),
  //   redirect: '/home',
  //   children: [
  //     {
  //       path: '/user',
  //       name: 'User',
  //       component: () => import('@/views/user'),
  //       meta: { title: '个人中心', icon: 'user' }
  //     },
  //     {
  //       path: '/library',
  //       name: 'Library',
  //       component: () => import('@/views/library'),
  //       meta: { title: '图书馆', icon: 'book' }
  //     },
  //     {
  //       path: '/home',
  //       name: 'Home',
  //       component: () => import('@/views/home'),
  //       meta: { title: '主页', icon: 'home' }
  //     }
  //   ]
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
]

// format routes
const formatRoutes = routes => {
  let newRoutes = []
  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let newRoute = {
      name: route.name,
      path: route.path,
      redirect: route.redirect,
      component: () => import(`../${route.component}/index.vue`),
      meta: {
        title: route.title,
        icon: route.icon
      },
      children: route.children
    }
    newRoutes.push(newRoute)
  })
  return newRoutes
}

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
  router.options.routes = constantRoutes
  router.matcher = newRouter.matcher
}

// add router
export function addRouter(routes) {
  const newRoutes = formatRoutes(routes)
  constantRoutes.forEach(route => {
    newRoutes.push(route)
  })
  // error page must be placed at the end
  newRoutes.push({
    path: '*',
    redirect: '/error',
    hidden: true
  })
  router.options.routes = newRoutes
  router.addRoutes(newRoutes)
}

export default router
