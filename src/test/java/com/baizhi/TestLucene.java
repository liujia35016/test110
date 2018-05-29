package com.baizhi;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestLucene {
    //创建索引
    @Test
    public void testCreateLucene() throws IOException {
        //索引库路径
        Directory directory = FSDirectory.open(new File("d:/index"));
        //分词器配置
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, new StandardAnalyzer(Version.LUCENE_44));
        //创建索引库索引的写入，需要两个参数，一索引库路径和分瓷器的参数
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //索引库包括两部分，索引和生成的docoument对象数据
        Document document = new Document();
        //document可以存储八种基本数据类型和String,text
        //记住，索引是增进快速查询的，document记住了数据内容，但是查询条件是
        //依据分词器来判定的，而默认的分词器根据document里面的字段类型，对应stringField是不会分词，即
        //stringField生成的关键词就是其内容，对于textField生成的关键词是一个一个的字
        //Field.Store.YES判定是否在doucument里存储索引的数据
        document.add(new StringField("id", "123", Field.Store.YES));
        document.add(new TextField("content", "我爱你", Field.Store.YES));
        document.add(new StringField("author", "海明威", Field.Store.YES));
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
    }
}
