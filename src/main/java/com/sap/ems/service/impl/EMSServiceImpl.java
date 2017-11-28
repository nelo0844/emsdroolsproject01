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
import com.sap.ems.dto.PropertiesDto;
import com.sap.ems.dto.RuleDto;
import com.sap.ems.dto.RulePartDto;
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
	
	public List<RuleDto> getAllRules() {
		List<Rule> rules = new ArrayList();
		List<RuleDto> rulesDtos = new ArrayList();
		String whenString;
		String thenString;
		rules = ruleDao.queryAll();
		for(Rule rule : rules){
			whenString = new String(rule.getWhen());
			thenString = new String(rule.getThen());
			rulesDtos.add(new RuleDto(rule.getId(), rule.getName(), rule.getDisplayName(), rule.getValidFrom(), rule.getValidTo(),
					rule.getDelay(), rule.getPriority(), rule.getDescription(), rule.getVersion(), rule.isInternal(), rule.isEnabled(),
					rule.isDirty(), rule.isDeployed(), whenString, thenString, rule.getWhenString(), rule.getThenString()));
		}
		return rulesDtos;
	}

	public RuleDto getRuleById(long ruleId) {
		Rule rule = ruleDao.queryById(ruleId);
		String whenString = new String(rule.getWhen());
		String thenString = new String(rule.getThen());
		return new RuleDto(rule.getId(), rule.getName(), rule.getDisplayName(), rule.getValidFrom(), rule.getValidTo(),
				rule.getDelay(), rule.getPriority(), rule.getDescription(), rule.getVersion(), rule.isInternal(), rule.isEnabled(),
				rule.isDirty(), rule.isDeployed(), whenString, thenString, rule.getWhenString(), rule.getThenString());
	}

	public Integer insertRule(RuleDto rule) {
		String whenString;
		String thenString;
		whenString = parseListToString(rule.getWhenPart());
		thenString = parseListToString(rule.getThenPart());
		ruleDao.insertRule(rule.getRuleName(), rule.getDisplayName(), whenString.getBytes(), thenString.getBytes(),
				rule.getWhenString(), rule.getThenString(), rule.getWhenDrl(), rule.getThenDrl(), rule.getValidFrom(), rule.getValidTo(), rule.getDelay(),
				rule.getPriority(), rule.getDescription(), rule.isInternal(), rule.getVersion(), rule.isEnabled(),
				rule.isDirty(), rule.isDeployed());
		return 1;
	}

	public Integer updateRule(RuleDto rule) {
		String whenString;
		String thenString;
		whenString = parseListToString(rule.getWhenPart());
		thenString = parseListToString(rule.getThenPart());
		ruleDao.updateRule(rule.getRuleId(), rule.getRuleName(), rule.getDisplayName(),
				whenString.getBytes(), thenString.getBytes(),
				rule.getWhenString(), rule.getThenString(), rule.getWhenDrl(), rule.getThenDrl(), rule.getValidFrom(), rule.getValidTo(), rule.getDelay(),
				rule.getPriority(), rule.getDescription(), rule.isInternal(), rule.getVersion(), rule.isEnabled(),
				rule.isDirty(), rule.isDeployed());
		return 1;
	}

	public Integer deleteRule(long ruleId) {
		ruleDao.deleteById(ruleId);
		return 1;
	}
	
	private String parseListToString(List<RulePartDto> List) {
		String result = "[";
		List<PropertiesDto> Properties = new ArrayList<PropertiesDto>();
		PropertiesDto selectedChildProperty;
		
		for(int i = 0; i < List.size();i++){
			result = result + "{";
			result = result + "\"propertyId\":" + List.get(i).getPropertyId() + ",";
			result = result + "\"propertyName\":\"" + List.get(i).getPropertyName() + "\",";
			Properties = List.get(i).getProperties();
			if(Properties != null){
				result = result + "\"properties\":" + "[";
					for(int k=0;k < Properties.size();k++){
						result = result + "{\"property\":\"" + Properties.get(k).getProperty() + "\",";
						if(Properties.get(k).getType() != null){
							result = result + "\"type\":\"" + Properties.get(k).getType() + "\"";
						}else{
							result = result + "\"type\":null,";
						}
						selectedChildProperty = Properties.get(k).getSelectedChildProperty();
						if(selectedChildProperty != null){
							result = result + "\"selectedChildProperty\":" + "{";
							result = result + "\"property\":\"" + selectedChildProperty.getProperty() + "\",";
							result = result + "\"type\":\"" + selectedChildProperty.getType() + "\"";
						}
						result = result + ",\"operation\":\"" + Properties.get(k).getOperation() + "\"";
						result = result + ",\"value\":\"" + Properties.get(k).getValue() + "\"";
						if(k == Properties.size() - 1) {
							result = result + "}";
						}else {
							result = result + "},";
						}
					}
			}
			result = result + "}]";
		}
		result = result + "}]";
		
		return result;
	}
	
}
