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
        <div :v-for="(book, i) in books" :key="i">
          
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'Library',
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
        books: [
          {
            title: '解忧杂货店',
            cover: 'https://img3.doubanio.com/view/subject/s/public/s27264181.jpg',
            author: '[日] 东野圭吾',
            press: '南海出版公司',
            date: '2014-5',
            page: 291,
            summary: '现代人内心流失的东西，这家杂货店能帮你找回——&lt;br&gt;&lt;br&gt;僻静的街道旁有一家杂货店，只要写下烦恼投进卷帘门的投信口，第二天就会在店后的牛奶箱里得到回答。&lt;br&gt;&lt;br&gt;因男友身患绝症，年轻女孩静子在爱情与梦想间徘徊；克郎为了音乐梦想离家漂泊，却在现实中寸步难行；少年浩介面临家庭巨变，挣扎在亲情与未来的迷茫中……&lt;br&gt;&lt;br&gt;他们将困惑写成信投进杂货店，随即奇妙的事情竟不断发生。&lt;br&gt;&lt;br&gt;生命中的一次偶然交会，将如何演绎出截然不同的人生？&lt;br&gt;&lt;br&gt;如今回顾写作过程，我发现自己始终在思考一个问题：站在人生的岔路口，人究竟应该怎么做？我希望读者能在掩卷时喃喃自语：我从未读过这样的小说。——东野圭吾',
            cid: '1'
          },
          {
            title: '山茶文具店',
            cover: 'https://img3.doubanio.com/view/subject/s/public/s29707472.jpg',
            author: '[日] 小川糸',
            press: '湖南文艺出版社',
            date: '2018-3',
            page: 320,
            summary: '在镰仓，有一家帮人代笔的文具店，每代店主均由女性担任，只要有委托便会接受，即使是餐厅的菜单也会帮忙。&lt;br&gt;不知不觉间，雨宫鸠子成为了第11代传人，而与外祖母之间的误会，以及开始独自一人活在世上的恐惧，使她充满迷茫。给死去宠物的吊唁信、宣布离婚的公告信、拒绝借钱的回绝信、写给挚友的分手信……&lt;br&gt;一封封代笔信是客人们的写实生活，也是一节节人生的课堂。',
            cid: '1'
          }
        ]
      }
    },
    methods: {

    }
  }
</script>

<style lang="scss" scoped>
  aside {
    width: 150px !important;
    @for $i from 0 to 6 {
      #side-#{$i} {
        animation: star (0.5s + $i * 0.1);
      }
    }
  }
  main {
    display: grid;
  }
</style>