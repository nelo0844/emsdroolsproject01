sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/core/routing/History", "sap/ui/Device"
], function(Controller, History, Device) {
	"use strict";

	return Controller.extend("sap.gm.controller.Detail", {
		onInit: function() {
			this.getOwnerComponent().getRouter().getRoute("ruleDetail").attachPatternMatched(this._onRouteMatched, this);
		},
		_onRouteMatched: function(oEvent) {
			this._ruleId = oEvent.getParameter("arguments").ruleId;
			this.getView().bindElement("globalModel>/rules/" + this._ruleId);

			var currentRule = this.getView().getBindingContext("globalModel").getObject();
			var oModel = new sap.ui.model.json.JSONModel();
			if (currentRule && currentRule.whenString) {
				oModel.setData({
					whenString: currentRule.whenString,
					thenString: currentRule.thenString
				});
			}

			this.getView().setModel(oModel);
		},
		onNavBack: function() {
			var sPreviousHash = History.getInstance().getPreviousHash();

			// The history contains a previous entry
			if (sPreviousHash !== undefined) {
				history.go(-1);
			} else {
				// There is no history!
				// Naviate to master page
				this.getOwnerComponent().getRouter().navTo("master", {}, true);
			}
		},
		onEditRuleEvent: function(oEvent) {
			this.getOwnerComponent().getRouter().navTo("ruleEdit", {
				ruleId: this._ruleId
			});
		},
		onRemoveRuleEvent: function(oEvent) {
			var that = this;
			var oCurrentModel = this.getView().getModel("globalModel");
			var oData = oCurrentModel.getData();

			var currentRule = this.getView().getBindingContext("globalModel").getObject();
			let
			deteleIndex = -1;
			oData.rules.forEach(function(item, index) {
				if (item.ruleId === currentRule.ruleId) {
					deteleIndex = index;
				}
			});
			if (deteleIndex != -1) {
				deleteFromServer("drools/rule/" + currentRule.ruleId + "/detail", function() {
					oData.rules.splice(deteleIndex, 1);
					oCurrentModel.refresh();
					that.getOwnerComponent().getRouter().navTo("master", {}, true);
				});
			}
		},
		onChangeSection: function(oEvent) {
			var oBindingContext = oEvent.getParameter("selectedItem").getBindingContext("selectionModel");
			var selectedObj = oBindingContext.getObject();
			var oModel = oBindingContext.getModel();
			var oData = oModel.getData();
			oData.selectedItem = selectedObj;
			oModel.setData(oData);
			oModel.refresh();
		}

	});

}, /* bExport= */true);
