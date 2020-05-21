from django.http import HttpResponse
from rest_framework.views import APIView
from rest_framework.response import Response
import json
from django.shortcuts import render
import pandas as pd
import numpy as np
from django.conf import settings
from rest_framework import views
from rest_framework import status
from rest_framework.response import Response
import json
import warnings
warnings.filterwarnings("ignore")

import ips_py.ips.analysis.distfit.DataLoader as DL
import ips_py.ips.analysis.distfit.FitDistribution as FD


# Configuration Class
class DistributionFittingConfig(object):
    datasetId = None
    repositoryId = None
   

    # Decoding from JSON Object
    def fromJson(self, json):
        self.datasetId = json.get('datasetId', None)
        self.repositoryId = json.get('repositoryId', None)

# Algorithm Runner
class Run(APIView):
    def post(self, request, format=None):
        # Reading Config from Web Request
        config = DistributionFittingConfig()
        config.fromJson(json.loads(request.body.decode('utf-8')))

        try:
    
            x = DL.DataLoader(config.repositoryId)
                          
            x.ConnectDB()
            
            Res = x.LoadTable()             

            ActList = Res['ea'].value_counts().index
        
            ActList = Res['ea'].value_counts().index
        
            ClassifyByAct = list()
        
            for i in range(len(ActList)):
            
                x = Res.loc[Res['ea']==ActList[i]].sort_values(['es','ec']).reset_index(drop=True)
        
                ClassifyByAct.append(x) 
            
            TD = list()
        
            for i in range(len(ActList)):
                
                if(len(ClassifyByAct[i])>2):
                
                    if(sum(ClassifyByAct[i]['ec']==ClassifyByAct[i]['es'])==len(ClassifyByAct[i])):
                        
                        start = pd.to_datetime(ClassifyByAct[i]['es'][0:(len(ClassifyByAct[i])-1)]).reset_index(drop = True)
                        
                        end = pd.to_datetime(ClassifyByAct[i]['es'][1:(len(ClassifyByAct[i]))]).reset_index(drop = True)
                        
                        TD0 = list()
                        
                        for j in range(len(ClassifyByAct[i])-1):
                            
                            TD0.append((end[j]-start[j]).seconds)
                            
                        TD0 = pd.DataFrame(TD0,columns=['td'])
                        
                        TD1 = pd.concat([ClassifyByAct[i]['ea'][0:len(ClassifyByAct[i])-1],TD0],axis=1)
                        
                        TD.append(TD1)
                        
                    else:
                        
                        start = ClassifyByAct[i]['es']
                        
                        end = ClassifyByAct[i]['ea']
                        
                        for j in range(len(ClassifyByAct[i])-1):
                            
                            TD0.append((end[j]-start[j]).seconds)
                            
                        TD0 = pd.concat([ClassifyByAct[i]['ea'][0:len(ClassifyByAct[i])-1],pd.DataFrame(TD0,columns=['td'])],axis=1)
                        
                        TD.append(TD0)
                        
                else:
                      
                    TD0 = pd.concat([ClassifyByAct[i]['ea'],pd.DataFrame(np.zeros(1),columns=['td'])],axis=1)   
                
                    TD.append(TD0)
                    
            for i in range(len(ActList)):
                
                if sum(TD[i]['td'].isnull())!=0 :
                    
                    TD[i]['td'].iloc[np.where(TD[i]['td'].isnull())[0][0]]= 0
                        
                else:
                    
                    pass
                    
            FitResult = dict()

            names = []

            #Bar = Progress.Progress(len(TD), prefix = 'Progress:', suffix = 'Complete')

            #Bar.PrintProgressBar(0)

            for i in range(len(TD)):
                
                x = FD.DistributionFitting(TD[i]['td'])
                
                x.NumOfBins()
                
                name, parm = x.FitDistribution()
                
                parm0 = dict()
                
                distribution = dict()
                
                if(len(parm)==1):
                    
                    parm0.update({"parm1":parm[0]})
                        
                elif(len(parm)==2):
                    
                    parm0.update({"parm1":parm[0],"parm2":parm[1]})
                    
                else:
                    
                    parm0.update({"parm1":parm[0],"parm2":parm[1],"parm3":parm[2]})
                    
                    
                distribution.update({name:parm0})
                
                FitResult.update({ActList[i]:distribution})
                
                #Bar.PrintProgressBar(i+1)
    
        
        except Exception as err:
                
            return Response(str(err), status=status.HTTP_400_BAD_REQUEST) 
                
        return Response(FitResult, status = status.HTTP_200_OK)
