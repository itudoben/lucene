/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.itudoben.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BitSet;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.RamUsageEstimator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class App {
  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(String[] args) throws Exception {
    runInputStream();
  }

  static void runInputStream() throws Exception {
    InputStream is = new InputStream() {
      int cursor = 0;
      byte[] dataToReadFrom = "ABXY".getBytes();

      @Override
      public int read() throws IOException {
        if (cursor < dataToReadFrom.length) {
          System.out.printf("reading byte #%d,\tbyte: %d,\tlength: %d \n", cursor, dataToReadFrom[cursor], dataToReadFrom.length);
          int v = dataToReadFrom[cursor] & 0xFF;
          cursor += 1;
          return v;
        }
        else {
          return -1;
        }
      }
    };

    boolean assertOn = false;
    // *assigns* true if assertions are on.
    assert assertOn = true;

    System.out.printf("Is assertion enabled: %s\n", assertOn);

    byte[] bytes = new byte[3];
    printScreen(bytes, 0);
    readAndPrintOnScreen(is, bytes, 3);
    readAndPrintOnScreen(is, bytes, 4);
    readAndPrintOnScreen(is, bytes, -1);
  }

  private static void readAndPrintOnScreen(InputStream is, byte[] bytes, final int expectedBytesRead) throws IOException {
    int bytesRead = is.read(bytes);
    assert bytesRead == expectedBytesRead: String.format("Expected %d bytes read and got %d", expectedBytesRead, bytesRead);
    printScreen(bytes, expectedBytesRead);
  }

  private static void printScreen(byte[] bytes, Object expectedBytesRead) {
    for (byte aByte : bytes) {
      System.out.printf("char %s, expected bytes read: %d\n", new String(new byte[]{aByte}), expectedBytesRead);
    }
    System.out.println("\n");
  }

  public static void bitSet() throws Exception {
    DocIdSetIterator ite = DocIdSetIterator.all(5);
    final BitSet bitSet = BitSet.of(ite, 10);
    System.out.println(bitSet.getClass());
    System.out.println(RamUsageEstimator.sizeOf(bitSet));

    System.out.println(VM.current().details());
    System.out.println(ClassLayout.parseClass(BitSet.class).toPrintable());
    System.out.println(ClassLayout.parseInstance(bitSet).toPrintable());
    System.out.println("done");
  }

  public static void someStuff() throws Exception {
    System.out.println(new App().getGreeting());

    Analyzer analyzer = new StandardAnalyzer();

    Path indexPath = Files.createTempDirectory("tempIndex");
    Directory directory = FSDirectory.open(indexPath);
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter iwriter = new IndexWriter(directory, config);
    Document doc = new Document();
    String text = "This is the text to be indexed.";
    doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
    iwriter.addDocument(doc);
    iwriter.close();

    DirectoryReader ireader = null;
    try {
      // Now search the index:
      ireader = DirectoryReader.open(directory);
      IndexSearcher isearcher = new IndexSearcher(ireader);
      // Parse a simple query that searches for "text":
      QueryParser parser = new QueryParser("fieldname", analyzer);
      Query query = parser.parse("text");
      ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
      if (1 != hits.length) {
        throw new IllegalArgumentException("value different than 1: " + hits.length);
      }

      // Iterate through the results:
      for (int i = 0; i < hits.length; i++) {
        Document hitDoc = isearcher.doc(hits[i].doc);
        if (!text.equals(hitDoc.get("fieldname"))) {
          throw new IllegalArgumentException(String.format("value different than {}: {}", text, hits.length));
        }
      }
    }
    finally {
      ireader.close();
      directory.close();
      IOUtils.rm(indexPath);
    }
  }
}
