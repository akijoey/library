<template>
  <el-form :model="form" :rules="rules" ref="form" status-icon label-width="70px">
    <el-form-item label="旧密码" prop="password" id="form-0">
      <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPassword" id="form-1">
      <el-input type="password" v-model="form.checkPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword" id="form-2" @keyup.enter.native="handleSubmit">
      <el-input type="password" v-model="form.newPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item id="form-3">
      <el-button icon="el-icon-check" type="primary" @click="handleSubmit">提交</el-button>
      <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  export default {
    name: 'Passwd',
    data() {
      const validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'))
        } else {
          if (this.form.checkPassword !== '') {
            this.$refs.form.validateField('checkPassword')
          }
          callback()
        }
      }
      const validateCheckPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      }
      const vaildateNewPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'))
        } else if (value.length < 6) {
          callback(new Error('密码长度不能小于 6 位'))
        } else {
          callback()
        }
      }
      return {
        form: {
          password: '',
          checkPassword: '',
          newPassword: ''
        },
        rules: {
          password: [{
            validator: validatePassword,
            trigger: 'blur'
          }],
          checkPassword: [{
            validator: validateCheckPassword,
            trigger: 'blur'
          }],
          newPassword: [{
              validator: vaildateNewPassword,
              trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      handleSubmit() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            // post password and new password
            console.log('submit');
          } else {
            console.log('Error Submit');
            return false;
          }
        })
      },
      handleReset() {
        this.$refs.form.resetFields()
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-form {
    @for $i from 0 to 4 {
      #form-#{$i} {
        width: 400px;
        animation: star (.5s + $i * .1);
      }
    }
    button + button {
      margin-left: 20px;
    }
  }
</style>