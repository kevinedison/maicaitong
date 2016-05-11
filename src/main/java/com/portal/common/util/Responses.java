package com.portal.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.bean.Response;
import com.portal.common.mapper.JsonMapper;

public class Responses {

	private static final Logger LOGGER = LoggerFactory.getLogger(Responses.class);

	public static final void response(HttpServletResponse response, String content) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(content);
		} catch (IOException e) {
			LOGGER.error("repsonse with json error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static final void response(HttpServletResponse response, Response result) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(new JsonMapper().toJson(result));
		} catch (IOException e) {
			LOGGER.error("repsonse with json error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static final void responseHtml(HttpServletResponse response, Response result) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(new JsonMapper().toJson(result));
		} catch (IOException e) {
			LOGGER.error("repsonse with json error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
