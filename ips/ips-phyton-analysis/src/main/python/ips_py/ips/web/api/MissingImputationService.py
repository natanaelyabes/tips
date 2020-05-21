from django.http import HttpResponse
from rest_framework.views import APIView
from rest_framework.response import Response
import json

# Configuration Class
class MissingImputationConfig(object):
    datasetId = None
    repositoryId = None

    # Decoding from JSON Object
    def fromJson(self, json):
        self.datasetId = json.get('datasetId', None)
        self.repositoryId = json.get('repositoryId', None)


# Result Class
class MissingImputationModel(object):
    config = None
    data = None

    # Encoding to JSON Object
    def toJson(self):
        json = self.__dict__
        json['config'] = self.config.__dict__
        return json

# Algorithm Runner
class Run(APIView):
    def post(self, request, format=None):
        # Reading Config from Web Request
        config = MissingImputationConfig()
        config.fromJson(json.loads(request.body.decode('utf-8')))

        # Write Result as Object
        result = MissingImputationModel()
        result.config = config

        # REAL ALGORITHM HERE
        result.data = 'EXAMPLE RESULT'

        return Response(result.toJson())
