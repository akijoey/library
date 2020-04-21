import store from './store'
import router from './router'
import { getToken } from '@/utils/auth'
import { Message } from 'element-ui'

// no redirect whitelist
const whiteList = ['/login']

router.beforeEach(async(to, from, next) => {

  // test path
  if (to.path != '/login') {
    next()
    return
  }

  // set page title
  const title = 'Library'
  document.title = `${to.meta.title} - ${title}`

  // determine whether the user has logged in
  if (getToken()) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
    } else {
      if (store.getters.name) {
        next()
      } else {
        try {
          // get user info
          await store.dispatch('user/getInfo')
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Error')
          next(`/login?redirect=${to.path}`)
        }
      }
    }
  } else {
    // has no token
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
    }
  }
})
