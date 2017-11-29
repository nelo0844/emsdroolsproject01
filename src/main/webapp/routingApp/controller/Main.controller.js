sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/core/routing/History", "sap/ui/Device"
], function(Controller, History, Device) {
	"use strict";

	return Controller.extend("sap.gm.controller.Main", {
		onInit: function() {
			this.getOwnerComponent().getRouter().getRoute("mainPage").attachPatternMatched(this._onRouteMatched, this);
		},
		_onRouteMatched: function(oEvent) {
			this._orderId = oEvent.getParameter("arguments").orderId;
			this.getView().bindElement("globalModel>/rules/" + this._orderId);

			var currentRule = this.getView().getBindingContext("globalModel").getObject();
			var oModel = new sap.ui.model.json.JSONModel();
			if (currentRule && currentRule.whenDrl) {
				oModel.setData({
					whenDrl: currentRule.whenDrl,
					thenDrl: currentRule.thenDrl
				});
			}

			this.getView().setModel(oModel);
		}
	});

}, /* bExport= */true);
