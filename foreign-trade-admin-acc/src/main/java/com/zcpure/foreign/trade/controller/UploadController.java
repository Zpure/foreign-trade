package com.zcpure.foreign.trade.controller;

import com.zcpure.foreign.trade.WebJsonBean;
import com.zcpure.foreign.trade.utils.DateUtil;
import com.zcpure.foreign.trade.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ethan
 * @create_time 2018/10/22 13:43
 */
@RestController
@RequestMapping("/api/admin/upload")
@CrossOrigin
@Api(value = "上传")
public class UploadController {

	@Value("${path.img}")
	private String imgPath;
	@Value("${path.excel}")
	private String excelPath;
	@Value("${cdn.uri}")
	private String cdnUrl;

	@ApiOperation(value = "上传图片")
	@RequestMapping(value = "/img", method = RequestMethod.POST)
	public WebJsonBean<Map<String, String>> img(@RequestParam("file") MultipartFile file) throws IOException {
		String dateToNumber = DateUtil.getDateToNumber("yyyyMMdd");
		String savePath = imgPath + "/" + dateToNumber;
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {
			saveFile.mkdirs();
		}
		Map<String, String> result = new HashMap<>();
		Validate.isTrue(!file.isEmpty(), "文件为空");
		String originalFilename = file.getOriginalFilename();
		String temp[] = originalFilename.split("\\.");
		String suffixStr = temp[1];
		suffixStr = suffixStr.toLowerCase();
		String key = UUID.randomUUID().toString() + "." + suffixStr;
		FileUtil.SaveFileFromInputStream(file.getInputStream(), savePath, key);
		result.put("uid", key);
		result.put("uri", cdnUrl + "img/" + dateToNumber + "/" + key);
		return WebJsonBean.SUCCESS(result);
	}
}
