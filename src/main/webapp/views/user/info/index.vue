<template>
  <el-form :model="form" :rules="rules" ref="form" status-icon label-width="70px">
    <el-form-item prop="avatar" id="form-0">
      <img v-if="form.avatar" :src="form.avatar">
      <el-upload ref="upload" action="#" :limit="1" :auto-upload="false" :before-upload="beforeUpdate" :http-request="httpRequest">
        <el-button icon="el-icon-refresh" slot="trigger" @click="handleSelect">选择图片</el-button>
        <el-button icon="el-icon-check" type="primary" @click="handleUpload">点击上传</el-button>
        <p slot="tip">只能上传 jpg / png 文件, 且不超过 500kb</p>
      </el-upload>
    </el-form-item>
    <el-form-item label="用户名" prop="username" id="form-1">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="手机号" prop="phone" id="form-2">
      <el-input v-model="form.phone" />
    </el-form-item>
    <el-form-item id="form-3">
      <el-button icon="el-icon-check" type="primary" @click="handleSubmit">提交</el-button>
      <el-button icon="el-icon-switch-button" @click="handleLogout">退出</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import { logout } from '@/api/user'
  import { resetRouter } from '@/router'
  import { removeToken } from '@/utils/auth'
  import { validateUsername, validatePhone } from '@/utils/validate'
  export default {
    name: 'Info',
    data() {
      return {
        form: {
          avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
          username: 'admin',
          phone: '13751706869'
        },
        rules: {
          username: [{
            validator: validateUsername,
            trigger: 'blur'
          }],
          phone: [{
            validator: validatePhone,
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      beforeUpdate(file) {
        if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
          this.$message.error('上传头像图片只能是 JPG 或 PNG 格式')
          return false
        } else if (file.size > 1024 * 1024) {
          this.$message.error('上传头像图片大小不能超过 1MB')
          return false
        }
        return true
      },
      httpRequest() {
        this.$message.success('上传成功')
      },
      handleSelect() {
        this.$refs.upload.clearFiles()
      },
      handleUpload() {
        this.$refs.upload.submit()
      },
      handleSubmit() {
        this.$refs.form.validate(valid => {
          if (valid) {
            // post username and phone
            console.log('submit')
          } else {
            this.$message.error('Format Error')
            return false
          }
        })
      },
      handleLogout() {
        logout().then(response => {
          const { message } = response.data
          this.$message.success(message)
          removeToken()
          resetRouter()
          this.$router.push({ path: '/' })
          this.$store.commit('user/reset')
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-form {
    @for $i from 0 to 4 {
      #form-#{$i} {
        width: 425px;
        animation: star (.5s + $i * .1);
        &:first-child {
          width: 500px;
          margin-bottom: 30px;
          & > div > div {
            margin-left: 30px;
            float: left;
          }
          img {
            width: 125px;
            height: 125px;
            margin-left: -55px;
            border-radius: 5px;
            float: left;
          }
          p {
            margin: 9px 5px;
            font-size: 10px;
          }
        }
        button {
          margin: 0 20px 0 0;
        }
      }
    }
  }
</style>