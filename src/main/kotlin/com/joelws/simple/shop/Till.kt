package com.joelws.simple.shop

import com.joelws.simple.shop.Item.*

/*
Copyright 2016 Joel Whittaker-Smith

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/object Till {

    private fun convertStringToItem(itemAsString: String): Item {
        return when (itemAsString.toLowerCase()) {
            "apple" -> Apple
            "orange" -> Orange
            else -> NotKnown
        }
    }

    fun purchase(items: List<String>): String {
        return calculateTotal(items.map { convertStringToItem(it) })
    }

    private fun calculateTotal(items: List<Item>): String {
        return "The total for your shopping is: %.2f".format(Offers.applyOffers(items.filter { it !is NotKnown }).toDouble())
    }

}
