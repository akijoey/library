<template>
  <el-container>
    <el-header>
      <el-menu router :default-active="$route.path" mode="horizontal">
        <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view :key="$route.fullPath" />
    </el-main>
    <el-footer>
      <div id="footer">
        
      </div>
    </el-footer>
  </el-container>
</template>

<script>
  export default {
    name: 'Layout',
    data() {
      return {
        navList: [
          { path: '/home', title: '首页', icon: 'el-icon-house' },
          { path: '/library', title: '图书馆', icon: 'el-icon-notebook-2' },
          { path: '/user', title: '个人中心', icon: 'el-icon-user' }
        ]
      }
    },
    methods: {
      
    }
  }
</script>

<style lang="scss" scoped>

</style>