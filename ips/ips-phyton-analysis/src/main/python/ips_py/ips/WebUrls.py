from django.urls import include, path
from django.shortcuts import redirect

from ips_py.ips import ApiUrls
from ips_py.ips.web import PingView

urlpatterns = [
    path('', lambda request: redirect('/ips/py/ping')),
    path('ping', PingView.Pong.as_view()),
    path('api/v1/', include(ApiUrls)),
]
