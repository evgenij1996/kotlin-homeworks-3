package ru.netology

import java.util.*

fun main() {
    while (true) {
        println("Введите сумму текущего и предыдущего перевода в копейках")
        println("""Введите тип карты/счёта:
            1. MasterCard
            2. Maestro
            3. Viza
            4. Мир
            5. Vk Pay
        """.trimMargin())
        val scanner = Scanner(System.`in`)
        val currentTransfersSum = scanner.next().toInt()
        val previousTransfersSumMonth = scanner.next().toInt()
        val card_AccountType = scanner.next().toInt()
        val taxViza_Mir = 0.75
        val minTaxViza_Mir = 35 * 100
        val limitMonthMastercard_Maestro = 75_000 * 100
        val taxMonthMastercard_Maestro = 0.6
        val additionaltaxMonthMastercard_Maestro = 20 * 100
        val maximumMonthlyTransferCard = 600_000 * 100
        val maximumTransferDayCard = 150_000 * 100
        val maximumMonthlyTransferVkPay = 40_000 * 100
        val maximumTransferDayVkPay = 15_000 * 100

        if (!spotCard_AccountType(card_AccountType,
                        currentTransfersSum,
                        previousTransfersSumMonth,
                        maximumTransferDayCard,
                        maximumMonthlyTransferCard,
                        maximumMonthlyTransferVkPay,
                        maximumTransferDayVkPay)) continue

        val transfersComission = spotTaxOnCard_Account(card_AccountType,
                currentTransfersSum,
                previousTransfersSumMonth,
                taxViza_Mir,
                minTaxViza_Mir,
                limitMonthMastercard_Maestro,
                taxMonthMastercard_Maestro,
                additionaltaxMonthMastercard_Maestro)

        val finalTransfersSum = calculationFinalTransfersSum(currentTransfersSum, transfersComission)

        println("Сумма комиссии $transfersComission коп")
        println("Сумма перевода с учётом комиссии $finalTransfersSum коп")
    }
}

fun spotCard_AccountType(card_AccountType: Int,
                         currentTransfersSum: Int,
                         previousTransfersSumMonth: Int,
                         maximumTransferDayCard: Int,
                         maximumMonthlyTransferCard: Int,
                         maximumMonthlyTransferVkPay: Int,
                         maximumTransferDayVkPay: Int): Boolean =
        when {
            (card_AccountType in 1..4) -> {
                chekLimitMaxTransferCard(currentTransfersSum,
                        previousTransfersSumMonth,
                        maximumTransferDayCard,
                        maximumMonthlyTransferCard)
            }
            (card_AccountType == 5) -> {
                chekLimitMaxTransferAccount(currentTransfersSum,
                        previousTransfersSumMonth,
                        maximumMonthlyTransferVkPay,
                        maximumTransferDayVkPay)
            }
            else -> false
        }

fun chekLimitMaxTransferCard(currentTransfersSum: Int,
                             previousTransfersSumMonth: Int,
                             maximumTransferDayCard: Int,
                             maximumMonthlyTransferCard: Int): Boolean {
    if (currentTransfersSum > maximumTransferDayCard) {
        println("Сумма перевода превышает дневной лимит: $maximumTransferDayCard коп")
        return false
    }
    if (previousTransfersSumMonth > maximumMonthlyTransferCard) {
        println("Сумма перевода превышает месячный лимит: $maximumMonthlyTransferCard коп")
       return false
    } else return true
}

fun chekLimitMaxTransferAccount(currentTransfersSum: Int,
                                previousTransfersSumMonth: Int,
                                maximumMonthlyTransferVkPay: Int,
                                maximumTransferDayVkPay: Int): Boolean {
    if (currentTransfersSum > maximumTransferDayVkPay) {
        println("Сумма перевода превышает дневной лимит: $maximumTransferDayVkPay коп")
        return false
    }

    if (previousTransfersSumMonth > maximumMonthlyTransferVkPay) {
        println("Сумма перевода превышает дневной лимит: $maximumMonthlyTransferVkPay коп")
        return false
    } else return true
}

fun spotTaxOnCard_Account(card_AccountType: Int,
                          currentTransfersSum: Int,
                          previousTransfersSumMonth: Int,
                          taxViza_Mir: Double,
                          minTaxViza_Mir: Int,
                          limitMonthMastercard_Maestro: Int,
                          taxMonthMastercard_Maestro: Double,
                          additionaltaxMonthMastercard_Maestro: Int): Int =
        when (card_AccountType) {
            1, 2 -> if (previousTransfersSumMonth + currentTransfersSum < limitMonthMastercard_Maestro)
                0 else (currentTransfersSum * taxMonthMastercard_Maestro).toInt() / 100 + additionaltaxMonthMastercard_Maestro
            3, 4 -> if (currentTransfersSum > minTaxViza_Mir)
                (currentTransfersSum * taxViza_Mir).toInt() / 100 else minTaxViza_Mir
            5 -> 0
            else -> throw RuntimeException()
        }

fun calculationFinalTransfersSum(currentTransfersSum: Int,
                                 transfersComission: Int): Int =
        currentTransfersSum - transfersComission