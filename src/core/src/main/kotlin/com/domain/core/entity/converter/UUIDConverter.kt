package com.domain.core.entity.converter

import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class UUIDConverter : AttributeConverter<UUID, UUID> {

    override fun convertToDatabaseColumn(attribute: UUID): UUID {
        return attribute
    }

    override fun convertToEntityAttribute(dbData: UUID): UUID {
        return dbData
    }
}