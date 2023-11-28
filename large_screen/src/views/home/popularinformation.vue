<template>
  <div class="popularinformation">
    <div class="card_title">关注热点事件排名</div>
    <div @click="clickitem">
      <vue-seamless-scroll :data="listData" :class-option="optionSingleHeight" class="seamless-warp">
        <div class="list">
          <div class="item" v-for="(item,index) in listData" :key="index" :data-row="JSON.stringify(item)">
            <div class="info">
              <div v-if="item.emotion==1" class="mood zm">正面</div>
              <div v-if="item.emotion==2" class="mood zx">中性</div>
              <div v-if="item.emotion==3" class="mood fm">负面</div>
              <div class="source">{{item.source_name}}</div>
              <div class="date">{{item.publish_time}}</div>
              <div class="title">{{item.title}}</div>
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
        this.$emit("jump",row.article_public_id)
      }
    },
  }
}
</script>
<style lang="scss" scoped>
.popularinformation{
  position: relative;
  margin-top: 26px;
}
.card_title{
  width: 822px;
  height: 93px;
  line-height: 138px;
  margin-top: 0;
  background-image: url("@/assets/images/bt_b_title.png");
}
.seamless-warp {
  height: 160px;
  overflow: hidden;
}
.list{
  padding: 0 10px;
}
.item{
  width: 100%;
  height: 40px;
  line-height: 40px;
  position: relative;
  display: flex;
  align-items: center;
  .info{
    display: flex;
    .mood{
      font-size: 16px;
      margin-right: 16px;
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
    .source{
      color: #03b4f5;
      font-size: 16px;
      margin-right: 16px;
    }
    .date{
      color: #03b4f5;
      font-size: 15px;
      margin-right: 16px;
    }
    .title{
      flex: 1;
      color: #fff;
      font-size: 16px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}
.item:hover{
  cursor: pointer;
}
</style>