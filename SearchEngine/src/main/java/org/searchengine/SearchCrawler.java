/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.searchengine;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.parser.ParseResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author hu
 */
public class SearchCrawler extends BreadthCrawler{
    
    SolrIndexer solrIndexer=new SolrIndexer();
    
    @Override
    public void visit(Page page){
        String contentType=page.getResponse().getContentType();
        if(!contentType.equals("text/html")){
            return;
        }
        ParseResult parseResult=page.getParseResult();
        String url=page.getUrl();
        String id=url;
        String title=parseResult.getParsedata().getTitle();
        String text=parseResult.getParsetext().getText();
        
        HashMap<String,String> indexMap=new HashMap<String,String>();
        indexMap.put("id", id);
        indexMap.put("url", url);
        indexMap.put("title", title);
        indexMap.put("text", text);
        
        try {
            solrIndexer.addDoc(indexMap);
            System.out.println("Index:"+url+"  "+title);
        } catch (SolrServerException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println(page.getUrl()+" "+contentType);
    }
    
    public static void main(String[] args) throws Exception{
        SearchCrawler crawler=new SearchCrawler();
        crawler.setCrawlPath("/home/hu/data/crawl_search");
        crawler.addSeed("http://www.hfut.edu.cn/ch/");
        
        crawler.addRegex("http://.*hfut.edu.cn/.*");
        crawler.addRegex(("-.*jpg.*"));
        crawler.addRegex(("-.*gif.*"));
        crawler.addRegex(("-.*png.*"));
        crawler.addRegex(("-.*js.*"));
        crawler.addRegex(("-.*css.*"));
        crawler.addRegex(("-.*#.*"));
        
        crawler.setResumable(false);
        crawler.start(5);
        
    }
    
}
