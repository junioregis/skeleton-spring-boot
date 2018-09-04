package com.domain.core.entity.converter

import com.domain.core.entity.Profile
import com.domain.core.exception.GenderException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class GenderConverter : AttributeConverter<Profile.Gender, String> {

    override fun convertToDatabaseColumn(attribute: Profile.Gender): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Profile.Gender {
        return Profile.Gender.values()
                .firstOrNull { it.value == dbData } ?: throw GenderException()
    }
}