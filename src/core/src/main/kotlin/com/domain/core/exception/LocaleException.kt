package com.domain.core.exception

import java.util.*

class LocaleException(locale:Locale) : Exception("Locale $locale not supported")