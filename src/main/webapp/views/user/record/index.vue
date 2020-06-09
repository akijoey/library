<template>
  <el-container>
    <el-table :data="records" highlight-current-row @row-click="handleClick">
      <el-table-column prop="name" label="书名" align="center"></el-table-column>
      <el-table-column prop="borrow" label="借书日期" align="center" sortable>
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span>{{ scope.row.borrow | date }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="return" label="还书期限" align="center" sortable>
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span>{{ scope.row.return | date }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button icon="el-icon-refresh-right" size="mini" @click.stop="handleRenew(scope.row)">续借</el-button>
          <el-button icon="el-icon-position" size="mini" type="primary" @click.stop="handleReturn(scope.row)">还书</el-button>
        </template>
      </el-table-column>
      <detail-dialog :isbn="isbn" :show.sync="show" @return="handleReturn" />
    </el-table>
    <el-pagination layout="total, prev, pager, next, jumper" :page-size="10" :total="total" :current-page.sync="page" @current-change="handleChange" background></el-pagination>
  </el-container>
</template>

<script>
  import { getTable, getTotal, returning, renewing } from '@/api/record'
  import Dialog from '@/components/dialog'
  export default {
    name: 'Record',
    components: {
      'detail-dialog': Dialog
    },
    data() {
      return {
        index: 0,
        records: [],
        day: 7,
        total: 0,
        page: 0,
        isbn: 0,
        show: false
      }
    },
    filters: {
      date(timestamp) {
        return new Date(timestamp).toJSON().substr(0, 10)
      }
    },
    created() {
      this.getRecords(1)
    },
    methods: {
      getRecords(page) {
        getTotal().then(response => {
          const { data } = response
          this.total = data.total
        })
        getTable(page, 10).then(response => {
          const { data } = response
          this.records = data.table
        })
      },
      handleChange(page) {
        this.records = []  // refresh animation
        this.getRecords(page)
      },
      handleClick(row) {
        this.index = row.id
        this.isbn = row.isbn
        this.show = true
      },
      handleRenew(row) {
        this.$msgbox({
          title: '续借天数',
          message: this.$createElement({
            render() { return (  // JSX render component
              <el-radioGroup value={ this.radio } onInput={ this.input }>
                <el-radio label={ 7 }>7天</el-radio>
                <el-radio label={ 15 }>15天</el-radio>
                <el-radio label={ 30 }>30天</el-radio>
              </el-radioGroup>
            )},
            data() { return { radio: 7 }},
            methods: { input(event) {
                this.radio = event
                this.$emit('update', this.radio)
            }}
          }, { on: { update: day => this.day = day}}),
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          center: true,
          callback: action => {
            if (action === 'confirm') {
              const returning = new Date(row.return)
              returning.setDate(returning.getDate() + this.day)
              renewing({
                id: row.id,
                timestamp: returning.getTime()
              }).then(response => {
                const { message } = response
                this.$message.success(message)
                this.getRecords(this.page)
              })
            }
            this.day = 7  // refresh day
          }
        })
      },
      handleReturn(row) {  // button or emit
        const id = row ? row.id : this.index
        returning({ id }).then(response => {
          const { message } = response
          this.$message.success(message)
          this.getRecords(this.page)
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-container {
    height: 639px;
    flex-direction: column;
    .el-table /deep/ .el-table__row {
      @for $i from 1 to 11 {
        &:nth-child(#{$i}) {
          animation: up (.4s + $i * .05);
          span {
            margin-left: 10px;
          }
          .cell {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
        }
      }
    }
    .el-pagination {
      display: flex;
      justify-content: center;
      padding-top: 20px;
      animation: star .8s;
    }
  }
</style>