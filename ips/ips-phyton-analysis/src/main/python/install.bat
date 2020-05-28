@echo off
cls
pip install django
pip install django-rest-framework
pip install django-cors-headers
pip install pandas
pip install scipy
pip install numpy
pip install django-environ
pip install psycopg2-binary
python manage.py migrate