# -*- coding: utf-8 -*-
# Copyright 2023 Ant Group CO., Ltd.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
# in compliance with the License. You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software distributed under the License
# is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
# or implied.

# ATTENTION!
# This file is generated by Schema automatically, it will be refreshed after schema has been committed
# PLEASE DO NOT MODIFY THIS FILE!!!
#

from knext.common.schema_helper import SPGTypeHelper, PropertyHelper


class Financial:
    
    class AdministrativeArea(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        alias = PropertyHelper("alias")
        stdId = PropertyHelper("stdId")
    
    class AreaRiskEvent(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        eventTime = PropertyHelper("eventTime")
        subject = PropertyHelper("subject")
        object = PropertyHelper("object")

    class Company(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        regArea = PropertyHelper("regArea")
        establishDate = PropertyHelper("establishDate")
        regCapital = PropertyHelper("regCapital")
        businessScope = PropertyHelper("businessScope")
        orgCertNo = PropertyHelper("orgCertNo")
        legalPerson = PropertyHelper("legalPerson")

    class CompanyEvent(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        subject = PropertyHelper("subject")
        eventTime = PropertyHelper("eventTime")
        object = PropertyHelper("object")
        location = PropertyHelper("location")
        happenedTime = PropertyHelper("happenedTime")

    class Indicator(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        alias = PropertyHelper("alias")
        stdId = PropertyHelper("stdId")

    class State(SPGTypeHelper):
        description = PropertyHelper("description")
        id = PropertyHelper("id")
        name = PropertyHelper("name")
        stdId = PropertyHelper("stdId")
        causes = PropertyHelper("causes")
        alias = PropertyHelper("alias")
        derivedFrom = PropertyHelper("derivedFrom")
    
    AdministrativeArea = AdministrativeArea("Financial.AdministrativeArea")
    AreaRiskEvent = AreaRiskEvent("Financial.AreaRiskEvent")
    Company = Company("Financial.Company")
    CompanyEvent = CompanyEvent("Financial.CompanyEvent")
    Indicator = Indicator("Financial.Indicator")
    State = State("Financial.State")
    