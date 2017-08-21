package cn.itcast.lucene;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class IndexSearcherTest {
	
	@Test
	public void test(){
		try {
			//指定索引仓库
			IndexReader ir = DirectoryReader.open(FSDirectory.open(new File("F:\\lucene")));
			//创建IndexSearcher对象
			IndexSearcher searcher = new IndexSearcher(ir);
			//创建query检索对象
			TermQuery termQuery = new TermQuery(new Term("fileContent","我"));
			//根据检索对象去索引仓库中找
			TopDocs search = searcher.search(termQuery, 5);
			System.out.println("最好的分数：" + search.getMaxScore());
			System.out.println("总命中的记录数：" + search.totalHits);
			//获取分数文档数组
			ScoreDoc[] docs = search.scoreDocs;
			for (ScoreDoc scoreDoc : docs) {
				System.out.println("得分数："+scoreDoc.score);
				System.out.println("索引号："+scoreDoc.doc);
				//根据索引号去IndexSearcher对象中找到对应的文档对象
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id") + "\t" + doc.get("name") 
				+ "\t" + doc.get("filePath") + "\t" + doc.get("fileContent"));
			}
			//释放资源
			ir.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页检索
	 */
	@Test
	public void test2(){
		try {
			//指定索引仓库
			IndexReader ir = DirectoryReader.open(FSDirectory.open(new File("F:\\lucene")));
			//创建IndexSearcher对象
			IndexSearcher searcher = new IndexSearcher(ir);
			//创建query检索对象
			TermQuery termQuery = new TermQuery(new Term("fileContent","我"));
			//模拟数据
			int page = 2;
			int rows = 3;
			//根据检索对象去索引仓库中找
			TopDocs search = searcher.search(termQuery, page * rows);
			System.out.println("最好的分数：" + search.getMaxScore());
			System.out.println("总命中的记录数：" + search.totalHits);
			//获取分数文档数组
			ScoreDoc[] docs = search.scoreDocs;
			for (int i = (page - 1) * rows ; i < docs.length; i++) {
				System.out.println("得分数："+docs[i].score);
				System.out.println("索引号："+docs[i].doc);
				//根据索引号去IndexSearcher对象中找到对应的文档对象
				Document doc = searcher.doc(docs[i].doc);
				System.out.println(doc.get("id") + "\t" + doc.get("name") 
				+ "\t" + doc.get("filePath") + "\t" + doc.get("fileContent"));
			}
			//释放资源
			ir.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
