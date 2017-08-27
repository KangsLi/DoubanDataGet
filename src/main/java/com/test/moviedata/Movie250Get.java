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
 * 使用jsoup进行get请求
 */
public class Movie250Get {
	private int item=0;
	private ArrayList<String> movieList=new ArrayList<String>();//电影名
	private ArrayList<String> movieURL=new ArrayList<String>();//电影链接
	LoginMainSelenium ls=new LoginMainSelenium();
	//获取电影细节内容
	public void getmovie(String url){
		//通过Jsoup获取网页内容	
		String cookieStr=ls.getCookieStr();
		Connection con=Jsoup.connect(url);
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
		con.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		con.header("Accept-Encoding", "gzip, deflate, br");
		con.header("Cookie", cookieStr);
		try { 			Document doc=con.get();
//			System.out.println(doc.toString());
//			SAXReader saxReader=new SAXReader();
//			
//			Document document =saxReader.read();//
			Elements titlesGet=doc.getElementsByClass("title");
			Elements urlGet=doc.getElementsByTag("a");
			for(Element urlStr:urlGet){
				if(urlStr.text().toString().contains("subject")){
					movieURL.add(urlStr.text().toString());
				}
			}
			for(Element title:titlesGet){
				if(!title.text().toString().contains("/")){
					movieList.add(title.text());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String element:movieURL){
			item++;
			System.out.println(item+"----->"+element);
		}
		movieList.clear();
	}
	//进行循环获取网页
	public void getURL(){
		int i=0;
		String url="https://movie.douban.com/top250";
		do{
			getmovie(url);
			i+=25;
			url="https://movie.douban.com/top250?start="+i+"&filter=";
			System.out.println(url);
		}while(i<250);
	}
	public static void main(String[] args){		
		new Movie250Get().getURL();
	}
}
