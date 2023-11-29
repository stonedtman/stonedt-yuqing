import axios from "./request"

// 方案组
export function listSolutionGroupByUserId() {
  return axios({
    url: "/system/listSolutionGroupByUserId",
    method: "post",
  });
}

// 方案
export function listProjectByGroupId(data) {
  return axios({
    url: "/system/listProjectByGroupId",
    method: "post",
    data
  });
}

// 刷新后台数据
export function updateanalysisdata(data) {
  return axios({
    url: "/analysis/updateanalysisdata",
    method: "get",
    params: data
  });
}

// 整体数据
export function getAanlysisByProjectidAndTimeperiod(data) {
  return axios({
    url: "/analysis/opinionScreen/getAanlysisByProjectidAndTimeperiod",
    method: "post",
    data
  });
}

// 最新资讯
export function latestnews(data) {
  return axios({
    url: "/analysis/latestnews",
    method: "post",
    data
  });
}
