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

function deleteFromServer(url, fnSuccess) {
	$.ajax({
		type: 'DELETE',
		url: url,
		contentType: "application/json; charset=utf-8",
		success: fnSuccess,
		dataType: "json"
	});
}

function generateRuleString(Object) {
	return {
		whenPart: Object.whenPart,
		thenPart: Object.thenPart,
		whenString: readRuleStructure(Object.whenPart, generateWhenString),
		thenString: readRuleStructure(Object.thenPart, generateThenString)
	};
}
function generateWhenString(property) {
	return (!property.selectedChildProperty ? property.property : property.selectedChildProperty.property) + property.operation + "'" + property.value + "'";
}
function generateThenString(property) {
	return (!property.selectedChildProperty ? property.property : property.selectedChildProperty.property) + "=" + "'" + property.value + "'";
}

function readRuleStructure(sourceData, fnGenerate) {
	var objString = "";

	var childItems = {};
	sourceData.forEach(function(item) {
		var allItem = "";
		item.properties.forEach(function(property) {
			if (property.selectedChildProperty) {
				if (!childItems[property.property]) {
					childItems[property.property] = [];
				}
				childItems[property.property].push(property);
			} else {
				allItem = (allItem == "" ? "" : (allItem + ",")) + fnGenerate(property);
			}
		});

		var condition = allItem == "" ? "" : (item.propertyName + "(" + allItem + ")");
		objString = objString == "" ? condition : (objString + " and " + condition);
	});

	for ( var i in childItems) {
		var allItem = "";
		childItems[i].forEach(function(property) {
			allItem = (allItem == "" ? "" : (allItem + ",")) + fnGenerate(property);
		});
		var condition = i + "(" + allItem + ")";
		objString = objString == "" ? condition : (objString + " and " + condition);
	}
	return objString;
}
