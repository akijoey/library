<template>
  <el-container>
    <el-main>
      <img id="logo" src="@/assets/logo.png" alt="logo" />
    </el-main>
    <el-main>
      <div id="content">
        <h1>Library</h1>
        <p>📚 Books management system. Powered by Spring Boot and Vue.</p>

      </div>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: 'Home',
    data() {
      return {

      }
    },
    methods: {

    }
  }
</script>

<style lang="scss" scoped>
  main {
    width: 50% !important;
    display: flex;
    align-items: center;
    justify-content: center;
    height: calc(100vh - 10rem - 40px);
    img {
      width: 350px;
      height: 350px;
      animation: port 1.2s;
    }
    h1 {
      font-size: 4rem;
    }
    p {
      font-size: 1.7rem;
    }
  }
</style>