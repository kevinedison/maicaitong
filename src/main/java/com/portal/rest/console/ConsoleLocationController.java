package com.portal.rest.console;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.IdRequest;
import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.entity.Location;
import com.portal.bean.request.LocationSaveRequest;
import com.portal.bean.request.LocationSearchRequest;
import com.portal.bean.request.LocationUpdateRequest;
import com.portal.manager.AdminSessionManager;
import com.portal.service.LocationService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ConsoleLocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	AdminSessionManager adminSessionManager;
	
	@RequestMapping(value = "/console/location", method = RequestMethod.POST)
    @ResponseBody
    public Response saveLocation(HttpServletRequest request,@Valid @RequestBody LocationSaveRequest locationSaveRequest) {
        ResponseData<String> response = new ResponseData<String>();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Location location = locationSaveRequest.toEntity(admin);
        locationService.save(location);
        response.setData(location.getId());
        return response;
    }
	
	@RequestMapping(value = "/console/location", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateLocation(HttpServletRequest request,@Valid @RequestBody LocationUpdateRequest locationUpdateRequest) {
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        Location location = locationUpdateRequest.toEntity(admin);
        locationService.updateById(location);
        return response;
    }
	
	@RequestMapping(value = "/console/location", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteLocation(HttpServletRequest request,@Valid IdRequest idRequest) {
        Response response = new Response();
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Location deleteLocation = new Location();
        deleteLocation.setId(idRequest.getId());
        locationService.deleteById(deleteLocation);
        
        return response;
    }
	
	
	@RequestMapping(value = "/console/location/list", method = RequestMethod.GET)
	@ResponseBody
	public Response locationList(LocationSearchRequest searchLocation) {
		ResponseData<PagingData<Location>> response = new ResponseData<PagingData<Location>>();
		response.setData(locationService.selectByIndex(searchLocation.toEntity()));
		return response;
	}
}
