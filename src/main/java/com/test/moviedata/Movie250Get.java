package com.test.moviedata;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.io.SAXReader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.test.DoubanLogin.LoginMainSelenium;

/*
 * 主要对于豆瓣电影前250评分前250位进行爬取，主要获得每个电影的链接
 */
public class Movie250Get {
	private int item=0;
	private ArrayList<String> movieList=new ArrayList<String>();//电影名
	private ArrayList<String> infoList=new ArrayList<String>();//导演以及部分演员名字
	private ArrayList<String> starList=new ArrayList<String>();//该电影的评分
	private ArrayList<String> movieURL=new ArrayList<String>();//电影链接
	LoginMainSelenium ls=new LoginMainSelenium();
	//获取电影细节内容
	public ArrayList<String> getmovie(){
		int i=0;
		String urlPage="https://movie.douban.com/top250";
		do{
			//通过Jsoup获取网页内容	
			String cookieStr=ls.getCookieStr();
			Connection con=Jsoup.connect(urlPage);
			con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
			con.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			con.header("Accept-Encoding", "gzip, deflate, br");
			con.header("Cookie", cookieStr);
			try {
	 			Document doc=con.get();
				Elements titlesGet=doc.getElementsByClass("title");//获取各个电影的中文名字
				Elements staffsGet=doc.getElementsByClass("bd");//获得电影的导演和演员的名字
				Elements starGet=doc.getElementsByClass("rating_num");//获得电影的评分数
				//获取电影链接
				Element content=doc.getElementById("content");
				Elements links=content.select("a");
				for(int m=0;m<links.size();m+=2){
					String linkStr=links.attr("abs:href");
//					if(linkStr.text().contains("?start=")){
//						
//					}		
					System.out.println(linkStr);//???????不会从网页中获取精准获取链接（需要练习）
				}
				//电影标题
				for(Element title:titlesGet){
					if(!title.text().toString().contains("/")){//用于筛选去除外语电影名
						movieList.add(title.text());
					}
				}
				//导演以及演员名字
				int j=0;
				for(Element staff:staffsGet){
					j++;
					if(j>1){
						infoList.add(staff.text());
					}	
				}
				//获得电影的评分数
				for(Element star:starGet){
					starList.add(star.text());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i+=25;
			urlPage="https://movie.douban.com/top250?start="+i+"&filter=";
		}while(i<250);
		
//		for(int j=0;j<movieURL.size();j++){
//			item++;
//			System.out.println("---------"+item+"---------");
////			System.out.println(movieList.get(j));
//			System.out.println(movieURL.get(j));
////			System.out.println(staffList.get(i));
////			System.out.println(starList.get(j));
//			System.out.println("--------------------");
//		}
		return movieURL;
	}
	public static void main(String[] args){		
		new Movie250Get().getmovie();
	}
}
