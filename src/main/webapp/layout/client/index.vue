<template>
  <el-container>
    <el-header>
      <el-menu router :default-active="$route.path" mode="horizontal">
        <img id="logo" src="/favicon.ico" alt="logo" />
        <span id="title">Library</span>
        <el-menu-item v-for="(nav, i) in navbar" :key="i" :index="nav.path">
          <icon-font :icon-class="nav.meta.icon" />
          <span slot="title">{{ nav.meta.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view :key="$route.fullPath" />
    </el-main>
    <el-footer>
      <div id="copyright">Copyright 2020</div>
      <div id="framework">Powered by <a href="https://spring.io">Spring</a> + <a href="https://vuejs.org">Vue</a></div>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: 'Client',
    computed: {
      navbar() {
        return this.$router.options.routes.find(route => {
          return route.redirect === '/home'
        }).children
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
      125deg, #0BA463 0%, #3CB48E 70%
    );
    animation: up 1s, color 5s infinite;
    #copyright, #framework {
      color: #eee;
      line-height: 2;
      font-size: 13.5px;
      font-family: Verdana;
      text-align: center;
      a {
        color: #eee;
        text-decoration: none;
        transition: .2s;
        &:hover {
          color: #2c3e50;
        }
      }
    }
  }
</style>