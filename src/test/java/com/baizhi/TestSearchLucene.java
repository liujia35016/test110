package com.baizhi;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestSearchLucene {
    @Test
    public void testSearch() throws IOException {
        //创建索引搜索对象
        //索引库路径
        Directory directory = FSDirectory.open(new File("d:/index"));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        //执行搜索
        //搜索的关键词和要搜索的条数
        //默认分词器对document里字段类型为TextField的索引的分词为一个一个字，即我爱你被分为我，爱，你三个索引
        //那么所有的ddcument里的字段为TextField的content里有我字的就形成一个索引数组
        //"我":[docid1,docid2]等等。
        Query query = new TermQuery(new Term("content", "我"));
        //查询所有document里字段id为123的100条数据，而这分词由分词器决定
        TopDocs docs = searcher.search(query, 100);
        //权重排序
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        for (int i = 0; i < scoreDocs.length; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            //在索引库里索引字典字段生成了一个id叫123的docid数组，比如建立索引的时候
            //new StringField("id","123", Field.Store.YES));
            //就把所有含有id的，且id叫123的document编号生成一个docid数组，这样找到docid就找到对应的
            //document存储的数据
            int docid = scoreDoc.doc;
            //拿到对应的document对象
            Document document = searcher.doc(docid);
            //拿到document存储的数据
            String content = document.get("content");
            String author = document.get("author");
            System.out.println(content + "..." + author);
        }
    }
}
