package com.iamlomas.birthday_calculator

import java.util.*

/** Calculates the current age or time remaining for next birthday celebration. */
class BirthdayCalculator {
    private val currentDate = Calendar.getInstance()
    private val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
    private val currentMonth: Int = currentDate.get(Calendar.MONTH)
    private val currentYear: Int = currentDate.get(Calendar.YEAR)

    /**
     * @param year Year of birth
     * @param month Month of birth. Input month should be one less than actual month because
     * January = 0 and December = 11
     * @param day Day of birth */
    fun calculateAge(year: Int, month: Int, day: Int): String {

        val birth = Calendar.getInstance()
        birth.set(year, month, day)
        val birthDay = birth.get(Calendar.DAY_OF_MONTH)
        val birthMonth = birth.get(Calendar.MONTH)
        val birthYear = birth.get(Calendar.YEAR)

        val temp = Calendar.getInstance()
        temp.set(year, month, day)

        var totalDays = 0
        var intMonth = 0
        var intDays = 0

        for (iYear in birthYear..currentYear) {
            when {
                iYear == currentYear && iYear == birthYear -> {
                    for (iMonth in birthMonth..currentMonth) {
                        temp.set(iYear, iMonth, 1)
                        when {
                            iMonth == currentMonth && iMonth == birthMonth -> {
                                totalDays += currentDay - birthDay
                            }
                            iMonth != currentMonth && iMonth != birthMonth -> {
                                totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                                intMonth++
                            }
                            iMonth == birthMonth -> {
                                totalDays += birth.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay
                            }
                            iMonth == currentMonth -> {
                                totalDays += currentDay
                                intDays = if (birthDay < currentDay) {
                                    intMonth++
                                    currentDay - birthDay
                                } else {
                                    temp.set(currentYear, currentMonth - 1, 1)
                                    temp.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay + currentDay
                                }
                            }
                        }
                    }
                }
                iYear != currentYear && iYear != birthYear -> {
                    for (iMonth in 0..11) {
                        temp.set(iYear, iMonth, 1)
                        totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                        intMonth++
                    }
                }
                iYear == birthYear -> {
                    for (iMonth in birthMonth..11) {
                        temp.set(iYear, iMonth, 1)

                        totalDays += if (iMonth == birthMonth) {
                            birth.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay
                        } else {
                            intMonth++
                            temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                        }
                    }
                }
                iYear == currentYear -> {
                    for (iMonth in 0..currentMonth) {
                        temp.set(iYear, iMonth, 1)

                        if (iMonth == currentMonth) {
                            totalDays += currentDay

                            intDays = if (birthDay < currentDay) {
                                intMonth++
                                currentDay - birthDay
                            } else {
                                temp.set(currentYear, currentMonth - 1, 1)
                                temp.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay + currentDay
                            }
                        } else {
                            intMonth++
                            totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                        }
                    }
                }
            }
        }
        // For returning age in YEAR MONTH DAY
        val ageYear = intMonth / 12
        val ageMonth = intMonth % 12
        val ageDays = intDays

        return if (ageMonth == 11 && (ageDays == 31 || ageDays == 30)) {
            if (ageYear == 1) {
                "${ageYear + 1} year i.e., $totalDays days"
            } else {
                "${ageYear + 1} years i.e., $totalDays days"
            }
        } else {
            when {
                ageYear == 1 && ageMonth == 1 && ageDays == 1 -> {
                    "$ageYear year $ageMonth month \nand $ageDays day i.e., $totalDays days"
                }
                ageYear == 1 && ageMonth == 1 -> {
                    "$ageYear year $ageMonth month \nand $ageDays days i.e., $totalDays days"
                }
                ageYear == 1 && ageDays == 1 -> {
                    "$ageYear year $ageMonth months \nand $ageDays day i.e., $totalDays days"
                }
                ageMonth == 1 && ageDays == 1 -> {
                    "$ageYear years $ageMonth month \nand $ageDays day i.e., $totalDays days"
                }
                ageYear == 1 -> {
                    "$ageYear year $ageMonth months \nand $ageDays days i.e., $totalDays days"
                }
                ageMonth == 1 -> {
                    "$ageYear years $ageMonth month \nand $ageDays days i.e., $totalDays days"
                }
                ageDays == 1 -> {
                    "$ageYear years $ageMonth months \nand $ageDays day i.e., $totalDays day"
                }
                ageDays < 1 && totalDays == 1 -> {
                    "$ageYear years $ageMonth months \nand $ageDays days i.e., $totalDays day"
                }
                else -> "$ageYear years $ageMonth months \nand $ageDays days i.e., $totalDays days"
            }
        }
    }

    /**
     * @param year Year of birth
     * @param month Month of birth. Input month should be one less than actual month because
     * January = 0 and December = 11
     * @param day Day of birth */
    fun calculateNextBirthDate(year: Int, month: Int, day: Int): String {

        val birthDate = Calendar.getInstance()
        birthDate.set(year, month, day)
        val birthMonth = birthDate.get(Calendar.MONTH)
        val birthDay = birthDate.get(Calendar.DAY_OF_MONTH)

        val temp = Calendar.getInstance()
        temp.set(year, month, day)

        var totalDays = 0
        var intMonth = 0
        var intDays = 0

        when {
            birthMonth > currentMonth -> {
                temp.set(currentYear, currentMonth, 1)
                for (iMonth in (currentMonth + 1) until birthMonth) intMonth++
                totalDays +=
                    (intMonth * 30) + temp.getActualMaximum(Calendar.DAY_OF_MONTH) - currentDay + birthDay
                intMonth = totalDays / 30
                intDays = totalDays % 30
            }
            birthMonth < currentMonth -> {
                temp.set(currentYear, currentMonth, 1)
                for (iMonth in (currentMonth + 1)..11) intMonth++
                totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH) - currentDay

                temp.set(currentYear + 1, 0, 1)
                for (iMonth in 0 until birthMonth) intMonth++
                totalDays += (intMonth * 30) + birthDay
                intMonth = totalDays / 30
                intDays = totalDays % 30
            }
            else -> {
                if (birthMonth == currentMonth && birthDay > currentDay) {
                    totalDays += birthDay - currentDay
                    intDays = totalDays
                } else {
                    temp.set(currentYear, currentMonth, 1)
                    for (iMonth in (currentMonth + 1)..11) intMonth++
                    totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH) - currentDay

                    temp.set(currentYear + 1, 0, 1)
                    for (iMonth in 0 until birthMonth) intMonth++
                    totalDays += (intMonth * 30) + birthDay
                    intMonth = totalDays / 30
                    intDays = totalDays % 30
                }
            }
        }

        return when {
            intMonth == 1 && intDays == 1 -> "$intMonth month and $intDays day"
            intMonth == 1 -> "$intMonth month and $intDays days"
            intDays == 1 -> "$intMonth months and $intDays day"
            else -> "$intMonth months and $intDays days"
        }
    }
}