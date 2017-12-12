sap.ui.define([
	"sap/ui/core/UIComponent", "sap/ui/model/json/JSONModel", "sap/ui/Device"
], function(UIComponent, JSONModel, Device) {
	"use strict";
	return UIComponent.extend("sap.gm.Component", {

		metadata: {
			manifest: "json"
		},

		init: function() {

			var oModel = new JSONModel();
			// oModel.loadData("routingApp/data/data.json", null, true);
			oModel.loadData("drools/allrules", null, false);
			var oData = oModel.getData();
			if(oData.data){
				oData.data.forEach((item,index)=>{
					if(item.whenString && item.whenString != ""){
						item.whenPart = JSON.parse(item.whenString);
					}
					if(item.thenString && item.thenString != ""){
						item.thenPart = JSON.parse(item.thenString);
					}
				});
			}
			oModel.setData({
				rules: oData.data,
			});
			this.setModel(oModel, "globalModel");

			// static model
			// device
			this.setModel(this.createDeviceModel(), "device");

			// available selection model
			var oSelectionModel = new JSONModel();
			oSelectionModel.loadData("drools/list", null, true, "POST");
			oSelectionModel.attachRequestCompleted(function() {
				var oData = oSelectionModel.getData().data;
				var availableData = {
					selections: oData,
					selectedItem: oData[0]
				}
				oSelectionModel.setData(availableData);
			});
			this.setModel(oSelectionModel, "selectionModel");

			// operation model for all type
			var oOperationModel = new JSONModel();
			oOperationModel.loadData("routingApp/data/operation.json", null, false);
			this.setModel(oOperationModel, "operationModel");
			
			var notificationModel = new JSONModel();
			notificationModel.setData({status: true});
			this.setModel(notificationModel, "notificationModel");
			
			UIComponent.prototype.init.apply(this, arguments);

			// Parse the current url and display the targets of the route that matches the hash
			this.getRouter().initialize();

			this.getRouter().attachTitleChanged(function(oEvent) {
				// set the browser page title based on selected order/product
				document.title = oEvent.getParameter("title");
			});
		},
		createDeviceModel: function() {
			var oModel = new JSONModel(Device);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		}

	});
}, true);
