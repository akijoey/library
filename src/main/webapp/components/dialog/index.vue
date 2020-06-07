<template>
  <el-dialog :visible.sync="visible" append-to-body @open="openDialog">
    <img :src="book.cover" />
    <div id="info">
      <p v-for="(value, key, i) in fields" :key="key" :id="'info-' + i">
        <strong>{{ value + ': ' }}</strong>
        <strong>{{ book[key] }}</strong>
      </p>
    </div>
    <div id="summary">
      <h2>内容概要</h2>
      <div v-html="summary"></div>
    </div>
    <div slot="footer">
      <el-button v-if="path" type="primary" @click="handleBorrow">借书</el-button>
      <el-button v-else type="primary" @click="handleReturn">还书</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { getDetail } from '@/api/book'
  export default {
    name: 'Dialog',
    props: {
      index: {
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
          summary: ''
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
          isbn: this.index
        }).then(response => {
          const { data } = response
          this.book = data.detail
          this.book.category = this.book.category.name
        })
      },
      handleBorrow() {
        // post index to borrow
        this.visible = false
      },
      handleReturn() {
        // post index to return
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
        @for $i from 0 to 6 {
          #info-#{$i} {
            animation: port (.4s + $i * .05);
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
  }
</style>