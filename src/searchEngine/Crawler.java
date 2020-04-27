package searchEngine;


import java.io.*;
import java.net.*;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class Crawler {
	
	private String start_url;
	private String site;
	public Crawler(String start_url,String site) {
		this.start_url = start_url;
		this.site = site;
	}
	
	public void start() throws IOException {
		crawl(this.start_url);
	}
	
	/**
	 * 
	 * */
	private void crawl(String url) throws IOException {
		
		String html = getHTML(url);

		
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("a");
		int count=0;
		for (Element e: elements) {
			count++;
			String href = e.attr("href");
			href = processLink(href, url);
			//System.out.println(count+" "+href);
			writeToFiles(href,count);
		}
		System.out.println(processLink("../", url));
		
	}
	
	private String processLink(String link, String base) {
		
		try {
			URL u = new URL(base);
			if (link.startsWith("./")) {
				link = link.substring(2, link.length());
				link = u.getProtocol() + "://" + u.getAuthority() + stripFilename(u.getPath()) + link;
			} else if (link.startsWith("#")) {
				link = base + link;
			} else if (link.startsWith("javascript:")) {
				link = null;
			} else if (link.startsWith("../") || (!link.startsWith("http://") && !link.startsWith("https://"))) {
				link = u.getProtocol() + "://" + u.getAuthority() + stripFilename(u.getPath()) + link;
			}
			return link;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String stripFilename(String path) {
		int pos = path.lastIndexOf("/");
		return pos <= -1 ? path : path.substring(0, pos+1);
	}
	
	private String getHTML(String url) {
		
		URL u;
		try {
			u = new URL(url);
			
			URLConnection conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "BBot/1.0");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			String html = "";
			while ((line = reader.readLine()) != null) {
				html += line + "\n";
			}
			html = html.trim();
			
			return html;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	private void writeToFiles(String link,int count) throws IOException {
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(link).get();
			
			String text = doc.text();
			File file1 = new File("WebPages/"+"text/"+site+""+count+".txt");
			file1.createNewFile();
			PrintWriter writer = new PrintWriter(file1);
			writer.println(link);
			writer.println(text);
			writer.close();
			
			String html = doc.html();
			File file2 = new File("WebPages/"+"html/"+site+""+count+".html");
			file2.createNewFile();
			writer = new PrintWriter(file2);
			//writer.println("link");
			writer.println(html);
			writer.close();
		} 
		catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
}