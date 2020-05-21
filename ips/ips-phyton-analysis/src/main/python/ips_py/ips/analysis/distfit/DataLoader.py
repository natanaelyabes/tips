# -*- coding: utf-8 -*-
"""
Created on Mon Apr  6 10:52:11 2020

@author: iochord
"""
import psycopg2
import pandas as pd

"""
PostgreSQL
spring.datasource.url=jdbc:postgresql://19.45.8.222:5432/chdsr
#spring.datasource.url=jdbc:postgresql://apps-postgres-postgresql:5432/ips
spring.datasource.username=postgres
spring.datasource.password=tipspsql
"""

class DataLoader:
    
    def __init__(self, repositoryId):
        #psql://postgres:tipspsql@apps-postgres-postgresql:5432/ips
        self.DBName = 'ips'
        
        self.Host = 'apps-postgres-postgresql'
        
        self.UserName = 'postgres'
        
        self.Password = 'tipspsql'
        
        self.Port = '5432'
        
        self.TableName = repositoryId
        
    def ConnectDB(self):
        
        ConnectInformation = "dbname = {dbname} user = {user} host = {host} password={password} port={port}".format(
                
                dbname = self.DBName, user = self.UserName, host = self.Host, password = self.Password, port = self.Port)  
        
        print(ConnectInformation)
                            
        DB = psycopg2.connect(ConnectInformation)
        
        self.cur = DB.cursor()

    def LoadTable(self):
        
        Sql = "SELECT * FROM {Table}".format(Table = self.TableName) 
        
        self.cur.execute(Sql)

        Result = self.cur.fetchall()

        ColumnName = list()

        if len(Result)==0:
            
            print("There is no elments")
            
        else:
            
            for i in range(len(Result[0])):
    
                ColumnName.append(self.cur.description[i][0])
    
            LoadResult = pd.DataFrame(Result[0]).T

            for i in range(1,len(Result)):
    
                x = pd.DataFrame(Result[i]).T
    
                LoadResult = LoadResult.append(x).reset_index(drop=True)
    
            LoadResult.columns = ColumnName
    
        return LoadResult
        