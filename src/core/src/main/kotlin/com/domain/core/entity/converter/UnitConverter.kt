package com.domain.core.entity.converter

import com.domain.core.entity.Preference
import com.domain.core.exception.UnitException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class UnitConverter : AttributeConverter<Preference.Unit, String> {

    override fun convertToDatabaseColumn(attribute: Preference.Unit): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Preference.Unit {
        return Preference.Unit.values()
                .firstOrNull { it.value == dbData } ?: throw UnitException()
    }
}