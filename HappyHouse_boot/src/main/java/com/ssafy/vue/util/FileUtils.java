package com.ssafy.vue.util;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.vue.dto.BoardFileDto;

@Component
public class FileUtils {
	public List<BoardFileDto> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest)
			throws Exception {
		if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		List<BoardFileDto> fileList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		File abfile = new File(".");
		String rootPath = abfile.getAbsolutePath();
		rootPath = rootPath.substring(0, rootPath.length()-1);
		
		String path = "src/main/resources/static/images/" + current.format(format);
		rootPath += path;
		
		System.out.println("in FileUtils, rootPath :"+rootPath);
		
		File file = new File(rootPath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

		String newFileName, originalFileExtension, contentType;

		while (iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list) {
				if (multipartFile.isEmpty() == false) {
					System.out.println("multipartFile :" + multipartFile);
					contentType = multipartFile.getContentType();
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}

					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setSize(multipartFile.getSize());
					boardFile.setOriginal_name(multipartFile.getOriginalFilename());
					boardFile.setSave_path(rootPath + "/" + newFileName);
					fileList.add(boardFile);

					file = new File(rootPath + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
}
