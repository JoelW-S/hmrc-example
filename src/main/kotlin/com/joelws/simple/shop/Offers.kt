package com.joelws.simple.shop

import com.joelws.simple.shop.Item.Apple
import com.joelws.simple.shop.Item.Orange

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
*/object Offers {

    data class RunningTotal(val runningTotal: Int, val itemsToPurchase: List<Item>)

    private fun createOffer(amount: Int, priceOf: Int, type: Item): (RunningTotal) -> RunningTotal {

        return { runningTotal: RunningTotal ->
            val amountOfItems = runningTotal.itemsToPurchase.filterIsInstance(type.javaClass).count()
            val runningTotalToBe = runningTotal.runningTotal + ((amountOfItems / amount) * priceOf)
            val itemsLeftToPurchase = Array(amountOfItems % amount) { type }.plus(runningTotal.itemsToPurchase.filterNot { it.javaClass == type.javaClass }).toList()
            RunningTotal(runningTotalToBe, itemsLeftToPurchase)
        }
    }

    private val buyOneGetOneFreeOnApples = createOffer(2, Apple.cost, Apple)
    private val buyTwoForThreeOranges = createOffer(3, Orange.cost * 2, Orange)

    private val offers = listOf(buyOneGetOneFreeOnApples, buyTwoForThreeOranges)

    private fun applyOffer(runningTotal: RunningTotal, offer: (RunningTotal) -> RunningTotal): RunningTotal {
        return offer(runningTotal)
    }

    fun applyOffers(items: List<Item>): Int {
        val runningTotal = RunningTotal(0, items)
        val appliedOffers = offers.fold(runningTotal, { rt, offer ->
            applyOffer(rt, offer)
        })
        return appliedOffers.runningTotal + appliedOffers.itemsToPurchase.sumBy { it.cost }
    }

}
