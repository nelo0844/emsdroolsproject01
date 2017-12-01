sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/Device", 'sap/m/Button', 'sap/m/Dialog', 'sap/m/Label', 'sap/m/Text', 'sap/m/Input', 'sap/ui/model/Filter', 'sap/m/MessageStrip'
], function(Controller, Device, Button, Dialog, Label, Text, Input, Filter, MessageStrip) {
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
			var sRuleId = oEvent.getSource().getBindingContext("globalModel").getProperty("ruleId")
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
			this._dialog.close();
			this.getOwnerComponent().getRouter().navTo("ruleCreate", {
				ruleName: sText
			}, !Device.system.phone);

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
		},
		onApplyRuleEvent: function() {
			postToServer("drools/rule/appliance",{},(data,status)=>{
				if(!data.status){
					this.showMsgStrip(data.error);
				}
				
			});
		},
		showMsgStrip: function(message) {
			var oMs = sap.ui.getCore().byId("msgStrip");

			if (oMs) {
				oMs.destroy();
			}
			this._generateMsgStrip(message);
		},

		_generateMsgStrip: function(message) {
			var aTypes = [
				"Information", "Warning", "Error", "Success"
			], oVC = sap.ui.getCore().byId("__component0---app--oVerticalContent"),

			oMsgStrip = new MessageStrip("msgStrip", {
				text: message,
				showCloseButton: true,
				showIcon: true,
				type: "Error"
			});

			oVC.addContent(oMsgStrip);
		}
	});

}, /* bExport= */true);
