sap.ui.controller("sap.gm.controller.Edit", {
	_create: false,
	/**
	 * Called when a controller is instantiated and its View controls (if available) are already created. Can be used to modify the View before it is
	 * displayed, to bind event handlers and do other one-time initialization.
	 * 
	 * @memberOf emsdroolsproject01.Edit
	 */
	/**
	 * Custom model type for validating an E-Mail address
	 * 
	 * @class
	 * @extends sap.ui.model.SimpleType
	 */
	customType: sap.ui.model.CompositeType.extend("string", {
		formatValue: function(oValue, oType) {
			return oValue;
		},
		parseValue: function(oValue, oType, oCurrentValue) {
			return oValue;
		},
		validateValue: function(oValue) {
			var rexMail = /^\w+[\w-+\.]*\@\w+([-\.]\w+)*\.[a-zA-Z]{2,}$/;
			if (!oValue.match(rexMail)) {
				throw new ValidateException("'" + oValue + "' is not a valid email address");
			}
		}
	}),

	onInit: function() {
		this.getOwnerComponent().getRouter().getRoute("ruleEdit").attachPatternMatched(this._onRouteMatchedEdit, this);
		this.getOwnerComponent().getRouter().getRoute("ruleCreate").attachPatternMatched(this._onRouteMatchedCreate, this);

		sap.ui.getCore().getMessageManager().registerObject(this.getView().byId("ID_WhenContent"), true);
		sap.ui.getCore().getMessageManager().registerObject(this.getView().byId("ID_ThenContent"), true);
	},

	// for edit rule
	_onRouteMatchedEdit: function(oEvent) {
		this._create = false;
		var that = this;
		this._ruleId = oEvent.getParameter("arguments").ruleId;

		var oGlobalModel = this.getOwnerComponent().getModel("globalModel");
		var oGlobalData = oGlobalModel.getData();
		var bindingIndex = -1;
		if (oGlobalData.rules) {
			oGlobalData.rules.forEach(function(item, index) {
				if (item.ruleId == that._ruleId) {
					bindingIndex = index;
				}
			});
		}
		if (bindingIndex == -1) {
			alert("error");
			this.getOwnerComponent().getRouter().navTo("master", {}, true);
		} else {
			this.getView().bindElement("globalModel>/rules/" + bindingIndex);

			var currentRule = this.getView().getBindingContext("globalModel").getObject();
			currentRule = $.extend(true, {}, currentRule);
			var oModel = new sap.ui.model.json.JSONModel();
			oModel.setDefaultBindingMode("TwoWay");
			if (currentRule) {
				// initialize value for when part and then part
				currentRule = initOperationList(currentRule, this.getOwnerComponent().getModel("operationModel").getData());

				oModel.setData(currentRule);
			} else {
				oModel.setData({
					whenPart: [],
					thenPart: []
				});
			}

			this.getView().setModel(oModel);
		}
	},

	// for create a rule
	_onRouteMatchedCreate: function(oEvent) {
		this._create = true;
		this._ruleName = oEvent.getParameter("arguments").ruleName;
		var oModel = new sap.ui.model.json.JSONModel();
		oModel.setData({
			whenPart: [],
			thenPart: [],
			ruleName: this._ruleName
		});
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
			technicalName: firstChild.technicalName,
			property: firstChild.property,
			selectedChildProperty: selectedObj,
			type: selectedObj.type
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
		var oBindingContext = oEvent.getParameter("selectedItem").getBindingContext();
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
		var globalModel = this.getView().getModel("globalModel");
		var oData = oCurrentModel.getData();

		if (this._checkCurrentForm()) {
			if (this._create) {
				var ruleString = generateRuleString(oData);
				ruleString = $.extend(ruleString, oData)
				postToServer("drools/rule", ruleString, function(data, status) {
					var globalData = globalModel.getData();
					globalData.rules.push(data.data);
					globalModel.refresh(true);
					that.getOwnerComponent().getRouter().navTo("ruleDetail", {
						ruleId: data.data.ruleId
					});
				});
			} else {
				// TODO globalModel
				var currentRule = this.getView().getModel().getData();
				var ruleString = generateRuleString(oData);
				currentRule = $.extend(currentRule, ruleString);
				putToServer("drools/rule", currentRule, function(data, status) {
					globalModel.setProperty(that.getView().getBindingContext("globalModel").getPath(), data.data);
					globalModel.refresh();
					that.getOwnerComponent().getRouter().navTo("ruleDetail", {
						ruleId: that._ruleId
					});

				});
			}
		}
	},

	onCancelRuleEvent: function(oEvent) {
		var that = this;
		if (this._create) {
			this.getOwnerComponent().getRouter().navTo("mainPage", null, true);
		} else {
			this.getOwnerComponent().getRouter().navTo("ruleDetail", {
				ruleId: that._ruleId
			});
		}
	},

	onRemoveRuleEvent: function(oEvent) {
		var that = this;
		if (this._create) {
			this.getOwnerComponent().getRouter().navTo("mainPage", null, true);
		} else {
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
		}

	},

	onDeleteConditionEvent: function(oEvent) {
		var oCurrentModel = this.getView().getModel();
		var oData = oCurrentModel.getData();

		var path = oEvent.getSource().getBindingContext().getPath();
		var paths = path.split("/");
		var child = oData[paths[1]][paths[2]][paths[3]];
		child.splice(paths[4], 1);
		if (child.length == 0) {
			oData[paths[1]].splice(paths[2], 1);
		}
		oCurrentModel.refresh();
	},

	/*************************************************************************************************************************************************
	 * Internal functions
	 */
	_addSeletedItemToEditPage: function(selectedItem, selectedObj) {
		// add operation array for current selected object

		selectedObj.operationList = getDataByType(selectedObj.type, this.getOwnerComponent().getModel("operationModel").getData());

		var oCurrentModel = this.getView().getModel();
		var oData = oCurrentModel.getData();
		var isWhenPart = this.byId("ID_WhenContent").hasStyleClass("GMActivePage");
		var oCurrentData = isWhenPart ? oData.whenPart : oData.thenPart;

		if (!oCurrentData) {
			oCurrentData = [];
		}
		var selectedIndex = oCurrentData.length
		oCurrentData.forEach(function(e, a) {
			if (e.propertyId === selectedItem.propertyId) {
				selectedIndex = a;
			}
		});
		// 判断selectedItem类别不存在的情况
		if (selectedIndex == oCurrentData.length) {
			var globalSelecteItem = {
				propertyId: selectedItem.propertyId,
				propertyName: selectedItem.propertyName,
				technicalName: selectedItem.technicalName
			};
			oCurrentData[selectedIndex] = $.extend(true, {}, globalSelecteItem);
			oCurrentData[selectedIndex].properties = [];
			oCurrentData[selectedIndex].properties[0] = $.extend(true, {}, selectedObj);
		} else {
			oCurrentData[selectedIndex].properties[oCurrentData[selectedIndex].properties.length] = $.extend(true, {}, selectedObj);
		}

		if (isWhenPart) {
			oData.whenPart = oCurrentData;
		} else {
			oData.thenPart = oCurrentData;
		}

		oCurrentModel.setData(oData);
	},

	_checkCurrentForm: function() {
		var checkResult = true;
		$("#" + this.getView().getId() + " .GMCustomeInput").each((index,item)=>{
			var currentInput = sap.ui.getCore().byId(item.id);
			currentInput.setValueState("Success");
			var type = currentInput.getCustomData()[0].getValue();
			
			// 不同类型的string不一样的
			var patt= typeRegExp(type);
			
			var check = patt.test(currentInput.getValue());
			if(!check){
				checkResult = false;
				currentInput.setValueState("Error");
			}
		});
		return checkResult;
	}
});
