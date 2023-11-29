<template>
  <div class="publisherinteraction">
    <div class="card_title">
      <div class="line"></div>
      <div class="text">事件统计</div>
    </div>
    <div id="interaction"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default {
  data() {
    return{
      xAxisData: ["业绩优异","项目规划","竞技比赛","政策影响","历史事件"],
      barData: [233,203,199,173,169],
    }
  },
  methods: {
    changeData(data){
      this.xAxisData = []
      this.barData = []
      data.forEach(item => {
        if(this.xAxisData.length<5){
          this.xAxisData.push(item.name)
          this.barData.push(item.value)
        }
      });
      this.initData()
    },
    initData(){
      var chartDom = document.getElementById('interaction');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '3%',
          bottom: '0%',
          top: "10%",
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.xAxisData,
          axisLabel: {
            color: '#E6F7FF',
            fontSize: 10
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.1)'
            }
          },
          axisTick: {
            show: false
          },
          animation: false
        },
        yAxis: [
          {
            nameTextStyle:{
              color: '#60f6ff',
              fontSize: 12,
            },
            type: 'value',
            axisLabel: {
              textStyle: {
                color: "#FFFFFF"
              }
            },
            splitLine: {
              show: false,
            },
          },
        ],
        series: [
          {
            type: 'bar',
            barWidth: 24,
            data: this.barData,
            color: "#00eeff",
          },
        ]
      };


      option && myChart.setOption(option);
      window.addEventListener('resize', () => {
        myChart.resize()
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.publisherinteraction{
  margin-top: 50px;
}
#interaction{
  width: 100%;
  height: 320px;
}
</style>