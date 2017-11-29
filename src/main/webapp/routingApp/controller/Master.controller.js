sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/Device", 'sap/m/Button', 'sap/m/Dialog', 'sap/m/Label', 'sap/m/Text', 'sap/m/Input', 'sap/ui/model/Filter'
], function(Controller, Device, Button, Dialog, Label, Text, Input, Filter) {
	"use strict";

	return Controller.extend("sap.gm.controller.Master", {
		onInit: function() {
			this.getOwnerComponent().getRouter().getRoute("master").attachPatternMatched(this._onRouteMatched, this);
		},
		_onRouteMatched: function(oEvent) {
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
				ruleName: sText
			}
			postToServer("drools/rule", data.rules[data.rules.length - 1], function(returnData, status) {
				// TODO 将存储后的数据填充在这个节点
				that.getView().getModel("globalModel").setData(data);
			});
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
