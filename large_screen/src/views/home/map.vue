<template>
  <div class="map">
    <div class="allnum">
      <div class="item publicsentiment">
        <div class="text">全部信息</div>
        <div class="num">{{publicsentimentData}}</div>
      </div>
      <div class="item nosensitive">
        <div class="text">非消极信息</div>
        <div class="num">{{nosensitiveData}}</div>
      </div>
      <div class="item sensitive">
        <div class="text">消极信息</div>
        <div class="num">{{sensitiveData}}</div>
      </div>
    </div>
    <div id="charts"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
let china = require("@/assets/json/china.json");
export default {
  data(){
    return{
      publicsentimentData: "192",
      nosensitiveData: "160",
      sensitiveData: "32",
      datalist: [],
    }
  },
  methods: {
    changeData(data_overview,hot_spot_ranking){
      this.publicsentimentData = data_overview.all.count
      this.nosensitiveData = data_overview.noSensitive.count
      this.sensitiveData = data_overview.sensitive.count
      this.datalist = hot_spot_ranking.chart
      this.initData()
    },
    initData(){
      echarts.registerMap("china",china)
      let myChart = echarts.init(document.getElementById("charts"))

      let option = {
        // 提示浮窗样式
        tooltip: {
          show: true,
          trigger: "item", 
          backgroundColor: "rgba(0, 0, 0, 0.6);",
          borderColor: "rgba(0, 0, 0, 0.16);",
          textStyle: {
            color: "#fff"
          },
          formatter: function (e) { // 鼠标移入地图
            let str = `<div>城市: ${e.name}</div><div>数值: ${e.value||0}</div>`
            return str
          },
        },
        visualMap: { // 热力图颜色显示
          type: 'piecewise',
          left: '20',
          top: '20',
          itemWidth: 27,
          itemHeight: 15,
          // show: false,
          seriesIndex: [0],
          textStyle: {
            color: '#ffffff',
            fontSize: 14,
          },
          pieces: [
            // {
            //   min: 200,
            //   label: '>200',
            // }, {
            //   max: 200,
            //   min: 100,
            //   label: '100-200',
            // }, {
            //   max: 100,
            //   min: 50,
            //   label: '50-100',
            // }, {
            //   max: 50,
            //   min: 10,
            //   label: '10-50',
            // }, {
            //   max: 10,
            //   min: 1,
            //   label: '1-10',
            // }, {
            //   max: 0,
            //   label: '0',
            // }
            {
              min: 30,
              label: '>30',
            }
          ],
          inRange: {
            // color: ['#aaa', '#b2b2ff', '#9999ff', '#7f7fff', '#6666ff', '#fe475d']
            color: ['#0a2dae']
          },
          outOfRange: {
            color: ['#2075b8']
          }
        },
        geo: {
          map: 'china',
          zoom: 1.2,
          label: {
            show: true,
            color: '#eee',
            emphasis: { // 对应的鼠标悬浮效果
              color: "#eee",
            }
          },
          itemStyle: {
            color: '#2075b8', // 背景
            shadowBlur: 20,
            shadowColor: "#0a2dae",
            borderWidth: '0.05', // 边框宽度
            borderColor: '#0a2dae', // 边框颜色
            emphasis: { // 对应的鼠标悬浮效果
              color: "#fff",
              areaColor: "#0a2dae",
              borderWidth: '0.05', // 边框宽度
              borderColor: '#0a2dae', // 边框颜色
            }
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: [
          { 
            type: 'map',
            geoIndex: 0,
            selectedMode: false,
            data: this.datalist,
          },
        ],
      };
      // 地图注册，第一个参数的名字必须和option.geo.map一致
      myChart.setOption(option);
      // myChart.on("click", function (e) { //取消鼠标移入地图区域高亮
      //   alert("点击了： "+e.name)
      // });
    }
  }
}
</script>
<style lang="scss" scoped>
.allnum{
  display: flex;
  justify-content: space-between;
}
.publicsentiment{
  color: #00fffc;
  .text{
    margin-left: 2px;
    letter-spacing: 6px;
  }
}
.nosensitive{
  color: #fed52f;
  .text{
    letter-spacing: 1.5px;
  }
}
.sensitive{
  color: #239aff;
  .text{
    margin-left: 2px;
    letter-spacing: 6px;
  }
}
.item{
  width: 233px;
  height: 70px;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: space-between;
  background-image: url("@/assets/images/yq_bj.png");
  background-size: cover;
  .text{
    width: 70px;
    line-height: 22px;
    font-size: 18px;
  }
  .num{
    width: 145px;
    font-size: 44px;
  }
}
#charts{
  width: 100%;
  height: 720px;
  margin-top: 40px;
  margin-bottom: -200px;
}
</style>