function getDataByType(type,operationData) {
		return typeFormatter(type, operationData.long, operationData.boolean, operationData.strings, operationData.date);
	}
	
function initOperationList(currentRule,operationData) {
		currentRule.whenPart.forEach((item)=>{
			item.properties.forEach((child)=>{
				child.operationList = getDataByType(child.type,operationData);
			});
		})

		return currentRule;
	}
/**
 * don't need the value for some operation type.
 * 
 * @param data
 * @returns
 */
function operationFormatter(data) {
	if (data && (data == '== null' || data == '!= null')) {
		return false;
	}
	return true;
}

/**
 * different type need binding different value
 * 
 * @param type
 * @param long
 * @param boolean
 * @param string
 * @param date
 * @returns
 */
function typeFormatter(type, long, boolean, string, date) {
	var returnValue = long;
	switch (type) {
		case 'long':
		case 'float':
		case 'double':
		case 'int':
			returnValue = long;
			break;
		case 'boolean':
			returnValue = boolean;
			break;
		case 'java.lang.String':
			returnValue = string;
			break;
		case 'java.util.Date':
			returnValue = date;
			break;
		default:
			returnValue = long;
	}

	return returnValue;
}

function typeRegExp(type) {
	var returnValue = new RegExp("[0-9]");
	switch (type) {
		case 'long':
		case 'float':
		case 'double':
		case 'int':
			returnValue = new RegExp("[0-9]");
			break;
		case 'boolean':
			returnValue = new RegExp("true|false");
			break;
		case 'java.lang.String':
			returnValue = new RegExp("");
			break;
		case 'java.util.Date':
			returnValue = new RegExp("[0-9]");
			break;
		default:
			returnValue = new RegExp("[0-9]");
	}

	return returnValue;
}

/** *****************************Ajax calls***************************************** */
function postToServer(url, data, fnSuccess) {
	$.ajax({
		type: 'POST',
		url: url,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data),
		success: fnSuccess,
		dataType: "json"
	});
}

function putToServer(url, data, fnSuccess) {
	$.ajax({
		type: 'PUT',
		url: url,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data),
		success: fnSuccess,
		dataType: "json"
	});
}

function deleteFromServer(url, fnSuccess) {
	$.ajax({
		type: 'DELETE',
		url: url,
		contentType: "application/json; charset=utf-8",
		success: fnSuccess,
		dataType: "json"
	});
}

/**
 * main function to generate the rule String
 * 
 * @param Object
 * @returns
 */
function generateRuleString(Object) {
	var whenString = readRuleWhenStructure(Object.whenPart);
	var thenString = readRuleThenStructure(Object.thenPart, whenString.structure);
	thenString.needObjects.forEach((item)=>{
		whenString.content= whenString.content == "" ? item : whenString.content + " and " + item;
	});
	return {
		whenDrl: whenString.content,
		thenDrl: thenString.content
	};
}

// generate the When field
/**
 * status=="stop"
 * 
 * @param property
 * @returns
 */
function generateWhenString(property) {
	var operation = property.operation;
	if(operation=='== null'|| operation=='!= null'){
		return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + " " +property.operation;
	}
	
	return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + " " +property.operation + " " + _tranformRuleValue(property);
}

// generate the Then field
/**
 * $so.setStatus('up')
 * 
 * @param property
 * @param objectName
 * @returns
 */
function generateThenString(property,objectName) {
	var propertyName = (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName);
	var methodName = propertyName.substr(0,1).toLocaleUpperCase()+propertyName.substr(1);
	return objectName+".set"+methodName+"("+_tranformRuleValue(property)+")";
}


/**
 * getInstantiatedObject
 * 
 * @description generate the name of object like SaleOrder && Entitlement
 * @param name eg. SaleOrder
 * @returns eg. $so
 */
function getInstantiatedObject(name){
	var nameArr = name.split("");
	var returnVal = "";
	nameArr.forEach((item)=>{
		if(item>'A' && item<'Z'){
			returnVal+=item;
		}
	})
	
	return "$"+returnVal.toLocaleLowerCase();
}

// read when structure and collect the object name by technical Name
function readRuleWhenStructure(sourceData) {
	var objString = "";
	var objectArr = {};
	var childItems = {};
	if (sourceData) {
		sourceData.forEach(function(item) {
			var allItem = "";
			item.properties.forEach(function(property) {
				if (property.selectedChildProperty) {
					if (!childItems[property.technicalName]) {
						childItems[property.technicalName] = [];
					}
					childItems[property.technicalName].push(property);
				} else {
					allItem = (allItem == "" ? "" : (allItem + ",")) + generateWhenString(property);
				}
			});
			var condition = connectWhenString(allItem,item.technicalName,objectArr);
			objString = objString == "" ? condition : (objString + " and " + condition);
		});
	}
	
	for ( var child in childItems) {
		var allItem = "";
		childItems[child].forEach(function(property) {
			allItem = (allItem == "" ? "" : (allItem + ",")) + generateWhenString(property);
		});
		var condition = connectWhenString(allItem,child,objectArr);
		objString = objString == "" ? condition : (objString + " and " + condition);
	}
	
	return {
		content: objString,
		structure: objectArr
	};
}

function connectWhenString(allItems,technicalName,allObjects){
	var objectName = getInstantiatedObject(technicalName);
	allObjects[technicalName] = objectName;
	return allItems == "" ? "" : (objectName+": "+technicalName + "(" + allItems + ")");
}

// generate the Then part
function readRuleThenStructure(sourceData, whenStructure) {
	var objString = "";
	var needAddToWhenString = [];
	var childItems = {};
	var updateParameter = {};
	if (sourceData) {
		sourceData.forEach(function(item) {
			var allItem = "";
			item.properties.forEach(function(property) {
				if (property.selectedChildProperty) {
					if (!childItems[property.technicalName]) {
						childItems[property.technicalName] = [];
					}
					childItems[property.technicalName].push(property);
				} else {
					allItem = handTheUndefinedObject(allItem,property,item.technicalName,needAddToWhenString,whenStructure);
					updateParameter[item.technicalName] = whenStructure[item.technicalName] ? whenStructure[item.technicalName] : getInstantiatedObject(item.technicalName);
				}
			});

			
			if(allItem!=""){
				objString = objString == "" ? allItem : (objString + ";" + allItem);
			}
		});
	}
	
	for ( var i in childItems) {
		var allItem = "";
		childItems[i].forEach(function(property) {
			allItem = handTheUndefinedObject(allItem,property,i,needAddToWhenString,whenStructure);
			updateParameter[i] = whenStructure[i] ? whenStructure[i] : getInstantiatedObject(i);
		});
		
		objString = objString == "" ? allItem : (objString + " ; " + allItem);
	}
	
//	// update
//	for ( var i in updateParameter) {
//		objString = objString == "" ? "" : (objString + " ; " + "update("+updateParameter[i]+")");
//	}
	
	return {
		content: objString,
		needObjects: needAddToWhenString
	};
}

function handTheUndefinedObject(allItem,property,technicalName,needAddToWhenString,whenStructure){
	var objectName = whenStructure[technicalName];
	if(!objectName){
		objectName = getInstantiatedObject(technicalName);
		needAddToWhenString.push(objectName+": "+ technicalName+"()");
	}
	return (allItem == "" ? "" : (allItem + ";")) + generateThenString(property,objectName);
}

/** **********private function************ */
// set value by the value type
function _tranformRuleValue(property) {
	// return (property.type || property.selectedChildProperty.type) == "java.lang.String" ? ("'" + property.value + "'") : property.value;
	return ("'" + property.value + "'");
}
