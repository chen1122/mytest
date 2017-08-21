package cn.itcast.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexWriterTest {
	
	@Test
	public void test1(){
		try {
			//创建索引库
			Directory directory = FSDirectory.open(new File("F:\\lucene"));
			//创建分词器
			IKAnalyzer analyzer = new IKAnalyzer();
			//创建IndexWriter配置对象
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3 ,analyzer);
			//设置打开索引库的方式
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			//创建IndexWriter对象
			IndexWriter indexWriter = new IndexWriter(directory,config);
			//读取文本数据
			File dir = new File("F:\\就业班\\day81Lucene\\资料\\file");
			int cur = 1;
			for (File file : dir.listFiles()) {
				//创建文本对象
				Document doc = new Document();
				
				doc.add(new StringField("id",String.valueOf(cur++),Store.YES));
				doc.add(new TextField("name", file.getName(), Store.YES));
				doc.add(new TextField("filePath", file.getPath(), Store.YES));
				doc.add(new TextField("fileContent",readFile(file), Store.YES));
				doc.add(new IntField("int", cur * 10, Store.YES));
				doc.add(new FloatField("float", cur * 50.5f, Store.YES));
				doc.add(new DoubleField("double", cur * 100.5d, Store.YES));
				doc.add(new LongField("long", cur * 1000l, Store.YES));
				doc.add(new StoredField("stored", "只存储: " + cur));
				//构建索引
				indexWriter.addDocument(doc);
				//提交
				indexWriter.commit();
				
			}
			indexWriter.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile(File file) throws Exception{
		BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file),"gbk"));
		String line = null;
		String res = "";
		while((line = br.readLine()) != null){
			res += line;
		}
		br.close();
		return res;
	}
	
}
