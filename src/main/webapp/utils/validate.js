// validate utils

export const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else {
    callback()
  }
}

export const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 6) {
    callback(new Error('密码长度不能小于 6 位'))
  } else {
    callback()
  }
}

export const validateOldPassword = function (rule, value, callback) {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 6) {
    callback(new Error('密码长度不能小于 6 位'))
  } else {
    if (this.form.checkPassword !== '') {
      this.$refs.form.validateField('checkPassword')
    }
    callback()
  }
}

export const validateCheckPassword = function (rule, value, callback) {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== this.form.oldPassword) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

export const vaildateNewPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入新密码'))
  } else if (value.length < 6) {
    callback(new Error('密码长度不能小于 6 位'))
  } else {
    callback()
  }
}

export const validatePhone = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入手机号'))
  } else if (!/^[1][3,4,5,7,8,9][0-9]{9}$/.test(value)) {
    callback(new Error('手机号格式不正确'))
  } else {
    callback()
  }
}

export const validateAddress = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入地址'))
  } else {
    callback()
  }
}
