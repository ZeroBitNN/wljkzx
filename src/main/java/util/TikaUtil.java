package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

import kmbsModel.FileModel;

public class TikaUtil {
	private static final Logger logger = Logger.getLogger(TikaUtil.class);

	private TikaUtil() {

	}

	/**
	 * 提取指定文档目录下所有文件内容存入FileModel对象列表中
	 * 
	 * @param docsDirPath
	 *            指定文档所在的目录
	 * @return FileModel对象列表
	 * @throws IOException
	 */
	public static final List<FileModel> extractFilesFromDir(String docsDirPath) throws IOException {
		List<FileModel> list = new ArrayList<FileModel>();
		// File fileDir = new File("src/main/webapp/docfiles");
		File fileDir = new File(docsDirPath);
		File[] allFiles = fileDir.listFiles();
		for (File f : allFiles) {
			FileModel sf = new FileModel(f.getName(), ParserExtraction(f));
			list.add(sf);
		}
		return list;
	}

	/**
	 * 提取单个文件的内容保存到FileModel对象里
	 * 
	 * @param file
	 *            文件File对象
	 * @return FileModel对象
	 * @throws IOException
	 */
	public static final FileModel extractFile(File file) throws IOException {
		return new FileModel(file.getName(), ParserExtraction(file));
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
