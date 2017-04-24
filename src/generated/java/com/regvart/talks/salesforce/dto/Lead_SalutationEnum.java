/*
 * Salesforce DTO generated by camel-salesforce-maven-plugin
 * Generated on: Sun Apr 23 21:11:25 CEST 2017
 */
package com.regvart.talks.salesforce.dto;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Salesforce Enumeration DTO for picklist Salutation
 */
@Generated("org.apache.camel.maven.CamelSalesforceMojo")
public enum Lead_SalutationEnum {

    // Dr.
    DR_("Dr."),
    // Mr.
    MR_("Mr."),
    // Mrs.
    MRS_("Mrs."),
    // Ms.
    MS_("Ms."),
    // Prof.
    PROF_("Prof.");

    final String value;

    private Lead_SalutationEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Lead_SalutationEnum fromValue(String value) {
        for (Lead_SalutationEnum e : Lead_SalutationEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

}
