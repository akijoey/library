<template>
  <el-table :data="data" border highlight-current-row @row-click="handleClick">
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
        <el-button icon="el-icon-refresh-right" size="mini" @click.stop="handleBorrow(scope.row)">续借</el-button>
        <el-button icon="el-icon-position" size="mini" type="primary" @click.stop="handleReturn(scope.row)">还书</el-button>
      </template>
    </el-table-column>
    <detail-dialog :index="index" :show.sync="show" />
  </el-table>
</template>

<script>
  import Dialog from '@/components/dialog'
  export default {
    name: 'Record',
    components: {
      'detail-dialog': Dialog
    },
    data() {
      return {
        data: [
          {
            id: 1,
            name: '解忧杂货店',
            borrow: 1580260459981,
            return: 1580260459981
          }, {
            id: 2,
            name: '解忧杂货店',
            borrow: 1566260459981,
            return: 1566260459981
          }, {
            id: 3,
            name: '解忧杂货店',
            borrow: 1528260459981,
            return: 1528260459981
          }, {
            id: 4,
            name: '解忧杂货店',
            borrow: 1525260459981,
            return: 1525260459981
          }, {
            id: 5,
            name: '解忧杂货店',
            borrow: 1550260459981,
            return: 1550260459981
          }, {
            id: 6,
            name: '解忧杂货店',
            borrow: 1538260459981,
            return: 1538260459981
          }, {
            id: 7,
            name: '解忧杂货店',
            borrow: 1545300459981,
            return: 1545300459981
          }, {
            id: 8,
            name: '解忧杂货店',
            borrow: 1542260459981,
            return: 1542260459981
          }, {
            id: 9,
            name: '解忧杂货店',
            borrow: 1540250459981,
            return: 1540250459981
          }
        ],
        index: 0,
        show: false
      }
    },
    filters: {
      date(timestamp) {
        return new Date(timestamp).toJSON().substr(0, 10)
      }
    },
    methods: {
      handleClick(row) {
        this.index = row.id
        this.show = true
      },
      handleBorrow(row) {
        // post row.id to borrow
        console.log(row)
      },
      handleReturn(row) {
        // post row.id to return
        console.log(row)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-table {
    animation: star .8s;
    span {
      margin-left: 10px;
    }
  }
</style>