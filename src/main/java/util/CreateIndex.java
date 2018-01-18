package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import pageModel.FileModel;

public class CreateIndex {
	private static final Logger logger = Logger.getLogger(CreateIndex.class);

	/**
	 * 列出文档存放目录下的所有文件
	 * 
	 * @return
	 * @throws IOException
	 */
	private static List<FileModel> extractFile(String dirPath) throws IOException {
		List<FileModel> list = new ArrayList<FileModel>();
		// File fileDir = new File("src/main/webapp/docfiles");
		File fileDir = new File(dirPath);
		File[] allFiles = fileDir.listFiles();
		for (File f : allFiles) {
			FileModel sf = new FileModel(f.getName(), ParserExtraction(f));
			list.add(sf);
		}
		return list;
	}

	/**
	 * 使用Tika提取文档内容
	 * 
	 * @param file
	 *            需要提取的文档
	 * @return
	 */
	private static String ParserExtraction(File file) {
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase(); // 获取文件类型
		String fileContent = ""; // 接收文档内容
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputStream;
		try {
			// logger.info("=====正在解析<" + file.getName() + ">文件=====");
			inputStream = new FileInputStream(file);
			ParseContext parseContext = new ParseContext();
			switch (fileType) {
			case "doc":
				OfficeParser docParser = new OfficeParser();
				docParser.parse(inputStream, handler, metadata, parseContext);
				fileContent = handler.toString();
				break;
			case "ppt":
				fileContent = getTextFromPPT(File2byte(file));
				break;
			case "pptx":
				fileContent = getTextFromPPTX(File2byte(file));
				break;
			default:
				Parser parser = new AutoDetectParser();
				parser.parse(inputStream, handler, metadata, parseContext);
				fileContent = handler.toString();
				break;
			}
		} catch (FileNotFoundException e) {
			logger.info("文件未找到！");
		} catch (IOException e) {
			logger.info("I/O异常！");
		} catch (SAXException e) {
			logger.info("SAX解析异常！");
		} catch (TikaException e) {
			logger.info("Tika异常！");
		}
		return fileContent;
	}

	/**
	 * 将指定目录下的所有文件添加至索引库
	 * 
	 * @param dirPath
	 *            指定的目录路径
	 * @throws IOException
	 */
	public static final void AppendIndex(String dirPath) throws IOException {
		Analyzer analyzer = new IKAnalyzer6x();
		IndexWriterConfig icw = new IndexWriterConfig(analyzer);
		icw.setOpenMode(OpenMode.CREATE_OR_APPEND);
		Directory dir = null;
		IndexWriter inWriter = null;
		Path indexPath = Paths.get("src/main/webapp/indexdir");
		Path docsPath = Paths.get(dirPath);
		FieldType fieldType = new FieldType();
		fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		fieldType.setStored(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectors(true);
		fieldType.setStoreTermVectorPositions(true);
		fieldType.setStoreTermVectorOffsets(true);
		Date start = new Date();
		if (!Files.isReadable(indexPath)) {
			logger.info(indexPath.toAbsolutePath() + "不存在或不可读，请检查！");
			throw new IOException(indexPath.toAbsolutePath() + "不存在或不可读，请检查！");
		}
		if (!Files.isReadable(docsPath)) {
			logger.info("指定的文档存入目录" + docsPath.toAbsolutePath() + "不存在或不可读，请检查！");
			throw new IOException("指定的文档存入目录" + docsPath.toAbsolutePath() + "不存在或不可读，请检查！");
		}
		dir = FSDirectory.open(indexPath);
		inWriter = new IndexWriter(dir, icw);
		List<FileModel> fileList = null;
		try {
			fileList = extractFile(dirPath);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		// 遍历fileList，建立索引
		for (FileModel f : fileList) {
			Document doc = new Document();
			doc.add(new Field("title", f.getTitle(), fieldType));
			doc.add(new Field("content", f.getContent(), fieldType));
			inWriter.addDocument(doc);
		}
		inWriter.commit();
		inWriter.close();
		dir.close();
		Date end = new Date();
		logger.info("索引文档完成，共耗时：" + (end.getTime() - start.getTime()) + "毫秒.");
	}

	private static String getTextFromPPT(byte[] file) {
		String text = "";
		InputStream fis = null;
		PowerPointExtractor ex = null;
		try {
			fis = new ByteArrayInputStream(file);
			ex = new PowerPointExtractor(fis);
			text = ex.getText();
			ex.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	private static String getTextFromPPTX(byte[] file) {
		InputStream is = null;
		XMLSlideShow slide = null;
		String text = "";
		try {
			is = new ByteArrayInputStream(file);
			slide = new XMLSlideShow(is);
			XSLFPowerPointExtractor extractor = new XSLFPowerPointExtractor(slide);
			text = extractor.getText();
			extractor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	private static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

}
