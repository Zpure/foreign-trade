package com.zcpure.foreign.trade;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class FeignClientErrorDecoder implements ErrorDecoder {
	
	private ErrorDecoder DEFAULT_DECODER = new Default();
	
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 400 || response.status() == 500) {
			try {
				throw new HystrixBadRequestException(String.valueOf(response.status()), new IllegalArgumentException(Util.toString(response.body().asReader())));
			} catch (IOException e) {
				throw new HystrixBadRequestException(response.status() + " decode error!", e);
			}
		}
		return DEFAULT_DECODER.decode(methodKey, response);
	}
	
}
