<template>
  <el-dialog :visible.sync="visible" append-to-body @open="openDialog">
    <el-image :src="book.cover">
      <div slot="error">
        <i class="el-icon-picture-outline"></i>
      </div>
    </el-image>
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
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="visible = false">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
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
      }
    },
    data() {
      return {
        book: {
          title: '解忧杂货店',
          cover: 'https://img3.doubanio.com/view/subject/s/public/s27264181.jpg',
          author: '[日] 东野圭吾',
          press: '南海出版公司',
          date: '2014-5',
          page: 291,
          summary: '现代人内心流失的东西，这家杂货店能帮你找回——\n僻静的街道旁有一家杂货店，只要写下烦恼投进卷帘门的投信口，第二天就会在店后的牛奶箱里得到回答。\n因男友身患绝症，年轻女孩静子在爱情与梦想间徘徊；克郎为了音乐梦想离家漂泊，却在现实中寸步难行；少年浩介面临家庭巨变，挣扎在亲情与未来的迷茫中……\n他们将困惑写成信投进杂货店，随即奇妙的事情竟不断发生。\n生命中的一次偶然交会，将如何演绎出截然不同的人生？\n如今回顾写作过程，我发现自己始终在思考一个问题：站在人生的岔路口，人究竟应该怎么做？我希望读者能在掩卷时喃喃自语：我从未读过这样的小说。\n——东野圭吾',
          category: '文学'
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
        console.log(this.index)
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/styles/math';
  .el-dialog__wrapper /deep/ .el-dialog {
    width: 700px;
    & > div:nth-child(2) {
      padding: 10px 45px;
      .el-image {
        width: 160px;
        height: 160px * sqrt(2);
        border-radius: 8px;
      }
      #info {
        width: 410px;
        font-size: 17px;
        float: right;
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