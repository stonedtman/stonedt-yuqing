<template>
  <div class="sensitiveinformation">
    <div class="card_title">最新舆情</div>
    <div @click="clickitem">
      <vue-seamless-scroll :data="listData" :class-option="optionSingleHeight" class="seamless-warp">
        <div class="list">
          <div class="item" v-for="(item,index) in listData" :key="index" :data-row="JSON.stringify(item)">
            <div class="info">
              <div class="content-title">
                <div class="title">{{item.title}}</div>
                <span class="emotion zm" v-if="item.emotion==1">正面</span>
                <span class="emotion zx" v-if="item.emotion==2">中性</span>
                <span class="emotion fm" v-if="item.emotion==3">负面</span>
              </div>
              
              <div>
                <span class="time">{{item.publish_time}}</span>
                <span class="source">{{item.source_name}}</span>
              </div>
            </div>
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
        limitMoveNum: 6,
        singleHeight: 61
      }
    }
  },
  methods: {
    changeData(data){
      this.listData = data
    },
    clickitem(e){
      let item = e.target.closest(".item")
      if(item){
        let row = JSON.parse(item.dataset.row)
        this.$emit("jump",row.article_public_id)
      }
    },
  }
}
</script>
<style lang="scss" scoped>
.seamless-warp {
  height: 452px;
  overflow: hidden;
}
.item{
  display: flex;
  margin-top: 12px;
  .source{
    width: 80px;
    text-align: center;
    img{
      width: 32px;
      height: 32px;
      border-radius: 4px;
    }
    .hostName{
      color: #03b4f5;
      font-size: 14px;
    }
  }
  .info{
    width: 100%;
    padding: 4px 16px 3px 0;
    border: 1px solid transparent;
    .content-title{
      width: 100%;
      display: flex;
      align-items: center;
      margin-bottom: 10px;
    }
    .title{
      width: calc(100% - 30px);
      font-size: 16px;
      color: #fff;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .emotion {
      color: #43c0ab;
      padding: 2px 4px;
      border-radius: 3px;
      font-size: 12px !important;
      white-space: nowrap;
      background-color: rgba(245, 249, 255, 0.2);
    }
    .zm {
      color: #36bea6;
    }
    .zx {
      color: #ffbc34;
    }
    .fm {
      color: #f62d51;
    }
    .time{
      display: inline-block;
      color: #03b4f5;
      font-size: 16px;
      margin-right: 12px;
    }
    .source{
      color: #03b4f5;
      font-size: 16px;
    }
  }
}
.item:hover{
  cursor: pointer;
}
</style>