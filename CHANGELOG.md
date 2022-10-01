# v0.0.1 - 2022.09.30

- Initial setup of the project
- Enable assertions in the build.gradle
- Lucene uses ByteStream for all IOs
- Work on Streams, particularly on ByteStream
- byte is a signed value
- conversion from a byte to an **int = byte & 0XFF** to keep the signed value
- Implemented an InputStream
- References
  - https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/InputStream.html#available() 
  - https://lucene.apache.org/core/9_3_0/index.html
  - https://lucene.apache.org/core/9_3_0/core/org/apache/lucene/codecs/lucene92/package-summary.html#package.description
  - Bits and Bytes manip: 
    - https://lucene.apache.org/core/9_3_0/core/org/apache/lucene/util/package-summary.html
  - Examples of streams 
    - https://www.scaler.com/topics/java/java-io-streams/
    - https://pzemtsov.github.io/2015/01/19/on-the-benefits-of-stream-buffering-in-Java.html
