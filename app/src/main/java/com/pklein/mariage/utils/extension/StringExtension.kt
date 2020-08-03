package com.pklein.mariage.utils.extension

import org.apache.commons.lang3.StringUtils

fun String.formatAnswer() : String {
    return StringUtils.stripAccents(this.trim().toLowerCase())
}