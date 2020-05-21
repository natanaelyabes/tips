from django.urls import include, path
from django.shortcuts import redirect

from ips_py.ips.web.api import TestService
from ips_py.ips.web.api import MissingImputationService
from ips_py.ips.web.api import DistributionFittingService

urlpatterns = [
    path('test', TestService.RunHello.as_view()),
    path('analysis/missimp', MissingImputationService.Run.as_view()),
    path('analysis/distfit', DistributionFittingService.Run.as_view()),
]
