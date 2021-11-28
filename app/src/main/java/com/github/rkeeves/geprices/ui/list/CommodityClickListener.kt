package com.github.rkeeves.geprices.ui.list

import com.github.rkeeves.geprices.data.local.Commodity

class CommodityClickListener(val clickListener: (commodityId: Int) -> Unit) {
    fun onClick(commodity: Commodity) = clickListener(commodity.id)
}