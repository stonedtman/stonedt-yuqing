<template>
  <div class="sensitiveinformation">
    <div class="card_title">数据来源分析</div>
    <div class="item th">
      <div>排名</div>
      <div>媒体/账号名称</div>
      <div>媒体类型</div>
      <div>总条数</div>
      <div>敏感舆情</div>
    </div>
    <div @click="clickitem">
      <vue-seamless-scroll :data="listData" :class-option="optionSingleHeight" class="seamless-warp">
        <div class="list">
          <div class="item" v-for="(item,index) in listData" :key="index" :data-row="JSON.stringify(item)">
            <div class="hot">{{index+1}}</div>
            <div class="name">{{item.name}}</div>
            <div class="type">{{item.type}}</div>
            <div class="allnum">{{item.allCount}}</div>
            <div class="sensitiveCount">{{item.sensitiveCount}}</div>
          </div>
        </div>
      </vue-seamless-scroll>
    </div>
    
  </div>
</template>
<script>
export default {
  data() {
    return{
      listData: [],
    }
  },
  computed: {
    optionSingleHeight () {
      return {
        limitMoveNum: 11,
        singleHeight: 32
      }
    }
  },
  methods: {
    changeData(data){
      this.listData = data.all
    },
    clickitem(e){
      let item = e.target.closest(".item")
      if(item){
        let row = JSON.parse(item.dataset.row)
        // window.open(row.url,"_blank")
      }
    },
  }
}
</script>
<style lang="scss" scoped>
.seamless-warp {
  height: 400px;
  overflow: hidden;
}
.item{
  height: 40px;
  line-height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  color: #eee;
  font-size: 16px;
  cursor: pointer;
  .hot {
    min-width: 18px;
    width: auto;
    display: inline-block;
    background: #8eb9f5;
    height: 18px;
    border-radius: 2px;
    line-height: 18px;
    text-align: center;
    font-size: 13px;
    margin-right: 5px;
  }
  .name{
    width: 120px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .type{
    width: 100px;
  }
  .allnum,.sensitiveCount{
    width: 80px;
  }
  .allnum{
    color: #8eb9f5;
  }
  .sensitiveCount{
    color: #f54545;
  }
}
.item:nth-of-type(1){
  .hot{
    background-color: #f54545;
  }
}
.item:nth-of-type(2){
  .hot{
    background-color: #ff8547;
  }
}
.item:nth-of-type(3){
  .hot{
    background-color: #ffac38;
  }
}
.th{
  cursor: default;
  div{
    color: #fff;
  }
  div:nth-of-type(2){
    width: 120px;
  }
  div:nth-of-type(3){
    width: 100px;
  }
  div:nth-of-type(4),div:nth-of-type(5){
    width: 80px;
  }
}
</style>