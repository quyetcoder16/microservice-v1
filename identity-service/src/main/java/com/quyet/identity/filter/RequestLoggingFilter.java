package com.quyet.identity.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("RequestLoggingFilter initialized");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Thu thập thông tin request
    String clientIp = getClientIpAddress(httpRequest);
    String method = httpRequest.getMethod();
    String uri = httpRequest.getRequestURI();
    String queryString =
        httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "";
    String fullUrl = uri + queryString;
    String headers = getHeadersInfo(httpRequest);

    // Ghi log
    log.info("Request: IP={}, Method={}, URL={}, Headers={}", clientIp, method, fullUrl, headers);

    // Ghi thời gian bắt đầu để đo latency
    long startTime = System.currentTimeMillis();

    // Tiếp tục chuỗi filter
    filterChain.doFilter(request, response);

    // Ghi log sau khi response trả về (đo latency và status code)
    long duration = System.currentTimeMillis() - startTime;
    log.info(
        "Response: IP={}, URL={}, Status={}, Duration={}ms",
        clientIp,
        fullUrl,
        httpResponse.getStatus(),
        duration);
  }

  // Lấy IP của client, xử lý X-Forwarded-For nếu qua proxy
  private String getClientIpAddress(HttpServletRequest request) {

    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }

  // Lấy thông tin headers
  private String getHeadersInfo(HttpServletRequest request) {
    StringBuilder headers = new StringBuilder("{");
    Collections.list(request.getHeaderNames())
        .forEach(
            name -> headers.append(name).append("=").append(request.getHeader(name)).append(", "));
    if (headers.length() > 1) {
      headers.setLength(headers.length() - 2); // Xóa ", " cuối
    }
    headers.append("}");
    return headers.toString();
  }

  @Override
  public void destroy() {
    log.info("RequestLoggingFilter destroyed");
  }
}
