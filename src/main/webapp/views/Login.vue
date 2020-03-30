<template>
  <div>
    username:<input type="text" v-model="loginForm.username" placeholder="input username"/>
    password<input type="password" v-model="loginForm.password" placeholder="input password"/>
    <button @click="login">login</button>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        loginForm: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      login() {
        var that = this
        this.$axios.post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(response => {
          if (response.data.code === 200) {
            that.$store.commit('login', that.loginForm)
            var path = this.$route.query.redirect
            this.$router.replace({
              path: path === '/' || path === undefined ? '/index' : path
            })
          }
        })
        .catch(error => {
          console.log(error)
        })
      }
    }
  }
</script>