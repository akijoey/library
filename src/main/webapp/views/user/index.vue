<template>
  <div>
    <el-container>
      <el-aside>
        <el-menu default-active="0">
          <el-menu-item v-for="(side, i) in sidebar" :key="i" :index="i.toString()" :id="'side-' + i">
            <i :class="side.icon"></i>
            <span slot="title">{{ side.title }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'User',
    data() {
      return {
        sidebar: [
          { title: '个人信息', icon: 'el-icon-menu' },
          { title: '修改密码', icon: 'el-icon-menu' },
          { title: '借书记录', icon: 'el-icon-menu' }
        ],
      }
    },
    methods: {

    }
  }
</script>

<style lang="scss" scoped>
  aside {
    width: 150px !important;
    padding-top: 10px;
    @for $i from 0 to 6 {
      #side-#{$i} {
        animation: port (.5s + $i * .1);
      }
    }
  }
</style>