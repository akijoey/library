<template>
  <div>
    username:<input type="text" v-model="username" placeholder="input username"/>
    password<input type="password" v-model="password" placeholder="input password"/>
    <button @click="login">login</button>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        username: '',
        password: ''
      }
    },
    methods: {
      login() {
        this.$axios.post('/login', {
          username: this.username,
          password: this.password
        })
        .then(response => {
          if (response.data.code === 200) {
            this.$router.replace({
              path: '/index'
            })
          }
        })
        .catch(error => {
          console.log(error);
        })
      }
    }
  }
</script>