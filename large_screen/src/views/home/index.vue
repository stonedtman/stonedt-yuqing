<template>
  <div class="home" v-loading="loading" element-loading-text="努力加载中">
    <!-- 顶部 -->
    <div class="header">
      <img class="header_bj" src="@/assets/images/header_line.png" />
      <div class="title">舆情数据监测大屏</div>
      <div class="selprogramme">
        <el-select v-model="schemagroup" @change="changeschemagroup" placeholder="请选择方案组">
          <el-option
            v-for="item in schemagroupoptions"
            :key="item.groupId"
            :label="item.groupName"
            :value="item.groupId">
          </el-option>
        </el-select>
        <el-select v-model="programme" @change="changeprogramme" placeholder="请选择方案">
          <el-option
            v-for="item in programmeoptions"
            :key="item.projectId"
            :label="item.projectName"
            :value="item.projectId">
          </el-option>
        </el-select>
      </div>
      <div class="header_right">
        <span class="updateanalysis" @click="refresh">
          <i class="el-icon-refresh-right"></i>刷新
        </span>
        <span>
          <i class="el-icon-time"></i>数据统计更新于：{{updatedOn}}
        </span>
        <el-select v-model="date" @change="changedate" placeholder="请选择方案">
          <el-option
            v-for="item in dateoptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="content">
      <div class="left">
        <publicsentiment ref="publicsentiment" @jump="jump"/>
        <sourcedistribution ref="sourcedistribution"/>
      </div>
      <div class="middle">
        <chart ref="chart"/>
        <popularinformation ref="popularinformation" @jump="jump"/>
      </div>
      <div class="right">
        <sensitiveinformation ref="sensitiveinformation"/>
        <publisherinteraction ref="publisherinteraction"/>
      </div>
    </div>
    
  </div>
</template>
<script>
import publicsentiment from "./publicsentiment";
import sourcedistribution from "./sourcedistribution";
import chart from "./map";
import popularinformation from "./popularinformation";
import sensitiveinformation from "./sensitiveinformation";
import publisherinteraction from "./publisherinteraction";
import {
  listSolutionGroupByUserId,
  listProjectByGroupId,
  updateanalysisdata,
  getAanlysisByProjectidAndTimeperiod,
  latestnews
} from "@/api/home";
export default {
  components: {
    publicsentiment,
    sourcedistribution,
    chart,
    popularinformation,
    sensitiveinformation,
    publisherinteraction,
  },
  data(){
    return{
      loading: false,
      schemagroupoptions: [],
      schemagroup: '',
      programmeoptions: [],
      programme: "",
      dateoptions: [
        {
          value: 1,
          label: "24小时"
        },
        {
          value: 2,
          label: "3天"
        },
        {
          value: 3,
          label: "7天"
        },
        {
          value: 4,
          label: "15天"
        },
      ],
      date: 1,
      updatedOn: "",
    }
  },
  mounted(){
    this.listSolutionGroupByUserId()
  },
  methods: {
    listSolutionGroupByUserId(){
      listSolutionGroupByUserId().then(res=>{
        if(typeof(res.data)=="string"){
          window.location.href = window.location.origin + "/login";
          return
        }
        this.schemagroupoptions = res.data
        this.schemagroup = this.schemagroupoptions[0].groupId
        this.listProjectByGroupId()
      }).catch(err=>{
        // window.location.href = window.location.origin + "/login";
      })
    },
    listProjectByGroupId(){
      let formData = new FormData();
      formData.append("groupId", this.schemagroup)
      listProjectByGroupId(formData).then(res=>{
        this.programmeoptions = res.data
        this.programme = this.programmeoptions[0].projectId
        this.latestnews()
        this.getAanlysisByProjectidAndTimeperiod()
      })
    },
    
    changeschemagroup(){
      this.listProjectByGroupId()
    },
    changeprogramme(){
      this.latestnews()
      this.getAanlysisByProjectidAndTimeperiod()
    },
    latestnews(){
      let formData = new FormData();
      formData.append("projectid", this.programme)
      formData.append("timePeriod", this.date)
      latestnews(formData).then(res=>{
        this.$refs.publicsentiment.changeData(res.data)
      })
    },
    getAanlysisByProjectidAndTimeperiod(){
      this.loading = true
      let formData = new FormData();
      formData.append("projectId", this.programme)
      formData.append("timePeriod", this.date)
      getAanlysisByProjectidAndTimeperiod(formData).then(res=>{
        let data = res.data
        
        if(data.isNeedRefresh){
          this.showNewInfo()
          this.loading = false
        }

        if(data.create_time){
          this.updatedOn = this.getDateDiff(data.create_time)
          // 如果超过48小时，提示用户刷新数据
          let now = new Date().getTime();
          let lasttime = new Date(data.create_time).getTime();
          let diff = now - lasttime;
          if (diff > 172800000) {
            this.showNewInfo()
          }
        }
        
        if(data.emotional_proportion){
          let emotional_proportion = JSON.parse(data.emotional_proportion)
          this.$refs.sourcedistribution.changeData(emotional_proportion)
        }

        if(data.data_overview&&data.hot_spot_ranking){
          let data_overview = JSON.parse(data.data_overview)
          let hot_spot_ranking = JSON.parse(data.hot_spot_ranking)
          this.$refs.chart.changeData(data_overview,hot_spot_ranking)
        }
        
        if(data.hot_event_ranking){
          let hot_event_ranking = JSON.parse(data.hot_event_ranking)
          this.$refs.popularinformation.changeData(hot_event_ranking)
        }
        
        if(data.data_source_analysis){
          let data_source_analysis = JSON.parse(data.data_source_analysis)
          this.$refs.sensitiveinformation.changeData(data_source_analysis)
        }
        
        if(data.event_statistics){
          let event_statistics = JSON.parse(data.event_statistics)
          this.$refs.publisherinteraction.changeData(event_statistics)
        }

        this.loading = false
      }).catch(()=>{
        this.loading = false
      })
    },
    getDateDiff(dateTime) {
      var dateTimeStamp = new Date(dateTime.replace(/-/g, '/')).getTime()
      var minute = 1000 * 60;
      var hour = minute * 60;
      var day = hour * 24;
      var halfamonth = day * 15;
      var month = day * 30;
      var now = new Date().getTime();
      var diffValue = now - dateTimeStamp;
      var result = '';
      if (diffValue < 0) {
        return;
      }
      var monthC = diffValue / month;
      var weekC = diffValue / (7 * day);
      var dayC = diffValue / day;
      var hourC = diffValue / hour;
      var minC = diffValue / minute;

      if (monthC >= 1) {
        result = "" + parseInt(monthC) + "月前";
      } else if (weekC >= 1) {
        result = "" + parseInt(weekC) + "周前";
      } else if (dayC >= 1) {
        result = "" + parseInt(dayC) + "天前";
      } else if (hourC >= 1) {
        result = "" + parseInt(hourC) + "小时前";
      } else if (minC >= 1) {
        result = "" + parseInt(minC) + "分钟前";
      } else {
        result = "刚刚";
      }
      return result
    },
    showNewInfo(){
      this.$swal({
        title: "是否更新监测分析数据？",
        text: "数据已超过48小时未更新，请点击刷新按钮更新数据!",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#36bea6",
        confirmButtonText: "刷新",
        cancelButtonColor: "#6c757d",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false
      }).then((res)=> {
        if (res.value) {
          this.refresh()
        } else {

        }
      });
    },
    refresh(){
      updateanalysisdata({
        groupid: this.schemagroup,
        projectid: this.programme,
      }).then(res=>{
        if(res.data.status==200){
          this.$message.success("定时任务已提交，稍后数据将会更新")
        }else{
          this.$message.error(res.data.message)
        }
      })
    },
    changedate(){
      this.latestnews()
      this.getAanlysisByProjectidAndTimeperiod()
    },
    jump(article_public_id){
      let url = "/monitor/detail/"+article_public_id+"?menu=analysis&groupid="+this.schemagroup+"&projectid="+this.programme
      window.open(url,"_blank")
    },
  }
}
</script>
<style lang="scss">
@import "@/assets/scss/app.scss";
.home{
  width: 1920px;
  height: 1080px;
  overflow: hidden;
  position: relative;
  background-image: url("@/assets/images/bj.png");
  background-size: cover;
}
.header{
  height: 125px;
  position: relative;
  .header_bj{
    width: 100%;
    height: 137px;
    position: absolute;
    top: 0px;
  }
  .title{
    position: relative;
    font-weight: 700;
    font-size: 42px;
    padding-top: 12px;
    text-align: center;
    color: transparent;
    background-image: linear-gradient(to bottom, #FFFFFF, #FFFFFF, #44c6f3);
    -webkit-background-clip: text;
  }
  .selprogramme{
    position: absolute;
    left: 24px;
    top: 40px;
    font-size: 14px;
    color: #FFFFFF;
  }
  .header_right{
    position: absolute;
    right: 24px;
    top: 40px;
    font-size: 14px;
    color: #eee;
    .updateanalysis{
      color: #FFF;
      background-color: #6184e6;
      padding: 2px 5px;
      border-radius: 2px;
      margin-right: 2px;
      cursor: pointer;
    }
  }
  .el-select{
    width: 160px;
    margin-right: 16px;
  }
  .el-input__inner{
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    outline: 0;
    padding: 0 12px;
    color: #FFFFFF;
    border-radius: 3px;
    background: transparent;
    border: 1px solid #2075b8;
  }
  .el-input__icon{
    width: 25px;
    line-height: 40px;
  }
}
.el-select-dropdown{
  border: 1px solid #2075b8;
  border-radius: 4px;
  background-color: #2075b8;
}
.el-select-dropdown__item{
  color: #e0e0e0;
  font-size: 14px;
  padding: 0 20px;
  height: 34px;
  line-height: 34px;
}
.el-select-dropdown__item.selected{
  color: #FFFFFF;
}
.el-popper[x-placement^=bottom] .popper__arrow{
  border-bottom-color: #2075b8;
}
.el-popper[x-placement^=bottom] .popper__arrow::after{
  border-bottom-color: #2075b8;
}
.el-select-dropdown__item.hover, .el-select-dropdown__item:hover{
  background-color: #2075b8;
}
.el-date-editor{
  width: 400px !important;
  height: 24px;
  line-height: 24px;
  background-color: transparent;
  border: 0;
  .el-range-input{
    width: 46%;
    color: #FFFFFF;
    background-color: transparent;
  }
  .el-range-separator{
    color: #FFFFFF;
    line-height: 20px;
  }
  .el-input__icon{
    display: none;
  }
}
.el-range-editor.el-input__inner{
  padding: 0 12px;
}
.el-date-editor .el-range-input{
  font-size: 16px;
}
.content{
  padding: 0 42px 50px 42px;
  display: flex;
  justify-content: space-between;
  .left{
    width: 424px;
  }
  .middle{
    width: 822px;
    margin-left: 82px;
  }
  .right{
    width: 424px;
    margin-left: 82px;
  }
}
.swal2-popup{
  width: 650px;
  font-size: 20px;
}
</style>