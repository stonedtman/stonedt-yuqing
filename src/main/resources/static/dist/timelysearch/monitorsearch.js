//左侧菜单变化
$('#sidebarnav li').each(function() {
	if ($(this).data('id') == stype) {
		$(this).siblings().removeClass("comactive")
		$(this).addClass("comactive")
	}
});
//总页数
let totalPage = 10;

let currentPage = pageNoData;

//updatepageHelper(currentPage, totalPage);

gettempleteinfo(stype, website_id);








/**
 * 获取模版信息
 */
function gettempleteinfo(fulltype, website_id) {
	//debugger;
	$.ajax({
		url: "/timelysearch/templete?stype=" + fulltype,
		type: "get",
		success: function(data) {
			let resultdata = '';
			if (data.length > 0) {
				for (let i = 0; i < data.length; i++) {
					let strdata = '';


					if (website_id == 0 && i == 0) {
						strdata = '<span data-engine="' + data[i].id +
							'" class="badge badge-pill badge-info">' + data[i].engine + '</span>';
					} else {
						if (data[i].id == website_id) {
							//strdata = '<input type="radio" value="'+data[i].id+'" name="sex" checked/>'+data[i].engine;
							strdata = '<span data-engine="' + data[i].id +
								'" class="badge badge-pill badge-info">' + data[i].engine + '</span>';
						} else {
							//strdata = '<input type="radio" value="'+data[i].id+'" name="sex"/>'+data[i].engine;
							strdata = '<span data-engine="' + data[i].id +
								'" class="badge badge-pill badge-light">' + data[i].engine + '</span>';
						}


					}




					resultdata += strdata;
				}
			}
			$("#engine").html(resultdata);
			pageHelper(currentPage, totalPage);
			///updatepageHelper(currentPage, totalPage);
			//var sexdata = $("input[name='sex']:checked").val();
			//搜索引擎
			var sexdata = '';
			$('span[data-engine]').each(function() {
				if ($(this).hasClass('badge-info')) {
					sexdata = $(this).data('engine');
				}
			});
			var keywords = $("#searchWord").val();

			//var pageNum = $("input[name='pageNum']:checked").val();

			$('span[data-pagenum]').each(function() {
				if ($(this).data('pagenum') == pageNoData) {
					$(this).removeClass(normal);
					$(this).removeClass(active);
					$(this).addClass(active);
				} else {
					$(this).removeClass(normal);
					$(this).removeClass(active);
					$(this).addClass(normal);
				}

			});

			var pageNum = currentPage;
			// $('#page li').each(function() {
			// 	if ($(this).hasClass('active')) {
			// 		pageNum = $(this).val();
			// 	}
			// });

			loadContent(fulltype,keywords,sexdata,pageNum);



		}
	});
}






//搜索引擎点击事件
$(document).ready(function() {


	$(document).on("click", "#engine span", function() {
		var active = "badge-info";
		var normal = "badge-light";

		var act = $(this).hasClass(active);
		if (act) {} else {
			$(this).siblings().removeClass(active);
			$(this).siblings().addClass(normal);
			$(this).removeClass(normal);
			$(this).addClass(active);
		}

		$('span[data-pagenum]').each(function() {
			if ($(this).data('pagenum') == '1') {
				$(this).removeClass(normal);
				$(this).removeClass(active);
				$(this).addClass(active);
			} else {
				$(this).removeClass(normal);
				$(this).removeClass(active);
				$(this).addClass(normal);
			}

		});



		pageHelper(1, totalPage);
		var stype = '';
		$('#sidebarnav li').each(function() {
			if ($(this).hasClass('comactive')) {
				stype = $(this).data('id');
			}
		});


		var sexdata = '';


		$('span[data-engine]').each(function() {
			if ($(this).hasClass('badge-info')) {
				sexdata = $(this).data('engine');
			}
		});

		var keywords = $("#searchWord").val();

		loadContent(stype, keywords, sexdata, 1);

	})
});








function loadContent(stype, keywords, sexdata, pageNum) {

	$.ajax({
		url: "/timelysearch/data" + "?keyword=" + keywords + "&website_id=" + sexdata + "&pageNoData=" +
			pageNum,
		type: "get",
		beforeSend: function() {
			$("#contentdata").html('');
			//debugger;
			loading("#monitor-content");
			$('#div1').show();
		},
		success: function(data) {
			$('#div1').hide();
			let jsondatadata = JSON.parse(data);

			$("#spider_time").html(jsondatadata.time + "ms");


			let resultresultdata = '';

			let jsondata = JSON.parse(jsondatadata.data);
			if (jsondata.length > 0) {
				if (stype == "5") {

					resultresultdata = '<div class="flex">';
					for (let i = 0; i < jsondata.length; i++) {
						let imgdata = JSON.parse(jsondata[i].videojson);
						strdata = '<div class="item"><a target="_blank" href="' + jsondata[i].url +
							'"><img src="' + imgdata.imglist[0].imgurl + '"/><span >' + jsondata[i].title +
							'</span></a></div><hr>';

						resultresultdata += strdata;
					}

					resultresultdata += '</div>';

				} else if (stype == '4') {

					resultresultdata = '<div class="flex">';
					for (let i = 0; i < jsondata.length; i++) {
						let imgdata = JSON.parse(jsondata[i].videojson);
						strdata = '<div class="item"><a target="_blank" href="' + jsondata[i].url +
							'"><img src="' + imgdata.videoorientationurl + '"/><span >' + jsondata[i]
							.title + '</span></a></div><hr>';

						resultresultdata += strdata;
					}

					resultresultdata += '</div>';
				} else {
					for (let i = 0; i < jsondata.length; i++) {
						let resultdata = jsondata[i];
						if (resultdata.videojson != null) {
							let vediodata = resultdata.videojson;
							console.info("i" + vediodata);


							if (vediodata != '') {



								let imgjsondata = JSON.parse(vediodata);
								if (imgjsondata != null && isEmptyObj(imgjsondata) == true) {

									if (imgjsondata.imglist.length > 0) {

										let imgurl = imgjsondata.imglist[0].imgurl;
										if (imgurl != '') {
											let strdata = '<div class="monitor-right">' +
												'<div class="monitor-content-title"><a target="_blank" title="' +
												resultdata.author + '" style="width: 65%"' +
												'href="' + resultdata.url + '"' +
												'class="link font-bold"><span class="content-logo"' +
												'style="background: url(' + imgurl +
												');"></span><span' +
												'style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
												resultdata.title + '</span></a><span class="sl-date">' +
												resultdata.publish_time + ' </span>' +
												'</div>' +
												'<div class="wb-content-imgbox">' +
												'<div class="wb-left-imgbox"><img src="' + imgurl +
												'" class="img-cover" alt=""></div>' +
												'<div class="wb-right-content">' +
												'<div class="monitor-content-con font-13">' + resultdata
												.abstract + '</div>' +
												'</div>' +
												'</div>' +
												'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
												resultdata.source + '</span></div></div><hr>';

											resultresultdata += strdata;
										} else {
											let strdata = '<div class="monitor-right">' +
												'<div class="monitor-content-title">' +
												'<a target="_blank" title="" style="width: 65%" href="' +
												jsondata[i].url +
												'" class="link font-bold"><span class="content-logo" style="background: url(http://www.njrb.njdaily.cn/favicon.ico);"></span><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
												jsondata[i].title + '</span></a>' +
												'<span class="sl-date">' + jsondata[i].publish_time +
												' </span></div>' +
												'<div class="monitor-content-con font-13">' + jsondata[i]
												.abstract + '</div>' +
												'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
												jsondata[i].source + '</span><span class="link m-r-0">' +
												'</span></div></div><hr>';

											resultresultdata += strdata;


										}





									} else {

										let strdata = '<div class="monitor-right">' +
											'<div class="monitor-content-title">' +
											'<a target="_blank" title="" style="width: 65%" href="' +
											jsondata[i].url +
											'" class="link font-bold"><span class="content-logo" style="background: url(http://www.njrb.njdaily.cn/favicon.ico);"></span><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
											jsondata[i].title + '</span></a>' +
											'<span class="sl-date">' + jsondata[i].publish_time +
											' </span></div>' +
											'<div class="monitor-content-con font-13">' + jsondata[i]
											.abstract + '</div>' +
											'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
											jsondata[i].source + '</span><span class="link m-r-0">' +
											'</span></div></div><hr>';

										resultresultdata += strdata;


									}


								} else {
									let strdata = '<div class="monitor-right">' +
										'<div class="monitor-content-title">' +
										'<a target="_blank" title="" style="width: 65%" href="' + jsondata[
											i].url +
										'" class="link font-bold"><span class="content-logo" style="background: url(http://www.njrb.njdaily.cn/favicon.ico);"></span><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
										jsondata[i].title + '</span></a>' +
										'<span class="sl-date">' + jsondata[i].publish_time +
										' </span></div>' +
										'<div class="monitor-content-con font-13">' + jsondata[i].abstract +
										'</div>' +
										'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
										jsondata[i].source + '</span><span class="link m-r-0">' +
										'</span></div></div><hr>';

									resultresultdata += strdata;
								}


							} else {
								let strdata = '<div class="monitor-right">' +
									'<div class="monitor-content-title">' +
									'<a target="_blank" title="" style="width: 65%" href="' + jsondata[i]
									.url +
									'" class="link font-bold"><span class="content-logo" style="background: url(http://www.njrb.njdaily.cn/favicon.ico);"></span><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
									jsondata[i].title + '</span></a>' +
									'<span class="sl-date">' + jsondata[i].publish_time + ' </span></div>' +
									'<div class="monitor-content-con font-13">' + jsondata[i].abstract +
									'</div>' +
									'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
									jsondata[i].source + '</span><span class="link m-r-0">' +
									'</span></div></div><hr>';

								resultresultdata += strdata;


							}









						} else {

							let strdata = '<div class="monitor-right">' +
								'<div class="monitor-content-title">' +
								'<a target="_blank" title="" style="width: 65%" href="' + jsondata[i].url +
								'" class="link font-bold"><span class="content-logo" style="background: url(http://www.njrb.njdaily.cn/favicon.ico);"></span><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">' +
								jsondata[i].title + '</span></a>' +
								'<span class="sl-date">' + jsondata[i].publish_time + ' </span></div>' +
								'<div class="monitor-content-con font-13">' + jsondata[i].abstract +
								'</div>' +
								'<div class="like-comm m-t-10 font-13"><span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' +
								jsondata[i].source + '</span><span class="link m-r-0">' +
								'</span></div></div><hr>';

							resultresultdata += strdata;

						}





					}
				}
			}
			$("#monitor-content").html(resultresultdata);
		}
	});
	let url = "result?website_id=" + sexdata + "&pageNoData=" + pageNum + "&keyword=" + keywords + "&stype=" + stype +
		"&fulltype=" + stype;
		console.info("11111111"+url);
	setUrl(url);
}


/**
 * 左侧菜单 点击
 * 
 * @param e
 * @returns
 */
$('body').on(
	'click',
	'#sidebarnav li',
	function(e) {
		if (!$(this).hasClass("comactive")) {
			$(this).siblings().removeClass("comactive")
			$(this).addClass("comactive")
			//alert();


			var stype = $(this).attr("data-id");
			gettempleteinfo2(stype, 0);
			
		}
	})


//搜索框点击事件

$('body').on(
	'click',
	'#searchBtn',
	function(e) {
		var keywords = $("#searchWord").val();

		var website_id = '';
		$('span[data-engine]').each(function() {
			if ($(this).hasClass('badge-info')) {
				website_id = $(this).data('engine');
			}
		});


		var stype = '';
		$('#sidebarnav li').each(function() {
			if ($(this).hasClass('comactive')) {
				stype = $(this).data('id');
			}
		});

		loadContent(stype, keywords, website_id, currentPage);

		pageHelper(1, totalPage);

		let url = "result?website_id=" + website_id + "&pageNoData=1&keyword=" + keywords +
			"&stype=" + stype + "&fulltype=" + stype;
			console.info("3333333"+url);
		setUrl(url);
	})




function setUrl(url) {
	history.pushState({
		url: url,
		title: document.title
	}, document.title, url)
}



function isEmptyObj(obj) {
	for (let item in obj) {
		return true
	}
	return false
}




function pageHelper(currentPage, totalPages) {
    $("#page").bootstrapPaginator({
        bootstrapMajorVersion: 3, //版本
        currentPage: currentPage, //当前页数
        numberOfPages: 10, //每次显示页数
        totalPages: totalPages, //总页数
        shouldShowPage: true, //是否显示该按钮
        useBootstrapTooltip: false,
        onPageClicked: function (event, originalEvent, type, page) {
			let keywords = $("#searchWord").val();
			
			var website_id = '';
			$('span[data-engine]').each(function() {
				if ($(this).hasClass('badge-info')) {
					website_id = $(this).data('engine');
				}
			});
			
			
			var stype = '';
			$('#sidebarnav li').each(function() {
				if ($(this).hasClass('comactive')) {
					stype = $(this).data('id');
				}
			});
			loadContent(stype, keywords, website_id, page);
			
			
			
			// let url = "result?website_id=" + website_id + "&pageNoData=" + page + "&keyword=" + keywords +
			// 	"&stype=" + stype + "&fulltype=" + stype;
			// setUrl(url);
			
			}
			
        }
    );
}



//更新分页参数
// function updatepageHelper(currentPage, totalPages) {
//     $("#page").bootstrapPaginator({
//         bootstrapMajorVersion: 3, //版本
//         currentPage: currentPage, //当前页数
//         numberOfPages: 10, //每次显示页数
//         totalPages: totalPages, //总页数
//         shouldShowPage: true, //是否显示该按钮
//         useBootstrapTooltip: false
//         }
//     );
// }


/**
 * 获取模版信息
 */
function gettempleteinfo2(fulltype, website_id) {
	//debugger;
	$.ajax({
		url: "/timelysearch/templete?stype=" + fulltype,
		type: "get",
		//async:false,
		success: function(data) {
			let resultdata = '';
			if (data.length > 0) {
				for (let i = 0; i < data.length; i++) {
					let strdata = '';


					if (website_id == 0 && i == 0) {
						strdata = '<span data-engine="' + data[i].id +
							'" class="badge badge-pill badge-info">' + data[i].engine + '</span>';
					} else {
						if (data[i].id == website_id) {
							//strdata = '<input type="radio" value="'+data[i].id+'" name="sex" checked/>'+data[i].engine;
							strdata = '<span data-engine="' + data[i].id +
								'" class="badge badge-pill badge-info">' + data[i].engine + '</span>';
						} else {
							//strdata = '<input type="radio" value="'+data[i].id+'" name="sex"/>'+data[i].engine;
							strdata = '<span data-engine="' + data[i].id +
								'" class="badge badge-pill badge-light">' + data[i].engine + '</span>';
						}


					}




					resultdata += strdata;
				}
			}
			
			$("#engine").html(resultdata);
			pageHelper(1, totalPage);
			var keywords = $("#searchWord").val();
			
			//var website_id = '';
			$('span[data-engine]').each(function() {
				if ($(this).hasClass('badge-info')) {
					website_id = $(this).data('engine');
				}
			});
			//debugger;
			loadContent(fulltype, keywords, website_id, 1);
			
			let url = "result?website_id=" + website_id + "&pageNoData=1&keyword=" + keywords +
				"&stype=" + fulltype + "&fulltype=" + fulltype;
				console.info("22222222222"+url);
			setUrl(url);
			
			
			
			
		}
	});
	
	
	
	
	
	
}