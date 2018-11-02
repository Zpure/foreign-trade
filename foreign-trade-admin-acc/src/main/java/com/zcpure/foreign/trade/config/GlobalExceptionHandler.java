package com.zcpure.foreign.trade.config;

import com.zcpure.foreign.trade.BaseCode;
import com.zcpure.foreign.trade.WebJsonBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<WebJsonBean> illegalArgumentExceptionHandler(IllegalArgumentException e) {
		return ResponseEntity.ok().body(new WebJsonBean(BaseCode.FAIL.getIndex(),
			e.getMessage()));
	}

}
