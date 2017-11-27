function generateRuleString(Object) {
	return {
		whenPart: Object.whenPart,
		thenPart: Object.thenPart,
		whenString: generateWhenString(Object.whenPart),
		thenString: generateThenString(Object.thenPart)
	};
}
function generateWhenString(Object) {
	var whenString = "";
	Object.forEach(function(item) {
		var allItem = "";
		item.properties.forEach(function(property) {
			if (allItem == "") {
				allItem = property.property + property.operation + "'" + property.value + "'";
			} else {
				allItem = allItem + "," + property.property + property.operation + "'" + property.value + "'";
			}
		});

		if (whenString == "") {
			whenString = item.propertyName + "(" + allItem + ")";
		} else {
			whenString = whenString + " and " + item.propertyName + "(" + allItem + ")";
		}
	})
	return whenString;
}
function generateThenString(Object) {
	var thenString = "";
	Object.forEach(function(item) {
		var allItem = "";
		item.properties.forEach(function(property) {
			if (allItem == "") {
				allItem = property.property + "=" + "'" + property.value + "'";
			} else {
				allItem = allItem + "," + property.property + "=" + "'" + property.value + "'";
			}
		});

		if (thenString == "") {
			thenString = item.propertyName + "(" + allItem + ")";
		} else {
			thenString = thenString + " and " + item.propertyName + "(" + allItem + ")";
		}
	})
	return thenString;
}
