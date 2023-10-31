package com.stonedt.intelligence.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.aop.SystemControllerLog;
import com.stonedt.intelligence.entity.FullPolymerization;
import com.stonedt.intelligence.entity.FullType;
import com.stonedt.intelligence.entity.FullWord;
import com.stonedt.intelligence.entity.TimelyPolymerization;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.FullSearchService;
import com.stonedt.intelligence.service.TimelysearchService;
import com.stonedt.intelligence.util.DateUtil;
import com.stonedt.intelligence.util.TxtUtil;
import com.stonedt.intelligence.util.UserUtil;
import com.stonedt.intelligence.vo.FullSearchParam;
import com.stonedt.intelligence.websocket.WebSocketUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全文搜索控制器
 */
@Controller
@RequestMapping(value = "/timelysearch")
public class TimelySearchController {
	
	@Value("${xmlFilePath}")
    private String xmlPath;
	
	
	

    @Autowired
    private FullSearchService fullSearchService;
    
    @Autowired
    private TimelysearchService searchearchService;
    
    

    @Autowired
    UserUtil userUtil;

    /**
     * 全文搜索页面
     */
    @SystemControllerLog(module = "全文搜索", submodule = "全文搜索", type = "全文搜索页面", operation = "search")
    @GetMapping(value = "")
    public String search(Integer full_poly, @RequestParam(defaultValue = "1") Integer full_type, Model model) {
        model.addAttribute("fulltype", full_type);
        model.addAttribute("full_poly", full_poly);
        model.addAttribute("menu", "full_search");
        return "timely_search/full_search";
    }

    /**
     * 全文搜索结果页面
     */
    @GetMapping(value = "/result")
    @SystemControllerLog(module = "全文搜索", submodule = "搜索结果", type = "查询", operation = "result")
    public String result(@RequestParam(value = "menuStyle", required = false, defaultValue = "1") Integer menuStyle,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "50") Integer pageSize,
                         Integer full_poly, String fulltype, @RequestParam(value = "keyword", required = false, defaultValue = "疫情")String keyword, 
                         String sourcename, Integer page, Model model, 
                         HttpServletRequest request,
                         @RequestParam(value = "website_id", required = false, defaultValue = "0") String website_id,
             			@RequestParam(value = "stype", required = false, defaultValue = "1") String stype,
            			@RequestParam(value = "pageNoData", required = false, defaultValue = "1") String pageNoData) {
    	model.addAttribute("searchWord", keyword);//搜索关键词
        model.addAttribute("page", page);
        model.addAttribute("source_name", sourcename);
        model.addAttribute("fulltype", fulltype);//搜索引擎类型
        model.addAttribute("full_poly", full_poly);
        model.addAttribute("menuStyle", menuStyle);
        model.addAttribute("website_id", website_id);
        model.addAttribute("stype", stype);
        model.addAttribute("pageNoData", pageNoData);
        
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("menu", "full_search");

        //将搜索的词存到数据库
        User user = userUtil.getuser(request);
        String user_id = String.valueOf(user.getUser_id());
        String create_time = DateUtil.getNowTime();

        if (!keyword.equals("")) {
            FullWord fullWord = new FullWord();
            fullWord.setUser_id(Long.valueOf(user_id));
            fullWord.setCreate_time(create_time);
            fullWord.setSearch_word(keyword);
            boolean result = fullSearchService.saveFullWord(fullWord);
        }
        return "timely_search/search_result";
    }
    
   

    /**
     * 资讯数据列表
     */
//    @SystemControllerLog(module = "全文搜索", submodule = "资讯列表", type = "查询", operation = "informationList")
//    @GetMapping(value = "/informationList")
//    public @ResponseBody
//    JSONObject informationList(FullSearchParam informationListParam) {
//
//
//    	JSONObject informationList = fullSearchService.informationList(informationListParam);
//
//        return informationList;
//    }



    /**
     * 资讯数据列表
     */
//    @SystemControllerLog(module = "全文搜索", submodule = "资讯列表", type = "查询", operation = "informationList1")
//    @PostMapping(value = "/informationListpost")
//    public @ResponseBody
//    JSONObject informationList1(@RequestBody FullSearchParam informationListParam) {
//
//
//        informationListParam.setMatchType(1);
//        informationListParam.setSortType(1);
//        informationListParam.setMergeType(0);
//
//        JSONObject informationList = fullSearchService.informationListSearch(informationListParam);
//
//        return informationList;
//    }

    /**
     * @param [articleid, groupid, projectid, menu, mv]
     * @return org.springframework.web.servlet.ModelAndView
     * @description: 跳转 资讯文章详情页面 <br>
     * @version: 1.0 <br>
     * @date: 2020/4/13 14:32 <br>
     * @author: huajiancheng <br>
     */
//    @SystemControllerLog(module = "全文搜索", submodule = "资讯文章详情", type = "查询", operation = "thesisnDetail")
//    @GetMapping(value = "/detail/{articleid}")
//    public ModelAndView skiparticle(@PathVariable() String articleid,
//                                    String groupid, String projectid, String relatedWord,
//                                    String menu, String page, ModelAndView mv,
//                                    String menuStyle, String fulltype,
//                                    String fullpoly, String searchWord,String publish_time,
//                                    HttpServletRequest request) {
//        if (StringUtils.isBlank(groupid)) groupid = "";
//        if (StringUtils.isBlank(projectid)) projectid = "";
//        if (StringUtils.isBlank(articleid)) articleid = "";
//        if (StringUtils.isBlank(relatedWord)) relatedWord = "";
//        if (StringUtils.isBlank(publish_time)) publish_time = "";
//        if (StringUtils.isBlank(menu)) menu = "full_search";
//        mv.addObject("menuStyle", menuStyle);
//        mv.addObject("full_poly", fullpoly);
//        mv.addObject("fulltype", fulltype);
//        mv.addObject("searchWord", searchWord);
//        mv.addObject("publish_time", publish_time);
//        mv.addObject("articleid", articleid);
//        mv.addObject("groupid", groupid);
//        mv.addObject("projectid", projectid);
//        mv.addObject("relatedword", relatedWord);
//        mv.addObject("menu", "full_search");
//        mv.setViewName("search/search_detail");
//        return mv;
//    }

    /**
     * 获取一级菜单
     *
     * @return
     */
//    @GetMapping("listFullTypeByFirst")
//    public @ResponseBody
//    List<FullType> listFullTypeByFirst() {
//        List<FullType> listFullTypeByFirst = fullSearchService.listFullTypeByFirst();
//        return listFullTypeByFirst;
//    }

    /**
     * 获取二级菜单
     *
     * @param type_one_id
     * @return
     */
//    @GetMapping("listFullTypeBySecond")
//    public @ResponseBody
//    List<FullType> listFullTypeBysecond(Integer type_one_id) {
//        List<FullType> listFullTypeBysecond = fullSearchService.listFullTypeBysecond(type_one_id);
//        return listFullTypeBysecond;
//    }

    /**
     * 获取三级菜单
     *
     * @param type_one_id
     * @return
     */
//    @GetMapping("listFullTypeByThird")
//    public @ResponseBody
//    List<FullType> listFullTypeBythird(Integer type_two_id) {
//        List<FullType> listFullTypeBythird = fullSearchService.listFullTypeBythird(type_two_id);
//        return listFullTypeBythird;
//    }

    /**
     * 获取聚合分类
     *
     * @return
     */
    @GetMapping("listFullPolymerization")
    public @ResponseBody
    List<TimelyPolymerization> listFullPolymerization() {
        return searchearchService.listFullPolymerization();
    }

//    /**
//     * @return
//     */
//    @GetMapping("getBreadCrumbs")
//    public @ResponseBody
//    JSONObject getBreadCrumbs(Integer menuStyle, Integer fulltype, Integer onlyid, Integer polyid) {
//        return fullSearchService.getBreadCrumbs(menuStyle, fulltype, onlyid, polyid);
//    }
//
//    /**
//     * 获取全文搜索一级分类列表 通过id list
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("listFullTypeOneByIdList")
//    public @ResponseBody
//    List<FullType> listFullTypeOneByIdList(String id) {
//        
//        return fullSearchService.listFullTypeOneByIdList(list);
//    }
//
//
//    /**
//     * @param [request]
//     * @return com.alibaba.fastjson.JSONObject
//     * @description: 获取用户输入的关键词 <br>
//     * @version: 1.0 <br>
//     * @date: 2020/7/13 14:05 <br>
//     * @author: huajiancheng <br>
//     */
//
    @PostMapping(value = "/search")
    @ResponseBody
    public JSONObject getSearchWordById(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        JSONObject paramJson = new JSONObject();
        User user = userUtil.getuser(request);
        String user_id = String.valueOf(user.getUser_id());
        paramJson.put("user_id", user_id);
        response = fullSearchService.getSearchWordById(paramJson);
        return response;
    }
    
    
    
	
	
	

	@GetMapping(value = "/data")
	@ResponseBody
	public String getSpiderData(String keyword,String website_id,String pageNoData) {
		
		
		long startJiebaTime = System.currentTimeMillis();
		String resultdata = "";
//		
//		if(!"1".equals(website_id)) {
			//Map map = templeteService.get(website_id);
			
			File file  = new File(xmlPath+website_id+".txt");
			
			String xml_data = TxtUtil.txt2String(file);
			
			//String data = WebSocketUtils.data(keyword,map.get("xml").toString(),pageNoData);
			
			String data = WebSocketUtils.data(keyword,xml_data,pageNoData);
			
			resultdata = dealdata(data);
			
//			
//		}else {
//		    String source_url = "https://weixin.sogou.com/weixin?oq=&query="+keyword+"&_sug_type_=&sut=281&s_from=input&ri=0&_sug_=n&type=2&sst0=&page="+pageNoData+"&ie=utf8&w=&dr=1";
//			
//			String html = PlaywrightUtil2.getHtml(source_url);
//			
//			
//			
//			Document parse = Jsoup.parse(html);
//			
//			Elements select = parse.select("#main > div.news-box > ul > li");
//			JSONArray jsonArray = new JSONArray();
//			for (int i = 0; i < select.size(); i++) {
//				JSONObject jsonObject = new JSONObject();
//				
//				Element element = select.get(i);
//				String title = element.select("h3").get(0).text();//标题
//				
//				String title_data = (title.indexOf(keyword)!=0)?(title.replaceAll(keyword,"<b class=\"key\" style=\"color:red\">"+keyword+"</b>")):(title);
//				
//				jsonObject.put("title", title_data);
//				
//				String abstract1 = element.select("#sogou_vr_11002601_summary_"+i).get(0).text();//摘要
//				
//				
//				
//				String abstractdata = (abstract1.indexOf(keyword)!=0)?(abstract1.replaceAll(keyword,"<b class=\"key\" style=\"color:red\">"+keyword+"</b>")):(abstract1);
//				jsonObject.put("abstract", abstractdata);
//				
//				
//				
//				
//				
//				String url = element.select("h3").get(0).attr("href");//原始链接
//				
//				jsonObject.put("url", url);
//				String publish_time = element.select("#sogou_vr_11002601_box_"+i+" > div.txt-box > div > span").get(0).text();
//				jsonObject.put("publish_time", publish_time);
//
//				String source = "微信";
//				jsonObject.put("source", source);
//				jsonArray.add(jsonObject);
//			}
//			resultdata = jsonArray.toJSONString();
//		}
		
		long time = (System.currentTimeMillis() - startJiebaTime);//毫秒值
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("time", time);
		jsonObject.put("data", resultdata);
		
		
		return jsonObject.toJSONString();
	}
	
	
	public String dealdata(String data) {
		
		JSONArray resultArray = new JSONArray();
		JSONArray parseArray = JSONArray.parseArray(data);
		for (int i = 0; i < parseArray.size(); i++) {
			JSONObject parseObject = JSONObject.parseObject(parseArray.get(i).toString());
			JSONObject parseObject2 = JSONObject.parseObject(parseObject.getString("message"));
			
			JSONArray parseArray2 = JSONArray.parseArray(parseObject2.getString("outputNames"));
			
			JSONArray valuesArray = JSONArray.parseArray(parseObject2.getString("values"));
			
			 JSONObject jsonObject = new JSONObject();
			for (int j = 0; j < parseArray2.size(); j++) {
				String string = parseArray2.get(j).toString();
//				System.out.println("json数据:"+parseArray2);
//				System.out.println("json数据2:"+valuesArray);
				String values = "";
				if(valuesArray.get(j)!=null) {
					values = valuesArray.get(j).toString();
				}
				jsonObject.put(string, values);
				
			}
			resultArray.add(jsonObject);
		}
		
		return resultArray.toJSONString();
		
	}
	
	
	
	@GetMapping(value = "/index")
	public ModelAndView getSpiderData(ModelAndView mv,
			@RequestParam(value = "website_id", required = false, defaultValue = "3") String website_id,
			@RequestParam(value = "stype", required = false, defaultValue = "1") String stype,
			@RequestParam(value = "keyword", required = false, defaultValue = "疫情") String keyword,
			@RequestParam(value = "pageNoData", required = false, defaultValue = "1") String pageNoData) {
		mv.addObject("website_id", website_id);
		mv.addObject("stype", stype);
		mv.addObject("keyword", keyword);
		mv.addObject("pageNoData", pageNoData);
		
		mv.setViewName("index");
		return mv;
	}
	
	
	@GetMapping(value = "/templete")
	@ResponseBody
	public List templete(String stype) {
		List<Map<String,Object>> list = searchearchService.getTemplete(stype);
		return list;
	}
    
    
    
    
}
