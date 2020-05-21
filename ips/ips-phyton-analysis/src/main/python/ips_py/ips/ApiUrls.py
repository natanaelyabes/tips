from django.urls import include, path
from django.shortcuts import redirect

from ips_py.ips.web.api import TestService
from ips_py.ips.web.api import MissingImputationService

urlpatterns = [
    path('test', TestService.RunHello.as_view()),
    path('service/missimp', MissingImputationService.Run.as_view()),
]
