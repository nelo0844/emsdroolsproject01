sap.ui.model.CompositeType.extend("sap.gm.customType", {
	formatValue: function(oValue, oType) {
		return oValue;
	},
	parseValue: function(oValue, oType, oCurrentValue) {
		// parsing step takes place before validating step, value could be altered here
		return oValue;
	},
	validateValue: function(oValue) {
		// The following Regex is NOT a completely correct one and only used for demonstration purposes.
		// RFC 5322 cannot even checked by a Regex and the Regex for RFC 822 is very long and complex.
		var rexMail = /^\w+[\w-+\.]*\@\w+([-\.]\w+)*\.[a-zA-Z]{2,}$/;
		if (!oValue.match(rexMail)) {
			throw new ValidateException("'" + oValue + "' is not a valid email address");
		}
	}
});

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

function generateRuleString(Object) {
	return {
		whenDrl: readRuleStructure(Object.whenPart, generateWhenString),
		thenDrl: readRuleStructure(Object.thenPart, generateThenString)
	};
}
function generateWhenString(property) {
	return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + property.operation + tranformRuleValue(property);
}
function generateThenString(property) {
	return (!property.selectedChildProperty ? property.technicalName : property.selectedChildProperty.technicalName) + "=" + tranformRuleValue(property);
}

function tranformRuleValue(property) {
	return (property.type || property.selectedChildProperty.type) == "java.lang.String" ? ("'" + property.value + "'") : property.value;
}

function readRuleStructure(sourceData, fnGenerate) {
	var objString = "";

	var childItems = {};
	if (sourceData) {
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

			var condition = allItem == "" ? "" : (item.technicalName + "(" + allItem + ")");
			objString = objString == "" ? condition : (objString + " and " + condition);
		});
	}
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
