package com.sap.ems.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.ems.dto.EMSResult;
import com.sap.ems.service.EMSService;

@Controller
@RequestMapping("/drools")
public class EMSController {
	
//	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EMSService emsService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public EMSResult<List<Map>> getMappingFields() {
		
		EMSResult<List<Map>> result;
		
		List<Map> map = emsService.getMappingFields();
		
		result = new EMSResult<List<Map>>(true, map);
		
		return result;
	};
}