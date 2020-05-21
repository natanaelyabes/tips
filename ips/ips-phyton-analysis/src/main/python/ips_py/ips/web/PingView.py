from django.http import HttpResponse
from rest_framework.views import APIView
from rest_framework.response import Response

class Pong(APIView):
    def get(self, request, format=None):
        return Response('PONG')

    def post(self, request, format=None):
        return Response('PONG')
