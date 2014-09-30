/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.searchengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author hu
 */
public class SolrIndexer {
    
    
    String addr="http://127.0.0.1:8983/solr/";
    SolrServer server=new HttpSolrServer(addr);
    public  synchronized void addDoc(HashMap<String,String> map) throws SolrServerException, IOException{
        SolrInputDocument document = new SolrInputDocument();
        for(Map.Entry<String,String> entry:map.entrySet()){
            document.addField(entry.getKey(),entry.getValue());
        }
        server.add(document);
        server.commit();
    }
    
    public void deleteAll() throws SolrServerException, IOException{
         server.deleteByQuery("*:*");
         server.commit();
        
    }
    
}
