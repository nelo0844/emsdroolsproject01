sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/Device", 'sap/m/Button', 'sap/m/Dialog', 'sap/m/Label', 'sap/m/Text', 'sap/m/Input', 'sap/ui/model/Filter'
], function(Controller, Device, Button, Dialog, Label, Text, Input, Filter) {
	"use strict";

	return Controller.extend("sap.gm.controller.Master", {
		onInit: function() {
			this.getOwnerComponent().getRouter().getRoute("master").attachPatternMatched(this._onRouteMatched, this);
		},
		_onRouteMatched: function(oEvent) {
			/*
			 * Navigate to the first item by default only on desktop and tablet (but not phone). Note that item selection is not handled as it is out
			 * of scope of this sample
			 */
			if (!Device.system.phone) {
				this.getOwnerComponent().getRouter().navTo("mainPage", null, true);
			}
		},
		onSelectionChange: function(oEvent) {
			var sRuleId = oEvent.getSource().getSelectedItem().getBindingContext("globalModel").getProperty("ruleId")
			this.getOwnerComponent().getRouter().navTo("ruleDetail", {
				ruleId: sRuleId
			}, !Device.system.phone);
		},
		onAddRuleEvent: function() {
			this._dialog = new Dialog({
				title: 'Add Rule',
				type: 'Message',
				content: [
					new Input("createRuleInput", {
						width: '100%',
						placeholder: 'Please input the rule name'
					})
				],
				beginButton: new Button({
					text: 'Ok',
					press: this.addRule.bind(this)
				}),
				endButton: new Button({
					text: 'Cancel',
					press: function() {
						this.getParent().close();
					}
				}),
				afterClose: function() {
					this.destroy();
				}
			});

			this._dialog.open();
		},
		addRule: function() {
			var that = this;
			var sText = sap.ui.getCore().byId('createRuleInput').getValue();
			var data = this.getView().getModel("globalModel").getData();
			data.rules[data.rules.length] = {
				ruleName: sText,
				products: []
			}
			postToServer("drools/rule", data.rules[data.rules.length - 1], function(data, status) {
				// that.getView().getModel("globalModel").setData(data);
			});
			that.getView().getModel("globalModel").setData(data);
			this._dialog.close();
		},
		onSearch: function(oEvt) {
			// add filter for search
			var aFilters = [];
			var sQuery = oEvt.getSource().getValue();
			if (sQuery && sQuery.length > 0) {
				var filter = new Filter("ruleName", sap.ui.model.FilterOperator.Contains, sQuery);
				aFilters.push(filter);
			}

			// update list binding
			var list = this.getView().byId("ruleList");
			var binding = list.getBinding("items");
			binding.filter(aFilters, "Application");
		}
	});

}, /* bExport= */true);
