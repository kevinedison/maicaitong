package com.portal.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.IdRequest;
import com.portal.bean.PagingData;
import com.portal.bean.RequestPage;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.entity.Location;
import com.portal.bean.request.LocationSearchRequest;
import com.portal.manager.SessionManager;
import com.portal.service.LocationService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	SessionManager sessionManager;

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	@ResponseBody
	public Response location(@Valid IdRequest idRequest) {
		ResponseData<Location> dataResponse = new ResponseData<Location>();
		dataResponse.setData(locationService.selectById(idRequest.getId()));
		return dataResponse;
	}
	
	@RequestMapping(value = "/location/list", method = RequestMethod.GET)
	@ResponseBody
	public Response locationList(LocationSearchRequest searchLocation) {
		ResponseData<PagingData<Location>> response = new ResponseData<PagingData<Location>>();
		response.setData(locationService.selectByIndex(searchLocation.toEntity()));
		return response;
	}
}
