package com.portal.common.exception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.portal.bean.Model;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.common.context.BaseContext;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.BusinessCode;

@ControllerAdvice
public class RestException {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestException.class);

	/**
	 * 对controller增强处理，拦截所有异常
	 * 
	 * @param request
	 * @param exception
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		ResponseData<String> resultResponse = new ResponseData<String>();

		// parameter validate exception
		if (exception instanceof BindException || exception instanceof MethodArgumentNotValidException
				|| exception instanceof UnexpectedTypeException) {
			BindingResult bindingResult = exception instanceof BindException
					? ((BindException) exception).getBindingResult()
					: ((MethodArgumentNotValidException) exception).getBindingResult();

			resultResponse.setCode(BusinessCode.VALIDATE_ERROR.getCode());

			if (bindingResult != null && bindingResult.hasErrors()) {
				List<FieldError> errors = bindingResult.getFieldErrors();
				List<ValidatorFieldError> validators = new ArrayList<ValidatorFieldError>();
				if (!CollectionUtils.isEmpty(errors)) {
					for (FieldError fe : errors) {
						validators.add(new ValidatorFieldError(fe));
					}
				}

				String validatorMsg = new JsonMapper().toJson(validators);
				resultResponse.setData(validatorMsg);
				LOGGER.warn("check param validator error, valid detail {} ..", validatorMsg);
			} else {
				LOGGER.warn("check param validator error, but bindResult is null ..");
			}

			return resultResponse;
		}

		// miss servlet request parameter exception
		if (exception instanceof MissingServletRequestParameterException) {
			resultResponse.setCode(BusinessCode.VALIDATE_ERROR.getCode());
			resultResponse.setMsg("missing servlet request parameter exception..");
			return resultResponse;

		}

		// method not support
		if (exception instanceof HttpRequestMethodNotSupportedException) {
			LOGGER.warn("rest api not support this method, message {}",
					request.getRequestURI() + "|" + request.getMethod());
			resultResponse.setCode(BusinessCode.REST_METHOD_NOT_SUPPORT.getCode());
			resultResponse.setMsg("This method not support..");
			return resultResponse;
		}

		// business exception
		if (exception instanceof BusinessException) {

			LOGGER.warn("ide besiness exception ..");
			BusinessException businessException = (BusinessException) exception;
			resultResponse.setCode(businessException.getCode());
			resultResponse.setMsg(businessException.getMsg());
			return resultResponse;

		}

		// sql or db exception
		if (exception instanceof DataAccessException || exception instanceof SQLException) {

			LOGGER.warn("ide db operator error ..", exception);
			resultResponse.setCode(BusinessCode.BAD_SQL_ERROR.getCode());
			resultResponse.setData(exception.getMessage());
			return resultResponse;
		}

		// unaccept exception
		if (exception instanceof UnacceptException) {

			UnacceptException unacceptException = (UnacceptException) exception;
			LOGGER.error("ide unaccept exception .." + unacceptException.getDesc(), exception);
			resultResponse.setCode(unacceptException.getCode());
			resultResponse.setMsg(unacceptException.getMsg());
			resultResponse.setData(unacceptException.getMessage());

			return resultResponse;

		}

		// unknown exception
		LOGGER.error("ide unknow exception ..", exception);
		resultResponse.setCode(BusinessCode.SERVER_ERROR.getCode());
		resultResponse.setData(exception.getMessage());
		return resultResponse;
	}
}

class ValidatorFieldError extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7055486341802218478L;

	private String field;

	private String message;

	private String code;

	public ValidatorFieldError(FieldError fieldError) {
		this.setCode(fieldError.getDefaultMessage());
		this.setField(fieldError.getField());
		this.setMessage(BaseContext.getMessage(fieldError.getDefaultMessage()));
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}