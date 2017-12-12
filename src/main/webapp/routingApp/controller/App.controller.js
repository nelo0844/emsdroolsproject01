sap.ui.define([
	"sap/ui/core/mvc/Controller", "sap/ui/core/routing/History", "sap/ui/Device"
], function(Controller, History, Device) {
	"use strict";

	return Controller.extend("sap.gm.controller.App", {
		onInit: function() {
		},
		handlePopoverPress: function(oEvent) {

			// create popover
			if (!this._oPopover) {
				this._oPopover = sap.ui.xmlfragment("sap.gm.fragment.Popover", this);
				this.getView().addDependent(this._oPopover);
				this._oPopover.setModel(this.getOwnerComponent().getModel("notificationModel"));
			}

			// delay because addDependent will do a async rerendering and the actionSheet will immediately close without it.
			var oButton = oEvent.getSource();
			jQuery.sap.delayedCall(0, this, function() {
				this._oPopover.openBy(oButton);
			});
		}
	});

}, /* bExport= */true);
