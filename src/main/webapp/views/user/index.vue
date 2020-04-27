<template>
  <div>
    <el-container>
      <el-aside>
        <el-menu default-active="0" @select="select">
          <el-menu-item v-for="(side, i) in sidebar" :key="i" :index="i.toString()" :id="'side-' + i">
            <icon-font :icon-class="side.icon" />
            <span slot="title">{{ side.title }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <user-info v-show="index == 0" />
        <user-passwd v-show="index == 1" />
        <user-record v-show="index == 2" />
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import Info from './info'
  import Passwd from './passwd'
  import Record from './record'
  export default {
    name: 'User',
    components: { 
      'user-info': Info,
      'user-passwd': Passwd,
      'user-record': Record
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
      select(index) {
        this.index = index
      }
    }
  }
</script>

<style lang="scss" scoped>
  aside {
    width: 150px !important;
    padding-top: 10px;
    @for $i from 0 to 3 {
      #side-#{$i} {
        animation: port (.5s + $i * .1);
      }
    }
  }
  main {
    padding: 15px 10px 15px 50px;
  }
</style>