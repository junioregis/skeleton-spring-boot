package com.domain.core.entity.converter

import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocaleConverter : AttributeConverter<Locale, String> {

    override fun convertToDatabaseColumn(attribute: Locale): String {
        return attribute.toLanguageTag()
    }

    override fun convertToEntityAttribute(dbData: String): Locale {
        return Locale(dbData)
    }
}