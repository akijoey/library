<template>
  <el-form :model="form" :rules="rules" ref="form" status-icon label-width="70px">
    <el-form-item>
      <img :src="avatar">
      <el-upload ref="upload" action="#" :limit="1" :auto-upload="false" :before-upload="beforeUpdate" :http-request="httpRequest">
        <el-button icon="el-icon-refresh" slot="trigger" @click="handleSelect">选择图片</el-button>
        <el-button icon="el-icon-check" type="primary" @click="handleUpload">点击上传</el-button>
        <p slot="tip">只能上传 jpg / png 文件, 且不超过 500kb</p>
      </el-upload>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="form.phone" />
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="form.address" />
    </el-form-item>
    <el-form-item>
      <el-button icon="el-icon-check" type="primary" @click="handleSubmit">提交</el-button>
      <el-button icon="el-icon-switch-button" @click="handleLogout">退出</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import { upload, getDetail } from '@/api/user'
  import {
    validateUsername,
    validatePhone,
    validateAddress
  } from '@/utils/validate'
  export default {
    name: 'Info',
    data() {
      return {
        avatar: this.$store.getters.avatar,
        form: {
          username: this.$store.getters.name,
          phone: '',
          address: ''
        },
        rules: {
          username: [{
            validator: validateUsername,
            trigger: 'blur'
          }],
          phone: [{
            validator: validatePhone,
            trigger: 'blur'
          }],
          address: [{
            validator: validateAddress,
            trigger: 'blur'
          }]
        }
      }
    },
    created() {
      getDetail().then(response => {
        const { data } = response
        this.form = data.detail
      })
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
      httpRequest({ file }) {
        const data = new FormData()
        data.append('avatar', file)
        upload(data).then(response => {
          const { message, data } = response
          const { avatar } = data
          this.avatar = avatar
          this.$store.commit('user/setAvatar', avatar)
          this.$message.success(message)
        }).catch(() => this.$refs.upload.clearFiles())
      },
      handleSelect() {
        this.$refs.upload.clearFiles()
      },
      handleUpload() {
        if (this.$refs.upload.uploadFiles.length > 0) {
          this.$refs.upload.submit()
        } else {
          this.$message.error('请选择图片')
        }
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
        this.$store.dispatch('user/logout').then(response => {
          const { message } = response
          this.$message.success(message)
          this.$router.push({ path: '/' })
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-form {
    @for $i from 1 to 6 {
      & > div:nth-child(#{$i}) {
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