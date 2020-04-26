<template>
  <el-container>
    <el-main>
      <img id="logo" src="@/assets/logo.png" alt="logo" />
    </el-main>
    <el-main>
      <div id="content">
        <h1>Library</h1>
        <p>📚 Books management system.</p>
        <p>Powered by Spring Boot and Vue.</p>
        <el-button type="primary" plain round @click="start">
          <icon-font icon-class="play-circle" />
          <span>GET STARTED</span>
        </el-button>
        <el-button type="primary" round @click="github">
          <icon-font icon-class="github" />
          <span>GITHUB</span>
        </el-button>
      </div>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: 'Home',
    methods: {
      start() {
        this.$router.push('/login')
      },
      github() {
        window.location.href = 'https://github.com/AkiJoey/Library'
      }
    }
  }
</script>

<style lang="scss" scoped>
  main {
    display: flex;
    align-items: center;
    height: calc(100vh - 10rem - 40px);
    img {
      width: 350px;
      animation: up .8s;
    }
    h1 {
      font-size: 5rem;
      font-family: Trebuchet MS;
      margin: 0 0 30px 10px;
      animation: down 1s;
    }
    p {
      font-size: 2rem;
      font-family: Arial;
      line-height: 2.5rem;
      margin: 10px;
      animation: down .8s;
    }
    span, svg {
      font-size: 1rem;
      font-weight: bold;
      margin-left: -4px;
    }
    button {
      margin: 25px 15px 0 10px;
      animation: port .8s;
    }
  }
</style>