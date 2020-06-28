package com.cjd.dwptest.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.cjd.dwptest.exception.NoEndpointFoundException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
  
    @Override
    public boolean hasError(final ClientHttpResponse httpResponse) throws IOException {

        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(final ClientHttpResponse httpResponse) throws IOException {
 
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpServerErrorException (httpResponse.getStatusCode());
        }
        
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoEndpointFoundException();
            }
            else {
                throw new HttpClientErrorException(httpResponse.getStatusCode());
            }
        }
    }
}
