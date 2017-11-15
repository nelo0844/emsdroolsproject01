sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/core/routing/History", "sap/ui/Device"
], function(Controller, History, Device) {
	"use strict";

	return Controller.extend("sap.gm.controller.Detail", {
		onInit: function() {
			this.getOwnerComponent().getRouter().getRoute("orderDetails").attachPatternMatched(this._onRouteMatched, this);
		},
		_onRouteMatched: function(oEvent) {
			this._orderId = oEvent.getParameter("arguments").orderId;
			this.getView().bindElement("globalModel>/orders/" + this._orderId);
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
			this.getOwnerComponent().getRouter().navTo("productDetails", {
				rulesId: this._orderId
			});
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
