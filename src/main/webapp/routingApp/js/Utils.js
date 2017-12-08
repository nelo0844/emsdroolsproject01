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
	return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + property.operation + _tranformRuleValue(property);
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
		});
		
		objString = objString == "" ? allItem : (objString + " ; " + allItem);
	}
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
	return (property.type || property.selectedChildProperty.type) == "java.lang.String" ? ("'" + property.value + "'") : property.value;
}
