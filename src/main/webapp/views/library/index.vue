<template>
  <div>
    <el-container>
      <el-aside>
        <el-menu default-active="0">
          <el-menu-item v-for="(side, i) in sidebar" :key="i" :index="i.toString()" :id="'side-' + i">
            <i :class="side.icon"></i>
            <span slot="title">{{ side.title }}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <div v-for="(book, i) in books" :key="i" :id="'book-' + i" @click="handleClick(book.isbn)">
          <img :src="book.cover" alt="cover">
          <p class="title">{{ book.title }}</p>
          <p>{{ book.author }}</p>
        </div>
      </el-main>
      <detail-dialog :index="index" :show.sync="show" />
    </el-container>
    <el-pagination layout="prev, pager, next" :page-size="18" :total="total" background @current-change="handleChange"></el-pagination>
  </div>
</template>

<script>
  import { getList, getTotal } from '@/api/book'
  import Dialog from '@/components/dialog'
  export default {
    name: 'Library',
    components: {
      'detail-dialog': Dialog
    },
    data() {
      return {
        sidebar: [
          { title: '文学', icon: 'el-icon-menu' },
          { title: '流行', icon: 'el-icon-menu' },
          { title: '文化', icon: 'el-icon-menu' },
          { title: '生活', icon: 'el-icon-menu' },
          { title: '经管', icon: 'el-icon-menu' },
          { title: '科技', icon: 'el-icon-menu' }
        ],
        total: 0,
        books: [],
        index: 0,
        show: false
      }
    },
    created() {
      getTotal('').then(response => {
        const { data } = response.data
        this.total = data.total
      })
      getList(1, 18, '').then(response => {
        const { data } = response.data
        this.books = data
      })
    },
    methods: {
      handleClick(index) {
        this.index = index
        this.show = true
      },
      handleChange(currentPage) {
        console.log(currentPage)
        this.books = [] // refresh animation
        getList(currentPage, 18, '').then(response => {
        const { data } = response.data
        this.books = data
      })
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/styles/math';
  aside {
    width: 150px !important;
    padding-top: 10px;
    @for $i from 0 to 6 {
      #side-#{$i} {
        animation: port (.5s + $i * .1);
      }
    }
  }
  main {
    display: grid;
    grid-gap: 25px;
    grid-template: repeat(3, 200px) / repeat(auto-fit, 110px);
    height: 100%;
    padding: 15px 10px 15px 50px;
    @for $i from 0 to 18 {
      #book-#{$i} {
        box-shadow: 0 4px 6px 5px rgba(7, 17, 27, .06);
        border-radius: 5px;
        transition: .5s;
        animation: star (.5s + $i * .05);
        &:hover {
          box-shadow: 0 4px 6px 5px rgba(7, 17, 27, .2);
        }
        img {
          width: 110px;
          height: 110px * sqrt(2);
          border-radius: 5px 5px 0 0;
        }
        p {
          font-size: 12px;
          line-height: 18px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          margin: 0 0 0 5px;
        }
        .title {
          font-weight: bold;
          color: #3EA07B;
        }
      }
    }
  }
  .el-pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    padding-top: 20px;
    animation: up 1.2s;
  }
</style>