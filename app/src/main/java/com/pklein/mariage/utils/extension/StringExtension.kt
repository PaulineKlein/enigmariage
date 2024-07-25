package com.pklein.mariage.utils.extension

import org.apache.commons.lang3.StringUtils
import java.util.*

fun String.formatAnswer() : String {
    return StringUtils.stripAccents(this.trim().lowercase(Locale.ROOT))
}