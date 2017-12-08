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

// generate when field
function generateWhenString(property) {
	return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + property.operation + _tranformRuleValue(property);
}

// generate then field
function generateThenString(property,objectName) {
	var propertyName = (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName);
	var methodName = propertyName.substr(0,1).toLocaleUpperCase()+propertyName.substr(1);
	return objectName+".set"+methodName+"("+_tranformRuleValue(property)+")";
}



/**
 * getInstantiatedObject
 * 
 * @description generate the name of object like SaleOrder && Entitlement
 * @param name
 * @returns
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
			var objectName = getInstantiatedObject(item.technicalName);
			objectArr[item.technicalName] = objectName;
			var condition = allItem == "" ? "" : (objectName+": "+item.technicalName + "(" + allItem + ")");
			objString = objString == "" ? condition : (objString + " and " + condition);
		});
	}
	
	for ( var i in childItems) {
		var allItem = "";
		childItems[i].forEach(function(property) {
			allItem = (allItem == "" ? "" : (allItem + ",")) + generateWhenString(property);
		});
		
		var objectName = getInstantiatedObject(i);
		var condition = objectName +": " + i + "(" + allItem + ")";
		objectArr[i] = objectName;
		objString = objString == "" ? condition : (objString + " and " + condition);
	}
	
	return {
		content: objString,
		structure: objectArr
	};
}

// generate the Then part
function readRuleThenStructure(sourceData, whenStructure) {
	var objString = "";
	var needAddToWhenString = [];
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
					var objectName = whenStructure[item.technicalName];
					if(!objectName){
						objectName = getInstantiatedObject(item.technicalName);
						needAddToWhenString.push(objectName+": "+ item.technicalName+"()");
					}
					allItem = (allItem == "" ? "" : (allItem + ";")) + generateThenString(property,objectName);
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
			var objectName = whenStructure[i];
			if(!objectName){
				objectName = getInstantiatedObject(i);
				needAddToWhenString.push(objectName+": "+ i+"()");
			}
			allItem = (allItem == "" ? "" : (allItem + ";")) + generateThenString(property,objectName);
		});
		
		objString = objString == "" ? allItem : (objString + " ; " + allItem);
	}
	return {
		content: objString,
		needObjects: needAddToWhenString
	};
}
/** **********private function************ */
// set value by the value type
function _tranformRuleValue(property) {
	return (property.type || property.selectedChildProperty.type) == "java.lang.String" ? ("'" + property.value + "'") : property.value;
}
