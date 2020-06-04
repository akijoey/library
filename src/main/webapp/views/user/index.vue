<template>
  <el-container>
    <el-aside>
      <side-menu :sidebar="sidebar" @select="handleSelect" />
    </el-aside>
    <el-main>
      <user-info v-show="index == 0" />
      <user-passwd v-show="index == 1" />
      <user-record v-show="index == 2" />
    </el-main>
  </el-container>
</template>

<script>
  import Info from './info'
  import Passwd from './passwd'
  import Record from './record'
  import Sidebar from '@/components/sidebar'
  export default {
    name: 'User',
    components: {
      'user-info': Info,
      'user-passwd': Passwd,
      'user-record': Record,
      'side-menu': Sidebar
    },
    data() {
      return {
        index: 0,
        sidebar: [
          { title: '个人信息', icon: 'vcard-o' },
          { title: '修改密码', icon: 'wrench' },
          { title: '借书记录', icon: 'table' }
        ]
      }
    },
    methods: {
      handleSelect(index) {
        this.index = index
      }
    }
  }
</script>

<style lang="scss" scoped>
  aside {
    width: 150px !important;
    padding-top: 10px;
  }
  main {
    padding: 15px 10px 15px 50px;
  }
</style>