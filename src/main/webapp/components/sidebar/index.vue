<template>
  <el-menu default-active="0" @select="select">
    <el-menu-item v-for="(side, i) in sidebar" :key="i" :index="i.toString()">
      <icon-font :icon-class="side.icon" />
      <span slot="title">{{ side.title }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: 'SideBar',
    props: {
      sidebar: {
        type: Array,
        required: true
      }
    },
    methods: {
      select(index) {
        this.$emit('select', index)
      }
    }
  }
</script>

<style lang="scss" scoped>
  @for $i from 1 to 11 {
    li:nth-child(#{$i}) {
      animation: up (.5s + $i * .1);
    }
  }
</style>