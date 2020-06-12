<template>
  <el-form :model="form" :rules="rules" ref="form" status-icon label-width="70px">
    <el-form-item label="旧密码" prop="password">
      <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPassword">
      <el-input type="password" v-model="form.checkPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword" @keyup.enter.native="handleSubmit">
      <el-input type="password" v-model="form.newPassword" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button icon="el-icon-check" type="primary" @click="handleSubmit">提交</el-button>
      <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import {
    validateOldPassword,
    validateCheckPassword,
    vaildateNewPassword
  } from '@/utils/validate'
  export default {
    name: 'Passwd',
    data() {
      return {
        form: {
          password: '',
          checkPassword: '',
          newPassword: ''
        },
        rules: {
          password: [{
            validator: validateOldPassword.bind(this),
            trigger: 'blur'
          }],
          checkPassword: [{
            validator: validateCheckPassword.bind(this),
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
        this.$refs.form.validate(valid => {
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
    @for $i from 1 to 5 {
      & > div:nth-child(#{$i}) {
        width: 400px;
        animation: star (.5s + $i * .1);
      }
    }
    button + button {
      margin-left: 20px;
    }
  }
</style>