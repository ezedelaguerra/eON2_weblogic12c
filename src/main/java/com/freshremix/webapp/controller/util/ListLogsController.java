package com.freshremix.webapp.controller.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.freshremix.autodownload.PasswordHandler;

public class ListLogsController extends MultiActionController {

	public static class FileObj {
		private String fileId;
		private String filePath;

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

	}

	private PasswordHandler passwordHandler;

	public void setPasswordHandler(PasswordHandler passwordHandler) {
		this.passwordHandler = passwordHandler;
	}

	public ModelAndView listLogs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String errorMessage = null;
		Map<String, Object> model = new HashMap<String, Object>();
		String viewName = "util/jsp/listLog";

		String password = request.getParameter("password");
		if (passwordHandler.isNotValidPassword(password)) {
			model.put("errorList", Arrays.asList(errorMessage));
			return new ModelAndView(viewName, model);
		}

		Set<String> directorySet = getLogDirectories();

		Set<String> files = getFilesFromDirectories(directorySet);

		String selectedFile = request.getParameter("selectedFile");
		if (StringUtils.isNotBlank(selectedFile)) {
			String filePath = new String(Base64.decodeBase64(selectedFile));
			if(isValidSubmittedFilePath(filePath, files)){
				downloadFile(response, filePath);
				return null;
			} else {
				model.put("errorList", Arrays.asList("File not found"));
			}
		}

		List<FileObj> fileList = generateFileObj(files);

		model.put("list", fileList);
		return new ModelAndView(viewName, model);
	}

	private Set<String> getLogDirectories() {
		Set<String> directorySet = new TreeSet<String>();

		Enumeration allAppenders = Logger.getRootLogger().getAllAppenders();

		getLogDirectories(directorySet, allAppenders);
		return directorySet;
	}

	private void downloadFile(HttpServletResponse response, String filePath)
			throws FileNotFoundException, IOException {
		//copy the selected file to the temp dir
		
		File zippedFile = createZipFile(filePath);
		response.setContentType("application/zip");
		response.setContentLength((int) zippedFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ zippedFile.getName() + "\"");
		FileInputStream fin = new FileInputStream(zippedFile);
		FileCopyUtils.copy(fin, response.getOutputStream());
		FileUtils.deleteQuietly(zippedFile);
		System.out.println("Zip File deleted from temp? "+ (!zippedFile.isFile()));
	}


	private boolean isValidSubmittedFilePath(String filePath, Set<String> logFileSet) {
		return logFileSet.contains(filePath); 
	}

	private File createZipFile(String filePath) {

		String tempDir = FileUtils.getTempDirectoryPath();
		String zipFileName = tempDir + File.separatorChar
				+ FilenameUtils.getBaseName(filePath) + ".zip";
		System.out.println("Writing zip to:" + zipFileName);
		File zipFile = new File(zipFileName);

		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			ZipEntry ze = new ZipEntry(FilenameUtils.getName(filePath));
			zos.putNextEntry(ze);

			FileInputStream in = new FileInputStream(filePath);

			int len;
			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}

			in.close();
			zos.closeEntry();
			// remember close it
			zos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return zipFile;
	}

	private List<FileObj> generateFileObj(Set<String> files) {
		List<FileObj> fileList = new ArrayList<FileObj>();
		for (String filePath : files) {
			FileObj fileObj = new FileObj();
			fileObj.setFilePath(FilenameUtils.getName(filePath));
			fileObj.setFileId(Base64.encodeBase64URLSafeString(filePath
					.getBytes()));
			fileList.add(fileObj);
		}

		return fileList;
	}

	private Set<String> getFilesFromDirectories(Set<String> directorySet) {

		Set<String> fileNameList = new TreeSet<String>();
		if (CollectionUtils.isEmpty(directorySet)) {
			return fileNameList;
		}
		for (String dirEntry : directorySet) {
			File dir = FileUtils.getFile(dirEntry);
			Collection<File> listFiles = FileUtils.listFiles(dir, null, false);
			for (File file : listFiles) {
				try {
					fileNameList.add(file.getCanonicalPath());
				} catch (IOException e) {
					continue;
				}
			}
		}
		return fileNameList;
	}

	private void getLogDirectories(Set<String> directorySet,
			Enumeration allAppenders) {
		while (allAppenders.hasMoreElements()) {
			Appender appender = (Appender) allAppenders.nextElement();
			if (appender instanceof FileAppender) {
				FileAppender fileAppender = (FileAppender) appender;
				String filename = fileAppender.getFile();
				StringBuilder sb = new StringBuilder();
				sb.append(FilenameUtils.getPrefix(filename));
				sb.append(FilenameUtils.getPath(filename));
				directorySet.add(sb.toString());
			}
			if (appender instanceof AsyncAppender) {
				AsyncAppender asyncAppender = (AsyncAppender) appender;
				Enumeration subAppenders = asyncAppender.getAllAppenders();
				getLogDirectories(directorySet, subAppenders);
			}
		}
	}

}
