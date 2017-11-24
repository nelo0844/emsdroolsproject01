package com.sap.ems.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.ems.entity.Rule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class EMSServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private EMSService emsService; 

	@Test
	public void testGetMappingFields() {
		
		List<Map> map = emsService.getMappingFields();
			
		logger.info("mapping={}",map);
		
		System.out.println(map);
	}
	
	@Test
	public void testGetAllRules() {
		List<Rule> rules = new ArrayList();
		rules = emsService.getAllRules();
		
		logger.info("rules={}",rules);
		System.out.println(rules);
	}

}
