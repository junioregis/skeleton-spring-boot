package com.domain.core.entity.converter

import com.domain.core.exception.ProviderException
import com.domain.core.service.social.Providers
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class ProviderConverter : AttributeConverter<Providers, String> {

    override fun convertToDatabaseColumn(attribute: Providers): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Providers {
        return Providers.values()
                .firstOrNull { it.value == dbData } ?: throw ProviderException(ProviderException.NOT_FOUND)
    }
}