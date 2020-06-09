<template>
  <div>
    <el-container>
      <el-aside>
        <side-menu :sidebar="sidebar" @select="handleSelect" />
      </el-aside>
      <el-main>
        <div v-for="(book, i) in books" :key="i" :id="'book-' + i" @click="handleClick(book.isbn)">
          <img :src="book.cover" alt="cover" />
          <p class="title">{{ book.title }}</p>
          <p>{{ book.author }}</p>
        </div>
      </el-main>
      <detail-dialog :isbn="isbn" :show.sync="show" />
    </el-container>
    <el-pagination layout="total, prev, pager, next, jumper" :page-size="18" :total="total" :current-page.sync="page" @current-change="handleChange" background></el-pagination>
  </div>
</template>

<script>
  import { getSide } from '@/api/category'
  import { getTotal, getList } from '@/api/book'
  import Sidebar from '@/components/sidebar'
  import Dialog from '@/components/dialog'
  export default {
    name: 'Library',
    components: {
      'side-menu': Sidebar,
      'detail-dialog': Dialog
    },
    data() {
      return {
        index: '0',
        sidebar: [
          { title: '全部', icon: 'book' }
        ],
        total: 0,
        page: 0,
        books: [],
        isbn: 0,
        show: false
      }
    },
    created() {
      getSide().then(response => {
        const { data } = response
        data.side.forEach(category => {
          this.sidebar.push(category)
        })
        this.handleChange(1)
      })
    },
    methods: {
      handleChange(page) {
        this.books = [] // refresh animation
        const cid = this.index > 0 ? this.index : ''
        getTotal(cid).then(response => {
          const { data } = response
          this.total = data.total
        })
        getList(page, 18, cid).then(response => {
          const { data } = response
          this.books = data.list
        })
      },
      handleSelect(index) {
        if (this.index !== index) {
          this.index = index
          this.handleChange(1)
          this.page = 1
        }
      },
      handleClick(isbn) {
        this.isbn = isbn
        this.show = true
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/styles/math';
  aside {
    width: 150px !important;
    padding-top: 10px;
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
          white-space: nowrap;
          overflow: hidden;
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