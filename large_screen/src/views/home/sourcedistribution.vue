<template>
  <div class="sourcedistribution">
    <div class="card_title">情感占比</div>
    <div class="container">
      <div id="emotionRateChart"></div>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default {
  data() {
    return{
      
    }
  },
  mounted(){
  },
  methods: {
    changeData(data){
      let arr = []
      data.chart.forEach(item => {
        arr.push({
          name: item[0],
          value: item[1],
        })
      });
      
      this.initData(arr)
    },
    initData(data){
      let myChart = echarts.init(document.getElementById("emotionRateChart"))
      let option = {
        tooltip: {
          trigger: 'item'
        },
        label: {
          color: "#fff"
        },
        color: ["#f0883b","#65c2f9","#2e66f6"],
        series: [
          {
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            data: data
          }
        ]
      }
      option && myChart.setOption(option);
    },
  }
}
</script>
<style lang="scss" scoped>
.sourcedistribution{
  margin-top: 50px;
}
.container {
  position: relative;
  width: 424px;
  height: 340px;
  #emotionRateChart{
    width: 100%;
    height: 100%;
  }
}
</style>