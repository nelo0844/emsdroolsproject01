package com.sap.ems.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.ems.dao.RuleDao;
import com.sap.ems.dto.RuleDto;
import com.sap.ems.entity.Entitlement;
import com.sap.ems.entity.Rule;
import com.sap.ems.entity.SalesOrder;
import com.sap.ems.entity.SalesOrderItem;
import com.sap.ems.service.EMSService;

@Service
public class EMSServiceImpl implements EMSService {

	// Logger logger = LoggerFactory.getLogger(this.getClass());
	String key;
	
	@Autowired
	private RuleDao ruleDao;

	public List<Map> getMappingFields() {

//		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> so = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> soItem = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> emsEntitlement = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, Object> field = new LinkedHashMap<String, Object>();
		
		List<Map> map = new ArrayList<Map>();
		
		List<Map> SoPropertyAndType = new ArrayList<Map>();
		
		List<Map> itemPropertyAndType = new ArrayList<Map>();
		
		List<Map> entitlementPropertyAndType = new ArrayList<Map>();
		
//		LinkedHashMap<String, Object> field = new LinkedHashMap<String, Object>();

		Field[] soHeads = SalesOrder.class.getDeclaredFields();

		Field[] soItems = SalesOrderItem.class.getDeclaredFields();

		Field[] entitlements = Entitlement.class.getDeclaredFields();

		// SO
		for (Field soHead : soHeads) {
			switch (soHead.getName()) {
			case "soId":
				SoPropertyAndType = setPropertyAndType(SoPropertyAndType, soHead, "SO ID");
				break;
			case "customerId":
				SoPropertyAndType = setPropertyAndType(SoPropertyAndType, soHead, "Customer ID");
				break;
			case "referanceDocumentNo":
				SoPropertyAndType = setPropertyAndType(SoPropertyAndType, soHead, "Referance Document Number");
				break;
			case "documentNumber":
				SoPropertyAndType = setPropertyAndType(SoPropertyAndType, soHead, "Document Number");
				break;
			case "parentId":
				SoPropertyAndType = setPropertyAndType(SoPropertyAndType, soHead, "Parent ID");
				break;
			default:
				break;
			}
		}

		// SO Item
		for (Field item : soItems) {
			switch (item.getName()) {
			case "itemId":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Item ID");
				break;
			case "productId":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Product ID");
				break;
			case "productName":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Product Name");
				break;
			case "itemValidFrom":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Valid From");
				break;
			case "itemValidTo":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Valid To");
				break;
			case "quantity":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Quantity");
				break;
			case "documentNumber":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Document Number");
				break;
			case "cardinality":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Cardinality");
				break;
			case "parentId":
				itemPropertyAndType = setPropertyAndType(itemPropertyAndType, item, "Item Parent ID");
				break;
			default:
				break;
			}
		}

		// Entitlement
		for (Field entitlement : entitlements) {
			switch (entitlement.getName()) {
			case "guid":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Guid");
				break;
			case "reason":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Reason");
				break;
			case "memory":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Memory");
				break;
			case "C000000003":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "C000000003");
				break;
			case "sourceSystem":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Source System");
				break;
			case "refItemNo":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Referance Item Number");
				break;
			case "channel":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Channel");
				break;
			case "validFrom":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Valid From");
				break;
			case "validTo":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Valid To");
				break;
			case "storage":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Storage");
				break;
			case "createdOn":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "created On");
				break;
			case "changedOn":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "changed On");
				break;
			case "generationMethod":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Generation Method");
				break;
			case "uom":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "UoM");
				break;
			case "createdBy":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Created By");
				break;
			case "changedBy":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Changed By");
				break;
			case "customerId":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Customer ID");
				break;
			case "simulationEntitlementNo":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Simulation Entitlement Number");
				break;
			case "performanceAndGoals":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Performance And Goals");
				break;
			case "refDocNo":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Referance DocumentNumber");
				break;
			case "quantity":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Quantity");
				break;
			case "comments":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Comments");
				break;
			case "distributorID":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Distributor ID");
				break;
			case "refDocType":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Referance Document Type");
				break;
			case "entitlementType":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Entitlement Type");
				break;
			case "version":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Version");
				break;
			case "entitlementNo":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Entitlement Number");
				break;
			case "performanceAndReward":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Performance And Reward");
				break;
			case "modules":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Modules");
				break;
			case "advancedEdition":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Advanced Edition");
				break;
			case "entitlementMasterId":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Entitlement Master ID");
				break;
			case "component":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Component");
				break;
			case "tenantURL":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Tenant URL");
				break;
			case "namedUsers":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Named Users");
				break;
			case "CLOUDSTORAGE":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Cloud Storage");
				break;
			case "persistence":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Persistence");
				break;
			case "status":
				entitlementPropertyAndType = setPropertyAndType(entitlementPropertyAndType, entitlement, "Status");
				break;
			default:
				break;
			}
		}

		soItem.put("property", "Sales Order Item");
		soItem.put("content", itemPropertyAndType);
		SoPropertyAndType.add(soItem);
		so.put("propertyId", 0);
		so.put("propertyName", "Sales Order");
		so.put("content", SoPropertyAndType);
		map.add(so);
		
		emsEntitlement.put("propertyId", 2);
		emsEntitlement.put("propertyName", "Entitlement");
		emsEntitlement.put("content", entitlementPropertyAndType);
		map.add(emsEntitlement);

		return map;

	}

	private List<Map> setPropertyAndType(List<Map> propertyAndType, Field field, String property) {
		LinkedHashMap<String, Object> value = new LinkedHashMap<String, Object>();
		value.put("property", property);
		String type = field.getType().toString();
		type = type.replace("class ", "");
		value.put("type", type);
		propertyAndType.add(value);
		return propertyAndType;
	}
	
	public List<Rule> getAllRules() {
		List<Rule> rules = new ArrayList();
		rules = ruleDao.queryAll();
		return rules;
	}

	public RuleDto getRuleById(long ruleId) {
		List when = new ArrayList();
		List then = new ArrayList();
		
		Rule rule = ruleDao.queryById(ruleId);

//		when.add(rule.getWhen().toString());
//		when.add(new String(rule.getWhen()));		
//		then.add(rule.getThen().toString());
//		then.add(new String(rule.getThen()));
		
		String whenString = new String(rule.getWhen());
		String thenString = new String(rule.getThen());
		

		
//		whenString = whenString.replaceAll("{", "");
//		whenString = whenString.replaceAll("}", "");
//		whenString = whenString.replaceAll("[", "");
//		whenString = whenString.replaceAll("]", "");
//		
//		thenString = thenString.replaceAll("{", "");
//		thenString = thenString.replaceAll("}", "");
//		thenString = thenString.replaceAll("[", "");
//		thenString = thenString.replaceAll("]", "");

		whenString = whenString.substring(2, whenString.length());
		whenString = whenString.substring(0, whenString.length()-2);
		thenString = thenString.substring(2, thenString.length());
		thenString = thenString.substring(0, thenString.length()-2);
		
		when = Arrays.asList(whenString);
		then = Arrays.asList(thenString);
		
//		when = Arrays.asList(whenString.split(","));
//		then = Arrays.asList(thenString.split(","));
		
		return new RuleDto(rule.getId(), rule.getName(), rule.getDisplayName(), rule.getValidFrom(), rule.getValidTo(),
				rule.getDelay(), rule.getPriority(), rule.getDescription(), rule.getVersion(), rule.isInternal(), rule.isEnabled(),
				rule.isDirty(), rule.isDeployed(), when, then, rule.getWhenString(), rule.getThenString());
	}

	public Integer insertRule(RuleDto rule) {
//		String when = rule.getWhenPart().toString();
//		when = when.substring(1, when.length());
//		when = when.substring(0, when.length()-1);
//		
//		String then = rule.getThenPart().toString();
//		then = then.substring(1, then.length());
//		then = then.substring(0, then.length()-1);
		
		List whenList = rule.getWhenPart();
		List thenList = rule.getThenPart();//when.getBytes(), then.getBytes()
		
		ruleDao.insertRule(rule.getRuleName(), rule.getDisplayName(), Arrays.toString(whenList.toArray()).getBytes(),
				Arrays.toString(thenList.toArray()).getBytes(), rule.getWhenString(), rule.getThenString(),
				rule.getValidFrom(), rule.getValidTo(), rule.getDelay(), rule.getPriority(), rule.getDescription(),
				rule.isInternal(), rule.getVersion(), rule.isEnabled(), rule.isDirty(), rule.isDeployed());
		return 1;
	}

	public Integer updateRule(RuleDto rule) {
		ruleDao.updateRule(rule.getRuleId(), rule.getRuleName(), rule.getDisplayName(),
				rule.getWhenPart().toString().getBytes(), rule.getThenPart().toString().getBytes(),
				rule.getWhenString(), rule.getThenString(), rule.getValidFrom(), rule.getValidTo(), rule.getDelay(),
				rule.getPriority(), rule.getDescription(), rule.isInternal(), rule.getVersion(), rule.isEnabled(),
				rule.isDirty(), rule.isDeployed());
		return 1;
	}

	public Integer deleteRule(long ruleId) {
		ruleDao.deleteById(ruleId);
		return 1;
	}
}
