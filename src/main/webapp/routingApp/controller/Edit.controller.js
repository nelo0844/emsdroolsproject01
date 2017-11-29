sap.ui.controller("sap.gm.controller.Edit", {

	/**
	 * Called when a controller is instantiated and its View controls (if available) are already created. Can be used to modify the View before it is
	 * displayed, to bind event handlers and do other one-time initialization.
	 * 
	 * @memberOf emsdroolsproject01.Edit
	 */
	onInit: function() {
		this.getOwnerComponent().getRouter().getRoute("ruleEdit").attachPatternMatched(this._onRouteMatched, this);
	},

	_onRouteMatched: function(oEvent) {
		this._ruleId = oEvent.getParameter("arguments").ruleId;
		this.getView().bindElement("globalModel>/rules/" + this._ruleId);

		var currentRule = this.getView().getBindingContext("globalModel").getObject();
		var oModel = new sap.ui.model.json.JSONModel();
		if (currentRule && currentRule.whenPart) {
			oModel.setData({
				whenPart: currentRule.whenPart,
				thenPart: currentRule.thenPart
			});
		} else {
			oModel.setData({
				whenPart: [],
				thenPart: []
			});
		}

		this.getView().setModel(oModel);
	},

	/**
	 * available selection page
	 */
	onChangeSection: function(oEvent) {
		var oBindingContext = oEvent.getParameter("selectedItem").getBindingContext("selectionModel");
		var selectedObj = oBindingContext.getObject();
		var oModel = oBindingContext.getModel();
		var oData = oModel.getData();
		oData.selectedItem = selectedObj;
		oModel.setData(oData);
		oModel.refresh();
	},

	onPressAddSelection: function(oEvent) {
		var oBindingContext = oEvent.getSource().getBindingContext("selectionModel");
		var selectedObj = oBindingContext.getObject();
		var selectedItem = oBindingContext.getModel().getData().selectedItem;

		var that = this;
		if (selectedObj.content) {
			var icon = new sap.ui.core.Icon({
				size: "1rem",
				src: "sap-icon://add-document",
				press: that.onPressAddChildSelection.bind(that)
			});
			icon.addStyleClass("sapUiTinyMarginBegin");

			var itemContent = new sap.m.HBox({
				items: [
					new sap.m.Label({
						text: "{property}"
					}), icon
				]
			})
			itemContent.addStyleClass("sapUiSmallMargin");

			this._dialog = new sap.m.Dialog({
				title: selectedObj.property,
				type: 'Message',
				content: new sap.m.List({
					items: {
						path: '/content',
						template: new sap.m.CustomListItem({
							content: itemContent
						})
					}
				}),
				endButton: new sap.m.Button({
					text: 'Cancel',
					press: function() {
						this.getParent().close();
					}
				}),
				afterClose: function() {
					this.destroy();
				}
			});
			var oModel = new sap.ui.model.json.JSONModel();
			oModel.setData(selectedObj);
			this.getView().addDependent(this._dialog);
			this._dialog.setModel(oModel);

			this._dialog.open();
		} else {
			this._addSeletedItemToEditPage(selectedItem, selectedObj);
		}
	},

	// 针对列表中包含子节点的情况下需要重新包装重新设置值
	onPressAddChildSelection: function(oEvent) {
		var oBindingContext = oEvent.getSource().getBindingContext();
		var selectedObj = oBindingContext.getObject();
		var firstChild = oBindingContext.getModel().getData();
		var forSelectObj = {
			property: firstChild.property,
			selectedChildProperty: selectedObj
		}

		var selectedItem = this.getView().getModel("selectionModel").getData().selectedItem;
		// TODO
		this._addSeletedItemToEditPage(selectedItem, forSelectObj);
		this._dialog.close();
	},

	/**
	 * Edit Content
	 */

	onChangeOperationSection: function(oEvent) {
		var oBindingContext = oEvent.getParameter("selectedItem").getBindingContext("operationModel");
		var selectedObj = oBindingContext.getObject();
		var oModel = oBindingContext.getModel();
		var oData = oModel.getData();
		oData.selectedItem = selectedObj;
		oModel.setData(oData);
		oModel.refresh();
	},

	onPressActiveEditPart: function(oEvent) {
		var partId = oEvent.getSource().getParent().getId();
		if (partId.indexOf("ID_ThenContent") > 0) {
			this.byId("ID_WhenContent").removeStyleClass("GMActivePage");
			this.byId("ID_ThenContent").addStyleClass("GMActivePage");
		} else {
			this.byId("ID_WhenContent").addStyleClass("GMActivePage");
			this.byId("ID_ThenContent").removeStyleClass("GMActivePage");

		}
	},

	onSaveRuleEvent: function(oEvent) {
		var that = this;
		var oCurrentModel = this.getView().getModel();
		var oData = oCurrentModel.getData();

		var currentRule = this.getView().getBindingContext("globalModel").getObject();
		var ruleString = generateRuleString(oData);
		Object.assign(currentRule, ruleString);

		postToServer("drools/rule", currentRule, function(data, status) {
			that.getOwnerComponent().getRouter().navTo("ruleDetail", {
				ruleId: that._ruleId
			});
		});
		this.getView().getBindingContext("globalModel").getModel().refresh();
	},

	onCancelRuleEvent: function(oEvent) {
		var that = this;
		this.getOwnerComponent().getRouter().navTo("ruleDetail", {
			ruleId: that._ruleId
		});
	},

	onRemoveRuleEvent: function(oEvent) {
		var that = this;
		var oCurrentModel = this.getView().getModel("globalModel");
		var oData = oCurrentModel.getData();

		var currentRule = this.getView().getBindingContext("globalModel").getObject();
		var deteleIndex = -1;
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

	onDeleteConditionEvent: function(oEvent) {
		var oCurrentModel = this.getView().getModel();
		var oData = oCurrentModel.getData();

		var path = oEvent.getSource().getBindingContext().getPath();
		var paths = path.split("/");
		var child = oData[paths[1]][paths[2]][paths[3]];
		child.splice(paths[4], 1);
		oCurrentModel.refresh();
	},

	/*************************************************************************************************************************************************
	 * Internal functions
	 */
	_addSeletedItemToEditPage: function(selectedItem, selectedObj) {
		var oCurrentModel = this.getView().getModel();
		var oData = oCurrentModel.getData();
		var isWhenPart = this.byId("ID_WhenContent").hasStyleClass("GMActivePage");
		var oCurrentData = isWhenPart ? oData.whenPart : oData.thenPart;

		var selectedIndex = oCurrentData.length;
		oCurrentData.forEach(function(e, a) {
			if (e.propertyId === selectedItem.propertyId) {
				selectedIndex = a;
			}
		});
		// 判断selectedItem类别不存在的情况
		if (selectedIndex == oCurrentData.length) {
			var globalSelecteItem = {
				propertyId: selectedItem.propertyId,
				propertyName: selectedItem.propertyName
			};
			oCurrentData[selectedIndex] = $.extend(true, {}, globalSelecteItem);
			oCurrentData[selectedIndex].properties = [];
			oCurrentData[selectedIndex].properties[0] = selectedObj;
		} else {
			oCurrentData[selectedIndex].properties[oCurrentData[selectedIndex].properties.length] = selectedObj;
		}

		if (isWhenPart) {
			oData.whenPart = oCurrentData;
		} else {
			oData.thenPart = oCurrentData;
		}

		oCurrentModel.setData(oData);
	}
});
