# coding: utf-8

"""
    knext

    No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)  # noqa: E501

    The version of the OpenAPI document: 1.0.0
    Generated by: https://openapi-generator.tech
"""


import pprint
import re  # noqa: F401

import six

from knext.common.rest.configuration import Configuration


class VectorSearchRequest(object):
    """NOTE: This class is auto generated by OpenAPI Generator.
    Ref: https://openapi-generator.tech

    Do not edit the class manually.
    """

    """
    Attributes:
      openapi_types (dict): The key is attribute name
                            and the value is attribute type.
      attribute_map (dict): The key is attribute name
                            and the value is json key in definition.
    """
    openapi_types = {
        'project_id': 'int',
        'label': 'str',
        'property_key': 'str',
        'query_vector': 'list[float]',
        'ef_search': 'int',
        'topk': 'int'
    }

    attribute_map = {
        'project_id': 'projectId',
        'label': 'label',
        'property_key': 'propertyKey',
        'query_vector': 'queryVector',
        'ef_search': 'efSearch',
        'topk': 'topk'
    }

    def __init__(self, project_id=None, label=None, property_key=None, query_vector=None, ef_search=None, topk=None, local_vars_configuration=None):  # noqa: E501
        """VectorSearchRequest - a model defined in OpenAPI"""  # noqa: E501
        if local_vars_configuration is None:
            local_vars_configuration = Configuration()
        self.local_vars_configuration = local_vars_configuration

        self._project_id = None
        self._label = None
        self._property_key = None
        self._query_vector = None
        self._ef_search = None
        self._topk = None
        self.discriminator = None

        self.project_id = project_id
        self.label = label
        self.property_key = property_key
        self.query_vector = query_vector
        if ef_search is not None:
            self.ef_search = ef_search
        self.topk = topk

    @property
    def project_id(self):
        """Gets the project_id of this VectorSearchRequest.  # noqa: E501


        :return: The project_id of this VectorSearchRequest.  # noqa: E501
        :rtype: int
        """
        return self._project_id

    @project_id.setter
    def project_id(self, project_id):
        """Sets the project_id of this VectorSearchRequest.


        :param project_id: The project_id of this VectorSearchRequest.  # noqa: E501
        :type: int
        """
        if self.local_vars_configuration.client_side_validation and project_id is None:  # noqa: E501
            raise ValueError("Invalid value for `project_id`, must not be `None`")  # noqa: E501

        self._project_id = project_id

    @property
    def label(self):
        """Gets the label of this VectorSearchRequest.  # noqa: E501


        :return: The label of this VectorSearchRequest.  # noqa: E501
        :rtype: str
        """
        return self._label

    @label.setter
    def label(self, label):
        """Sets the label of this VectorSearchRequest.


        :param label: The label of this VectorSearchRequest.  # noqa: E501
        :type: str
        """
        if self.local_vars_configuration.client_side_validation and label is None:  # noqa: E501
            raise ValueError("Invalid value for `label`, must not be `None`")  # noqa: E501

        self._label = label

    @property
    def property_key(self):
        """Gets the property_key of this VectorSearchRequest.  # noqa: E501


        :return: The property_key of this VectorSearchRequest.  # noqa: E501
        :rtype: str
        """
        return self._property_key

    @property_key.setter
    def property_key(self, property_key):
        """Sets the property_key of this VectorSearchRequest.


        :param property_key: The property_key of this VectorSearchRequest.  # noqa: E501
        :type: str
        """
        if self.local_vars_configuration.client_side_validation and property_key is None:  # noqa: E501
            raise ValueError("Invalid value for `property_key`, must not be `None`")  # noqa: E501

        self._property_key = property_key

    @property
    def query_vector(self):
        """Gets the query_vector of this VectorSearchRequest.  # noqa: E501


        :return: The query_vector of this VectorSearchRequest.  # noqa: E501
        :rtype: list[float]
        """
        return self._query_vector

    @query_vector.setter
    def query_vector(self, query_vector):
        """Sets the query_vector of this VectorSearchRequest.


        :param query_vector: The query_vector of this VectorSearchRequest.  # noqa: E501
        :type: list[float]
        """
        if self.local_vars_configuration.client_side_validation and query_vector is None:  # noqa: E501
            raise ValueError("Invalid value for `query_vector`, must not be `None`")  # noqa: E501

        self._query_vector = query_vector

    @property
    def ef_search(self):
        """Gets the ef_search of this VectorSearchRequest.  # noqa: E501


        :return: The ef_search of this VectorSearchRequest.  # noqa: E501
        :rtype: int
        """
        return self._ef_search

    @ef_search.setter
    def ef_search(self, ef_search):
        """Sets the ef_search of this VectorSearchRequest.


        :param ef_search: The ef_search of this VectorSearchRequest.  # noqa: E501
        :type: int
        """

        self._ef_search = ef_search

    @property
    def topk(self):
        """Gets the topk of this VectorSearchRequest.  # noqa: E501


        :return: The topk of this VectorSearchRequest.  # noqa: E501
        :rtype: int
        """
        return self._topk

    @topk.setter
    def topk(self, topk):
        """Sets the topk of this VectorSearchRequest.


        :param topk: The topk of this VectorSearchRequest.  # noqa: E501
        :type: int
        """
        if self.local_vars_configuration.client_side_validation and topk is None:  # noqa: E501
            raise ValueError("Invalid value for `topk`, must not be `None`")  # noqa: E501

        self._topk = topk

    def to_dict(self):
        """Returns the model properties as a dict"""
        result = {}

        for attr, _ in six.iteritems(self.openapi_types):
            value = getattr(self, attr)
            if isinstance(value, list):
                result[attr] = list(map(
                    lambda x: x.to_dict() if hasattr(x, "to_dict") else x,
                    value
                ))
            elif hasattr(value, "to_dict"):
                result[attr] = value.to_dict()
            elif isinstance(value, dict):
                result[attr] = dict(map(
                    lambda item: (item[0], item[1].to_dict())
                    if hasattr(item[1], "to_dict") else item,
                    value.items()
                ))
            else:
                result[attr] = value

        return result

    def to_str(self):
        """Returns the string representation of the model"""
        return pprint.pformat(self.to_dict())

    def __repr__(self):
        """For `print` and `pprint`"""
        return self.to_str()

    def __eq__(self, other):
        """Returns true if both objects are equal"""
        if not isinstance(other, VectorSearchRequest):
            return False

        return self.to_dict() == other.to_dict()

    def __ne__(self, other):
        """Returns true if both objects are not equal"""
        if not isinstance(other, VectorSearchRequest):
            return True

        return self.to_dict() != other.to_dict()