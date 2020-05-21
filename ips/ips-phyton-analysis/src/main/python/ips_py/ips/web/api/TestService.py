from django.http import HttpResponse
from rest_framework.views import APIView
from rest_framework.response import Response
import json

class HelloConfig(object):
    message = None

    def fromJson(self, json):
        self.message = json.get('message', None)

class HelloWorldModel(object):
    message = None

    def toJson(self):
        return self.__dict__

class RunHello(APIView):
    def get(self, request, format=None):
        return Response('OK !')

    def post(self, request, format=None):
        config = HelloConfig()
        config.fromJson(json.loads(request.body.decode('utf-8')))
        
        result = HelloWorldModel()
        result.message = 'Hello ' + config.message

        return Response(result.toJson())
