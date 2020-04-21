<template>
  <el-container>
    <el-header>
      <el-menu router default-active="'/index'" mode="horizontal">
        <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name">
          {{ item.title }}
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view :key="key" />
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
          { name: 'home', title: '首页' },
          { name: 'library', title: '图书馆' },
          { name: 'user', title: '个人中心' }
        ]
      }
    },
    methods: {
      
    },
    computed: {
      key() {
        return this.$route.path
      }
    }
  }
</script>

<style lang="scss" scoped>

</style>