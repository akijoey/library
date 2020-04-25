<template>
  <el-container>
    <el-header>
      <el-menu router :default-active="$route.path" mode="horizontal">
        <img id="logo" src="/favicon.ico" alt="logo" />
        <span id="title">Library</span>
        <el-menu-item v-for="(nav, i) in navbar" :key="i" :index="nav.path">
          <i :class="nav.icon"></i>
          <span slot="title">{{ nav.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view :key="$route.fullPath" />
    </el-main>
    <el-footer>
      <div id="copyright">©2020 By <a href="https://akijoey.com">AkiJoey</a></div>
      <div id="framework">Powered by <a href="https://spring.io">Spring</a> + <a href="https://vuejs.org">Vue</a></div>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: 'Client',
    data() {
      return {
        navbar: [
          {
            path: '/user',
            title: '个人中心',
            icon: 'el-icon-user'
          },
          {
            path: '/library',
            title: '图书馆',
            icon: 'el-icon-notebook-2'
          },
          {
            path: '/home',
            title: '首页',
            icon: 'el-icon-house'
          }
        ]
      }
    }
  }
</script>

<style lang="scss" scoped>
  $auto: calc((100% - 1000px) / 2);
  header {
    height: 4rem !important;
    padding: .2rem 0;
    box-shadow: 0 0 12px 0 rgba(7, 17, 27, .06);
    animation: down 1s;
    ul {
      padding: 0 $auto;
      #logo {
        padding: .65rem .75rem 0 1.45rem;
        float: left;
      }
      #title {
        font-size: 1.3rem;
        font-weight: bold;
        font-family: Trebuchet MS;
        line-height: 3.6rem;
      }
      .el-menu-item {
        float: right;
      }
    }
  }
  main {
    min-height: calc(100vh - 10rem);
    padding: 20px $auto;
  }
  footer {
    height: 6rem !important;
    padding: 1.5rem 0;
    background-size: 200%;
    background-image: linear-gradient(
      to right,
    #bb313e25, #bb313e25, #d7222925,
    #dd4a1625, #e4761525, #f5c50025,
    #f0e92725, #b1ce2425, #48a93525,
    #03944525, #157c4f25, #176a5825,
    #1b556325, #1d386f25, #1d386f25,
    #20277825, #52266325, #8a244b25
    );
    animation: up 1s, color 5s infinite;
    #copyright, #framework {
      color: #555;
      line-height: 2;
      font-size: 13.5px;
      font-family: Verdana;
      text-align: center;
      a {
        color: #555;
        text-decoration: none;
        transition: .2s;
        &:hover {
          color: #409EFF;
        }
      }
    }
  }
</style>