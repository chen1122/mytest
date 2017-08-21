package cn.itcast.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class LuceneCRUD {
	
	
	@Test
	public void test(){
		//指定索引仓库
		try {
			FSDirectory directory = FSDirectory.open(new File("F:\\lucene"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
