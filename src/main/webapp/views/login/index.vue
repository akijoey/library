<template>
  <div id="login-container">
    <el-form :model="form" :rules="rules" ref="form" label-position="left">
      <el-form-item>
        <h1>Login Form</h1>
      </el-form-item>
      <el-form-item prop="username">
        <icon-font icon-class="user" />
        <el-input v-model="form.username" placeholder="Username" ref="username" />
      </el-form-item>
      <el-form-item prop="password">
        <icon-font icon-class="password" />
        <el-input :type="password" v-model="form.password" placeholder="Password" ref="password" @keyup.enter.native="handleLogin" />
        <icon-font :icon-class="eye" @click.native="showPassword" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading.login" @click="handleLogin">Login</el-button>
        <el-button type="primary" :loading="loading.register" @click="handleRegister">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { register } from '@/api/user'
  import { validateUsername, validatePassword } from '@/utils/validate'
  export default {
    name: 'Login',
    data() {
      return {
        form: {
          username: '',
          password: ''
        },
        rules: {
          username: [{
            validator: validateUsername,
            trigger: 'blur'
          }],
          password: [{
            validator: validatePassword,
            trigger: 'blur'
          }]
        },
        loading: {
          login: false,
          register: false
        },
        show: false,
        redirect: undefined
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    computed: {
      password() {
        return this.show ? '' : 'password'
      },
      eye() {
        return this.show ? 'eye' : 'eye-slash'
      }
    },
    methods: {
      showPassword() {
        this.show = this.show ? false : true
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin() {
        this.$refs.form.validate(valid => {
          if (valid) {
            this.loading.login = true
            this.$store.dispatch('user/login', this.form).then(response => {
              const { message } = response
              this.loading.login = false
              this.$message.success(message)
              this.$router.push({ path: this.redirect || '/' })
            }).catch(() => this.loading.login = false)
          } else {
            this.$message.error('Format Error')
            return false
          }
        })
      },
      handleRegister() {
        this.$refs.form.validate(valid => {
          if (valid) {
            this.loading.register = true
            register(this.form).then(response => {
              const { message } = response
              this.loading.register = false
              this.$message.success(message)
            }).catch(() => this.loading.register = false)
          } else {
            this.$message.error('Format Error')
            return false
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  // fix: input cursor color
  $cursor: #fff;
  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    #login-container .el-input input {
      color: $cursor;
    }
  }
  #login-container {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-image: linear-gradient(
      125deg, #2c3e50, #16a085
    );
    .el-form {
      width: 450px;
      @for $i from 1 to 5 {
        & > div:nth-child(#{$i}) {
          animation: up (.5s + $i * .1);
          margin-bottom: 25px;
          &:nth-child(2), &:nth-child(3) {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
          }
          h1 {
            color: #eee;
            font-size: 26px;
            font-weight: bold;
            text-align: center;
            margin-top: 0;
          }
          .el-input {
            width: 78%;
            input {
              border: 0;
              color: #fff;
              background: transparent;
              caret-color: $cursor;
              &:-webkit-autofill {
                box-shadow: 0 0 0px 1000px #283443 inset !important;
                -webkit-text-fill-color: $cursor !important;
              }
            }
          }
          svg {
            color: #889aa4;
            margin: 17px 5px 17px 15px;
            vertical-align: middle;
            user-select: none;
            &:nth-child(3) {
              cursor: pointer;
            }
          }
          button {
            width: 47.5%;
            margin-bottom: 50px;
            & + button {
              margin-left: 5% !important;
            }
          }
        }
      }
    }
  }
</style>