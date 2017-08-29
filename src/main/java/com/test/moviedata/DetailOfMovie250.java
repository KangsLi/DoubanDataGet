package com.test.moviedata;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @使用从Movie250Get获得的链接
 */
public class DetailOfMovie250 {
	private ArrayList<String> movieURL=new ArrayList<String>();
	public DetailOfMovie250(){
		movieURL=new Movie250Get().getmovie(); 
	}
	/*
	 * 主要获取每一部电影的主要相关信息
	 */
	public void pagefirstInfo(){
		System.out.println("电影数:"+movieURL.size());
		for(int i=0;i<movieURL.size();i++){
			Connection con=Jsoup.connect(movieURL.get(i));
			try {
				Document doc=con.get();
				Element content=doc.getElementById("info");
//				Elements getPos=content.getElementsByClass("p1");
				Elements getName=content.getElementsByClass("attrs");//获取姓名
				for(Element name:getName){
					System.out.println(name.text());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	public static void main(String[] args){
		new DetailOfMovie250().pagefirstInfo();
	}
}
