<template>
  <el-dialog :visible.sync="visible" append-to-body @open="openDialog">
    <img :src="book.cover" />
    <div id="info">
      <p v-for="(value, key) in fields" :key="key">
        <strong>{{ value + ': ' }}</strong>
        <strong>{{ book[key] }}</strong>
      </p>
    </div>
    <div id="summary">
      <h2>内容概要</h2>
      <div v-html="summary"></div>
    </div>
    <div slot="footer">
      <span id="count">数量:<span>{{ book.count }}</span></span>
      <el-button v-if="path" type="primary" @click="handleBorrow">借书</el-button>
      <el-button v-else type="primary" @click="handleReturn">还书</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { getDetail } from '@/api/book'
  import { borrowing } from '@/api/record'
  export default {
    name: 'Dialog',
    props: {
      isbn: {
        type: Number,
        required: true
      },
      show: {
        type: Boolean,
        required: true
      }
    },
    computed: {
      visible: {
        get() {
          return this.show
        },
        set(value) {
          this.$emit('update:show', value)
        }
      },
      summary() {
        let summary = ''
        const sections = this.book.summary.split('\n')
        for (let section of sections) {
          summary += `<p>${section}</p>`
        }
        return summary
      },
      path() {
        return this.$route.path === '/library'
      }
    },
    data() {
      return {
        book: {
          summary: '',
          count: 21
        },
        fields: {
          title: '书名',
          author: '作者',
          press: '出版社',
          date: '出版日期',
          page: '页数',
          category: '分类'
        }
      }
    },
    methods: {
      openDialog() {
        getDetail({
          isbn: this.isbn
        }).then(response => {
          const { data } = response
          this.book = data.detail
        })
      },
      handleBorrow() {
        borrowing({
          isbn: this.isbn
        }).then(response => {
          const { message } = response
          this.$message.success(message)
          this.visible = false
        })
      },
      handleReturn() {
        this.$emit('return')
        this.visible = false
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/styles/math';
  .el-dialog__wrapper ::v-deep .el-dialog {
    width: 700px;
    & > div:nth-child(2) {
      padding: 10px 45px;
      img {
        width: 160px;
        height: 160px * sqrt(2);
        border-radius: 8px;
        box-shadow: 0 5px 12px 0 rgba(7, 17, 27, .6);
        animation: down .6s;
      }
      #info {
        width: 410px;
        font-size: 17px;
        float: right;
        @for $i from 1 to 7 {
          p:nth-child(#{$i}) {
            animation: port (.4s + $i * .05);
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
        }
        p:first-child {
          margin-top: 6px;
        }
        strong:first-child {
          color: #3EA07B;
          margin-right: 15px;
        }
      }
      #summary {
        margin-top: 30px;
        animation: up .5s;
        & /deep/ p {
          font-size: 16px;
          white-space: pre-wrap;
          text-align: justify;
          text-indent: 2em;
          margin: 0;
        }
      }
    }
    #count {
      display: inline-block;
      color: #41B883;
      font-size: 17px;
      font-weight: bold;
      animation: star .7s;
      & > span {
        color: #34495E;
        margin-left: 10px;
      }
      & + button {
        margin: 5px 10px 10px 20px;
        animation: star .6s;
      }
    }
  }
</style>